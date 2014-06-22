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
package br.ojimarcius.commons.util;

import br.ojimarcius.commons.math.exception.RuntimeParseException;
import br.ojimarcius.commons.util.ArgumentUtils;
import br.ojimarcius.commons.util.LocaleUtils;
import br.ojimarcius.commons.util.ObjectUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Class containing utilities for Numbers.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public abstract class NumberUtils {

    /**
     * Default format to parse numbers.
     */
    public static final String DECIMAL_PATTERN = "#,###.##";
    /**
     * A default {@link MathContext}, with a precision setting matching the IEEE 754R Decimal128 format, 34 digits,
     * and a rounding mode of {@link RoundingMode#HALF_EVEN HALF_EVEN}, the IEEE 754R default.
     */
    public static final MathContext DEFAULT_CONTEXT = MathContext.DECIMAL128;
    /**
     * BigDecimal value of 'Zero'.
     */
    public static final BigDecimal ZERO = BigDecimal.ZERO;
    /**
     * BigDecimal value of 'One'.
     */
    public static final BigDecimal ONE = BigDecimal.ONE;
    /**
     * BigDecimal value of 'Two'.
     */
    public static final BigDecimal TWO = new BigDecimal(2);
    /**
     * BigDecimal value of 'Five'.
     */
    public static final BigDecimal FIVE = new BigDecimal(5);
    /**
     * BigDecimal value of 'Ten'.
     */
    public static final BigDecimal TEN = BigDecimal.TEN;
    /**
     * BigDecimal value of 'One hundred'.
     */
    public static final BigDecimal HUNDRED = new BigDecimal(100);
    /**
     * BigDecimal value of 'One thousand'.
     */
    public static final BigDecimal THOUSAND = new BigDecimal(1000);
    /**
     * BigDecimal value of 'One million'.
     */
    public static final BigDecimal MILLION = new BigDecimal(1000000);

    public static boolean isNumber(final Object number) {
        return ObjectUtils.isNumber(number);
    }

    public static boolean isNumber(final Object number, final boolean acceptNull) {
        return ObjectUtils.isNumber(number, acceptNull);
    }

    /**
     * Returns <tt>true</tt> if the given number instance is a <b>not null {@link BigDecimal}</b>.
     *
     * @param number <tt>true</tt> if the given number instance is a not null {@link BigDecimal}.
     * <tt>false</tt> otherwise.
     * @return
     */
    public static boolean isBigDecimal(final Object number) {
        return ObjectUtils.isBigDecimal(number);
    }

    public static boolean isBigDecimal(final Object number, final boolean acceptNull) {
        return ObjectUtils.isBigDecimal(number, acceptNull);
    }

    /**
     * Returns <tt>true</tt> if the given number instance is a <b>not null {@link BigInteger}</b>.
     *
     * @param number <tt>true</tt> if the given number instance is a not null {@link BigInteger}.
     * <tt>false</tt> otherwise.
     * @return
     */
    public static boolean isBigInteger(final Object number) {
        return ObjectUtils.isBigInteger(number);
    }

    public static boolean isBigInteger(final Object number, final boolean acceptNull) {
        return ObjectUtils.isBigInteger(number, acceptNull);
    }

    /**
     * Returns <tt>true</tt> if the given number instance is a <b>not null {@link Integer}</b>.
     *
     * @param number <tt>true</tt> if the given number instance is a not null {@link Integer}.
     * <tt>false</tt> otherwise.
     * @return
     */
    public static boolean isInteger(final Object number) {
        return ObjectUtils.isInteger(number);
    }

    public static boolean isInteger(final Object number, final boolean acceptNull) {
        return ObjectUtils.isInteger(number, acceptNull);
    }

    /**
     * Returns <tt>true</tt> if the given number instance is a <b>not null {@link Long}</b>.
     *
     * @param number <tt>true</tt> if the given number instance is a not null {@link Long}.
     * <tt>false</tt> otherwise.
     * @return
     */
    public static boolean isLong(final Object number) {
        return ObjectUtils.isLong(number);
    }

    public static boolean isLong(final Object number, final boolean acceptNull) {
        return ObjectUtils.isLong(number, acceptNull);
    }

    /**
     * Returns <tt>true</tt> if the given number instance is a <b>not null {@link Float}</b>.
     *
     * @param number <tt>true</tt> if the given number instance is a not null {@link Float}.
     * <tt>false</tt> otherwise.
     * @return
     */
    public static boolean isFloat(final Object number) {
        return ObjectUtils.isFloat(number);
    }

    public static boolean isFloat(final Object number, final boolean acceptNull) {
        return ObjectUtils.isFloat(number, acceptNull);
    }

    /**
     * Returns <tt>true</tt> if the given number instance is a <b>not null {@link Double}</b>.
     *
     * @param number <tt>true</tt> if the given number instance is a not null {@link Double}.
     * <tt>false</tt> otherwise.
     * @return
     */
    public static boolean isDouble(final Object number) {
        return ObjectUtils.isDouble(number);
    }

    public static boolean isDouble(final Object number, final boolean acceptNull) {
        return ObjectUtils.isDouble(number, acceptNull);
    }

    /**
     * Returns <tt>true</tt> if the given number instance is a <b>not null {@link Byte}</b>.
     *
     * @param number <tt>true</tt> if the given number instance is a not null {@link Byte}.
     * <tt>false</tt> otherwise.
     * @return
     */
    public static boolean isByte(final Object number) {
        return ObjectUtils.isByte(number);
    }

    public static boolean isByte(final Object number, final boolean acceptNull) {
        return ObjectUtils.isByte(number, acceptNull);
    }

    /**
     * Returns <tt>true</tt> if the given number instance is a <b>not null {@link Short}</b>.
     *
     * @param number <tt>true</tt> if the given number instance is a not null {@link Short}.
     * <tt>false</tt> otherwise.
     * @return
     */
    public static boolean isShort(final Object number) {
        return ObjectUtils.isShort(number);
    }

    public static boolean isShort(final Object number, final boolean acceptNull) {
        return ObjectUtils.isShort(number, acceptNull);
    }

    /**
     * Returns the given number value converted to {@link Integer}.
     * <p/>
     * If the given number already is an instance of {@link Integer}, it will return the same given instance.
     *
     * @param number The number to convert to Integer.
     * @return The given number value converted to {@link Integer}.
     */
    public static Integer toInteger(final Number number) {
        if (number == null) {
            return null;
        }
        if (isInteger(number)) {
            return (Integer) number;
        }
        return number.intValue();
    }

    /**
     * Faz o cast de um Number qualquer para Long.
     * <br/>
     * Dependendo do tipo de Number passado, a precisão poder ser perdida.
     *
     * @param number O número a ser convertido.
     * @return O número dado, em formato Long.
     */
    public static Long toLong(final Number number) {
        if (number == null) {
            return null;
        }
        if (isLong(number)) {
            return (Long) number;
        }
        return number.longValue();
    }

    /**
     * Faz o cast de um Number qualquer para Double.
     * <br/>
     * Dependendo do tipo de Number passado, a precisão poder ser perdida.
     *
     * @param number O número a ser convertido.
     * @return O número dado, em formato Double.
     */
    public static Double toDouble(final Number number) {
        if (number == null) {
            return null;
        }
        if (isDouble(number)) {
            return (Double) number;
        }
        return number.doubleValue();
    }

    /**
     * Faz o cast de um Number qualquer para Float.
     * <br/>
     * Dependendo do tipo de Number passado, a precisão poder ser perdida.
     *
     * @param number O número a ser convertido.
     * @return O número dado, em formato Float.
     */
    public static Float toFloat(final Number number) {
        if (number == null) {
            return null;
        }
        if (isFloat(number)) {
            return (Float) number;
        }
        return number.floatValue();
    }

    /**
     * Faz o cast de um Number qualquer para Short.
     * <br/>
     * Dependendo do tipo de Number passado, a precisão poder ser perdida.
     *
     * @param number O número a ser convertido.
     * @return O número dado, em formato Short.
     */
    public static Short toShort(final Number number) {
        if (number == null) {
            return null;
        }
        if (isShort(number)) {
            return (Short) number;
        }
        return number.shortValue();
    }

    /**
     * Faz o cast de um Number qualquer para Byte.
     * <br/>
     * Dependendo do tipo de Number passado, a precisão poder ser perdida.
     *
     * @param number O número a ser convertido.
     * @return O número dado, em formato Byte.
     */
    public static Byte toByte(final Number number) {
        if (number == null) {
            return null;
        }
        if (isByte(number)) {
            return (Byte) number;
        }
        return number.byteValue();
    }

    /**
     * Returns the given number value converted to {@link BigDecimal}.
     * <p/>
     * If the given number already is an instance of {@link BigDecimal}, it will return the same given instance.
     *
     * @param number The number to convert to BigDecimal.
     * @return The given number value converted to {@link BigDecimal}.
     */
    public static BigDecimal toBigDecimal(final Number number) {
        if (number == null) {
            return null;
        }
        if (isBigDecimal(number)) {
            return (BigDecimal) number;
        }
        return new BigDecimal(String.valueOf(number));
    }

    /**
     * Separate
     */
    public static Long[] separate(final Number number, final int scale) {
        if (number == null) {
            return null;
        }
        Long[] ret = new Long[]{number.longValue(), 0L};
        if (scale > 0 && (ObjectUtils.isDouble(number) || ObjectUtils.isFloat(number) || ObjectUtils.isBigDecimal(number))) {
            BigDecimal x = BigDecimal.TEN.pow(scale);
            BigDecimal frac = toBigDecimal(number).remainder(BigDecimal.ONE).multiply(x);
            ret[1] = frac.longValue();
        }
        return ret;
    }

    /**
     * Generates a random Integer value, contained on the given range.
     *
     * @param min The minimum value to the random Integer.
     * @param max The maximum value to the random Integer.
     * @return The random Integer value generated.
     */
    public static Integer randomInteger(final Integer min, final Integer max) {
        return toBigDecimal((Math.random() * (max - min))).
                setScale(0, RoundingMode.HALF_EVEN).
                intValue() + min;
    }

    public static BigDecimal round(final BigDecimal number, final int scale) {
        ArgumentUtils.rejectIfNull(number);
        return number.setScale(scale, MathContext.DECIMAL128.getRoundingMode()).stripTrailingZeros();
    }

    public static <T extends Number> T normalize(final T value, final T min, final T max) {
        ArgumentUtils.rejectIfNull(value);
        ArgumentUtils.rejectIfAllNull(min, max);
        if (min != null && max != null) {
            ArgumentUtils.rejectIfGreaterEqual(min, max);
        }
        if (min != null && toBigDecimal(value).compareTo(toBigDecimal(min)) < 0) {
            return min;
        } else if (max != null && toBigDecimal(value).compareTo(toBigDecimal(max)) > 0) {
            return max;
        }
        return value;
    }

    public static Number parse(final CharSequence number) {
        return parse(number, null);
    }

    public static Number parse(final CharSequence number, final Locale locale) {
        return parse(number, DECIMAL_PATTERN, locale);
    }

    public static Number parse(final CharSequence number, final CharSequence pattern, final Locale locale) {
        try {
            return getNumberFormatter(pattern, locale).parse(CharSequenceUtils.castToString(number));
        } catch (ParseException ex) {
            throw new RuntimeParseException(ex);
        }
    }

    public static BigDecimal parseBigDecimal(final CharSequence number) {
        return parseBigDecimal(number, null);
    }

    public static BigDecimal parseBigDecimal(final CharSequence number, final Locale locale) {
        return toBigDecimal(parse(number, locale));
    }

    public static BigDecimal parseBigDecimal(final CharSequence number, final CharSequence pattern, final Locale locale) {
        return toBigDecimal(parse(number, pattern, locale));
    }

    public static Integer parseInteger(final CharSequence number) {
        return parseInteger(number, null);
    }

    public static Integer parseInteger(final CharSequence number, final Locale locale) {
        return parseInteger(number, DECIMAL_PATTERN, locale);
    }

    public static Integer parseInteger(final CharSequence number, final CharSequence pattern, final Locale locale) {
        return toInteger(parse(number, pattern, locale));
    }

    public static Long parseLong(final CharSequence number) {
        return parseLong(number, null);
    }

    public static Long parseLong(final CharSequence number, final Locale locale) {
        return parseLong(number, DECIMAL_PATTERN, locale);
    }

    public static Long parseLong(final CharSequence number, final CharSequence pattern, final Locale locale) {
        return toLong(parse(number, pattern, locale));
    }

    public static Double parseDouble(final CharSequence number) {
        return parseDouble(number, LocaleUtils.PT_BR_LOCALE);
    }

    public static Double parseDouble(final CharSequence number, final Locale locale) {
        return parseDouble(number, DECIMAL_PATTERN, locale);
    }

    public static Double parseDouble(final CharSequence number, final CharSequence pattern, final Locale locale) {
        return toDouble(parse(number, pattern, locale));
    }

    public static Float parseFloat(final CharSequence number) {
        return parseFloat(number, LocaleUtils.PT_BR_LOCALE);
    }

    public static Float parseFloat(final CharSequence number, final Locale locale) {
        return parseFloat(number, DECIMAL_PATTERN, locale);
    }

    public static Float parseFloat(final CharSequence number, final CharSequence pattern, final Locale locale) {
        return toFloat(parse(number, pattern, locale));
    }

    public static Short parseShort(final CharSequence number) {
        return parseShort(number, LocaleUtils.PT_BR_LOCALE);
    }

    public static Short parseShort(final CharSequence number, final Locale locale) {
        return parseShort(number, DECIMAL_PATTERN, locale);
    }

    public static Short parseShort(final CharSequence number, final CharSequence pattern, final Locale locale) {
        return toShort(parse(number, pattern, locale));
    }

    public static Byte parseByte(final CharSequence number) {
        return parseByte(number, LocaleUtils.PT_BR_LOCALE);
    }

    public static Byte parseByte(final CharSequence number, final Locale locale) {
        return parseByte(number, DECIMAL_PATTERN, locale);
    }

    public static Byte parseByte(final CharSequence number, final CharSequence pattern, final Locale locale) {
        return toByte(parse(number, pattern, locale));
    }

    public static <T extends Number> T parseTo(final Class<T> itemClass, final CharSequence number) {
        return parseTo(itemClass, number, DECIMAL_PATTERN, LocaleUtils.PT_BR_LOCALE);
    }

    public static <T extends Number> T parseTo(final Class<T> itemClass, final CharSequence number, final Locale locale) {
        return parseTo(itemClass, number, DECIMAL_PATTERN, LocaleUtils.getNullSafeLocale(locale));
    }

    public static <T extends Number> T parseTo(final Class<T> itemClass, final CharSequence number, final CharSequence pattern, final Locale locale) {
        ArgumentUtils.rejectIfAnyNull(itemClass, pattern, LocaleUtils.getNullSafeLocale(locale));
        if (number == null || itemClass.isAssignableFrom(number.getClass())) {
            return (T) number; // just do a cast (no parse needed)
        } else if (Integer.class.isAssignableFrom(itemClass)) {
            return (T) NumberUtils.parseInteger(number, pattern, locale); // parse integer
        } else if (Long.class.isAssignableFrom(itemClass)) {
            return (T) NumberUtils.parseLong(number, pattern, locale); // parse long
        } else if (Byte.class.isAssignableFrom(itemClass)) {
            return (T) NumberUtils.parseByte(number, pattern, locale); // parse byte
        } else if (Short.class.isAssignableFrom(itemClass)) {
            return (T) NumberUtils.parseShort(number, pattern, locale); // parse short
        } else if (Float.class.isAssignableFrom(itemClass)) {
            return (T) NumberUtils.parseFloat(number, pattern, locale); // parse float
        } else if (Double.class.isAssignableFrom(itemClass)) {
            return (T) NumberUtils.parseDouble(number, pattern, locale); // parse double
        } else if (BigDecimal.class.isAssignableFrom(itemClass)) {
            return (T) NumberUtils.parseBigDecimal(number, pattern, locale); // parse big decimal
        }
        throw new IllegalArgumentException("Conversion from String to " + itemClass + " not supported.");
    }

    public static String format(final Number number) {
        return format(number, null);
    }

    public static String format(final Number number, final Locale locale) {
        return format(number, DECIMAL_PATTERN, locale);
    }

    public static String format(final Number number, final CharSequence pattern, final Locale locale) {
        return getNumberFormatter(pattern, locale).format(number);
    }

    public static NumberFormat getNumberFormatter(final Locale locale) {
        return getNumberFormatter(DECIMAL_PATTERN, locale);
    }

    public static NumberFormat getNumberFormatter(final CharSequence pattern, final Locale locale) {
        final String p = (CharSequenceUtils.isBlankOrNull(pattern)) ? DECIMAL_PATTERN : CharSequenceUtils.castToString(pattern);
        final DecimalFormat numberFormatter = new DecimalFormat(p, getDecimalFormatSymbols(locale));
        numberFormatter.setParseBigDecimal(true);
        return numberFormatter;
    }

    public static DecimalFormatSymbols getDecimalFormatSymbols(final Locale locale) {
        return DecimalFormatSymbols.getInstance(LocaleUtils.getNullSafeLocale(locale));
    }

    public static CharSequence getNullSafePattern(final CharSequence pattern) {
        return CharSequenceUtils.isNotBlank(pattern) ? pattern : DECIMAL_PATTERN;
    }
}
