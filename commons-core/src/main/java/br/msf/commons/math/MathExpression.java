/*
 * Copyright (C) 2013 Marcius da Silva da Fonseca.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA
 */
package br.msf.commons.math;

import br.msf.commons.math.exception.DivisionByZeroException;
import br.msf.commons.math.exception.InfiniteNumberException;
import br.msf.commons.math.exception.InvalidExpressionException;
import br.msf.commons.math.exception.NaNException;
import br.msf.commons.text.EnhancedStringBuilder;
import br.msf.commons.util.ArgumentUtils;
import br.msf.commons.util.CharSequenceUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * A class that represents a math expression.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com.br)
 * @version 1.0
 */
public class MathExpression implements Serializable {

    private static final long serialVersionUID = -8896432225493060514L;
    /**
     * Default value for the max scale used on the calculations.
     * <p/>
     * Used when the programmer doesn't explicit indicates one value.
     * <p/>
     * Value = 14.
     */
    public static final int DEFAULT_MAX_SCALE = 14;
    /**
     * Default rounding mode to be used to truncate the fractional part of numbers that surpasses the max scale.
     * <p/>
     * Used when the programmer doesn't explicit indicates one value.
     * <p/>
     * Value = {@link RoundingMode#HALF_EVEN}.
     */
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
    /**
     * Max scale used on the calculations.
     * <p/>
     * Values that surpasses such scale will be rounded.
     */
    private int maxScale;
    /**
     * Rounding mode used to truncate the fractional part of numbers that surpasses the max scale.
     */
    private RoundingMode roundingMode;
    /**
     * String that holds the equation to be evaluated.
     */
    private CharSequence expression;

    /**
     * Default constructor.
     * <p/>
     * No expression is set.
     * <p/>
     * Uses {@link #DEFAULT_MAX_SCALE} and {@link #DEFAULT_ROUNDING_MODE}.
     */
    public MathExpression() {
        this(null, DEFAULT_MAX_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     * Constructor that defines an expression.
     * <p/>
     * Uses {@link #DEFAULT_MAX_SCALE} and {@link #DEFAULT_ROUNDING_MODE}.
     *
     * @param expression The expression to be initialized with.
     * @throws InvalidExpressionException If the given expression is invalid.
     */
    public MathExpression(final CharSequence expression) {
        this(expression, DEFAULT_MAX_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     * Constructor that defines an expression and the max scale.
     * <p/>
     * Uses {@link #DEFAULT_ROUNDING_MODE}.
     *
     * @param expression The expression to be initialized with.
     * @param maxScale   The max scale to be used if rounding is needed on calculations.
     * @throws InvalidExpressionException If the given expression is invalid.
     */
    public MathExpression(final CharSequence expression, final int maxScale) {
        this(expression, maxScale, DEFAULT_ROUNDING_MODE);
    }

    /**
     * Constructor that defines an expression, the max scale and the rounding mode.
     *
     * @param expression   The expression to be initialized with.
     * @param maxScale     The max scale to be used if rounding is needed on calculations.
     * @param roundingMode The rounding mode to be used if the resulting scale is <tt>&gt; maxScale</tt>.
     * @throws InvalidExpressionException If the given expression is invalid.
     */
    public MathExpression(final CharSequence expression, final int maxScale, final RoundingMode roundingMode) {
        setExpression(expression);
        setMaxScale(maxScale);
        setRoundingMode(roundingMode);
    }

    /**
     * Returns the current expression.
     *
     * @return The current expression.
     */
    public final CharSequence getExpression() {
        return expression;
    }

    /**
     * Sets a new expression.
     *
     * @param expression The new expression.
     * @throws InvalidExpressionException If the given expression is invalid.
     */
    public final void setExpression(final CharSequence expression) {
        try {
            this.expression = formatExpression(expression);
        } catch (Exception ex) {
            throw new InvalidExpressionException(ex);
        }
    }

    /**
     * Returns the current max scale.
     *
     * @return The current max scale.
     */
    public final int getMaxScale() {
        return maxScale;
    }

    /**
     * Sets a new max scale.
     *
     * @param maxScale The new max scale. Must be inside the [1 - 32] range.
     * @throws IllegalArgumentException If the new max scale is out of [1 - 32] range.
     */
    public final void setMaxScale(final int maxScale) {
        ArgumentUtils.rejectIfOutOfBounds(maxScale, 1, 32);
        this.maxScale = maxScale;
    }

    /**
     * Returns the current rounding mode.
     *
     * @return The current rounding mode.
     */
    public final RoundingMode getRoundingMode() {
        return roundingMode;
    }

    /**
     * Sets a new rounding mode.
     *
     * @param roundingMode The new rounding mode. Cannot be <tt>null</tt> nor <tt>RoundingMode.UNNECESSARY</tt>.
     * @throws IllegalArgumentException If the new rounding mode is <tt>null</tt> or <tt>RoundingMode.UNNECESSARY</tt>.
     */
    public final void setRoundingMode(final RoundingMode roundingMode) {
        ArgumentUtils.rejectIfNull(roundingMode);
        ArgumentUtils.rejectIfEquals(roundingMode, RoundingMode.UNNECESSARY);
        this.roundingMode = roundingMode;
    }

    /**
     * Returns the expression in PostFix notation.
     *
     * @return The current expression in PostFix notation.
     * @throws InvalidExpressionException If fails to convert to PostFix notation.
     */
    public final CharSequence toPostFix() {
        EnhancedStringBuilder postFix = new EnhancedStringBuilder();
        if (CharSequenceUtils.isBlankOrNull(expression)) {
            return postFix.toString();
        }
        Stack<String> stack = new Stack<>();
        Collection<String> tokens = CharSequenceUtils.split(expression, "\\s+");
        int priority;
        for (String token : tokens) {
            if (isOperand(token)) {
                postFix.append(token).append(" ");
            } else if (isOperator(token)) {
                priority = getOperatorPriority(token);
                while (!stack.isEmpty() && getOperatorPriority(stack.peek()) >= priority) {
                    postFix.append(stack.pop()).append(" ");
                }
                stack.push(token);
            } else if ("(".equals(token)) {
                stack.push(token);
            } else if (")".equals(token)) {
                String item = stack.pop();
                while (!item.equals("(")) {
                    postFix.append(item).append(" ");
                    item = stack.pop();
                }
            } else {
                throw new InvalidExpressionException();
            }
        }
        while (!stack.empty()) {
            postFix.append(stack.pop()).append(" ");
        }
        return postFix.trim();
    }

    /**
     * Returns the expression in PostFix notation, on a stack format.
     *
     * @return The current expression in PostFix notation, on a stack format.
     * @throws InvalidExpressionException If fails to convert to PostFix notation.
     */
    public final Stack<String> toPostFixStack() {
        EnhancedStringBuilder builder = (EnhancedStringBuilder) toPostFix();
        List<String> tks = builder.split("\\s+");
        Collections.reverse(tks);
        Stack<String> stack = new Stack<>();
        for (String tk : tks) {
            stack.push(tk);
        }
        return stack;
    }

    public BigDecimal evaluate() {
        return evaluate(null);
    }

    public BigDecimal evaluate(final Map<String, BigDecimal> vars) {
        try {
            Stack<String> exprStack = toPostFixStack();
            if (exprStack.isEmpty()) {
                return BigDecimal.ZERO;
            }
            int lParCount = CharSequenceUtils.countOccurrencesOf("(", this.expression);
            int rParCount = CharSequenceUtils.countOccurrencesOf(")", this.expression);
            if (lParCount != rParCount) {
                throw new InvalidExpressionException();
            }
            Stack<BigDecimal> operands = new Stack<>();
            while (!exprStack.isEmpty()) {
                String current = exprStack.pop();
                if (isNumber(current)) {
                    operands.push(new BigDecimal(current));
                } else if (isValidVariable(current, true)) {
                    int signal = 1;
                    String varName = current;
                    if (!Character.isLetter(current.charAt(0))) {
                        varName = current.substring(1);
                        if (current.charAt(0) == '-') {
                            signal = -1;
                        }
                    }
                    if (vars == null || !vars.containsKey(varName)) {
                        throw new InvalidExpressionException();
                    }
                    operands.push(vars.get(varName).multiply(new BigDecimal(signal)));
                } else if (isOperator(current)) {
                    BigDecimal op2 = operands.pop();
                    BigDecimal op1 = operands.pop();
                    BigDecimal resp = BigDecimal.ZERO;
                    switch (current.charAt(0)) {
                        case '-':
                            resp = op1.subtract(op2).stripTrailingZeros();
                            break;
                        case '+':
                            resp = op1.add(op2).stripTrailingZeros();
                            break;
                        case '/':
                            if (op2.compareTo(BigDecimal.ZERO) == 0) {
                            throw new DivisionByZeroException();
                        }
                            resp = op1.divide(op2, maxScale, roundingMode).stripTrailingZeros();
                            break;
                        case '*':
                            resp = op1.multiply(op2).stripTrailingZeros();
                            break;
                        case '^':
                            Double d = Math.pow(op1.doubleValue(), op2.doubleValue());
                            if (d.compareTo(Double.NaN) == 0) {
                                throw new NaNException();
                            } else if (d.compareTo(Double.POSITIVE_INFINITY) == 0 || d.compareTo(Double.NEGATIVE_INFINITY) == 0) {
                                throw new InfiniteNumberException();
                            }
                            EnhancedStringBuilder s = new EnhancedStringBuilder(Double.toString(d));
                            if (s.endsWith(".0")) {
                                s.delete(s.length() - 2, s.length());
                            }
                            resp = new BigDecimal(s.toString()).stripTrailingZeros();
                            break;
                        default:
                            throw new IllegalStateException("Unknown operator: " + current);
                    }
                    operands.push(resp);
                }
            }
            BigDecimal ret = operands.pop();
            if (ret.scale() > maxScale) {
                ret = ret.setScale(maxScale, roundingMode);
            }
            return ret;
        } catch (EmptyStackException ex) {
            throw new InvalidExpressionException();
        }
    }

    public static boolean isNumber(final String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isOperator(final char c) {
        return isOperator(Character.toString(c));
    }

    public static boolean isOperator(final String s) {
        return "^*/+-".contains(s) && s.length() == 1;
    }

    public static boolean isOperand(final String s) {
        return isNumber(s) || isValidVariable(s, true);
    }

    public static int getOperatorPriority(final String s) {
        if (s.length() != 1) {
            throw new IllegalArgumentException("Not a valid operator: " + s);
        }
        switch (s.charAt(0)) {
            case '(':
                return 1;
            case '-':
                return 2;
            case '+':
                return 3;
            case '/':
                return 4;
            case '*':
                return 5;
            case '^':
                return 6;
            default:
                throw new IllegalArgumentException("Not a valid operator: " + s);
        }
    }

    public static boolean isValidVariable(final String s, final boolean allowSignal) {
        final String regExp;
        if (CharSequenceUtils.isBlankOrNull(s) || s.equals("NaN")) {
            return false;
        }
        if (allowSignal) {
            regExp = "^[+|-]*[a-z|A-Z][a-z|A-Z|0-9|_]*";
        } else {
            regExp = "^[a-z|A-Z][a-z|A-Z|0-9|_]*";
        }
        return Pattern.matches(regExp, s);
    }

    public static CharSequence formatExpression(final CharSequence expression) {
        EnhancedStringBuilder cleanExpr = new EnhancedStringBuilder(expression);
        cleanExpr.replacePattern("\\s+", "").replacePlain("--", "+");
        while (cleanExpr.indexOf("++") >= 0) {
            cleanExpr.replacePlain("++", "+");
        }
        while (cleanExpr.indexOf("+-") >= 0) {
            cleanExpr.replacePlain("+-", "-");
        }
        while (cleanExpr.indexOf("-+") >= 0) {
            cleanExpr.replacePlain("-+", "-");
        }
        while (cleanExpr.indexOf("()") >= 0) {
            cleanExpr.replacePlain("()", "");
        }
        EnhancedStringBuilder builder = new EnhancedStringBuilder(expression.length());
        for (int i = 0; i < cleanExpr.length(); i++) {
            Character c = cleanExpr.charAt(i);
            if ("+-".indexOf(c) >= 0 && i == 0 || ("+-".indexOf(c) >= 0 && "^*/+-(".indexOf(cleanExpr.charAt(i - 1)) >= 0)) {
                builder.append(c);
            } else if ("^*/+-()".indexOf(c) >= 0) {
                if (builder.length() > 0 && CharSequenceUtils.lastChar(builder) != ' ') {
                    builder.append(" ");
                }
                builder.append(c).append(" ");
            } else {
                builder.append(c);
            }
        }
        return builder;
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
