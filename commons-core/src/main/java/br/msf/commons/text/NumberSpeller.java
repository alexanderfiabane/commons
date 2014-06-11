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
package br.msf.commons.text;

import br.msf.commons.math.exception.RuntimeParseException;
import br.msf.commons.util.ArgumentUtils;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.ULocale;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Spell numbers in full text.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com.br)
 * @version 1.0
 */
public class NumberSpeller {

    /**
     * The ICU rule name to spell out cardinal numbers.
     */
    protected final String cardinalRule;
    /**
     * The ICU rule name to spell out ordinal numbers.
     */
    protected final String ordinalRule;
    /**
     * The NumberFormatter responsible for spelling and parsing spelled out numbers.
     */
    protected final RuleBasedNumberFormat rbnf;

    /**
     * Creates a new NumberSpeller with the default locale.
     */
    public NumberSpeller() {
        this(Locale.getDefault());
    }

    /**
     * Creates a new NumberSpeller with the given locale.
     *
     * @param locale The locale to use while spelling out numbers.
     *
     * @throws IllegalArgumentException If the given locale is null.
     */
    public NumberSpeller(final Locale locale) {
        ArgumentUtils.rejectIfNull(locale);
        this.rbnf = new RuleBasedNumberFormat(locale, RuleBasedNumberFormat.SPELLOUT);
        this.cardinalRule = "%spellout-numbering";
        this.ordinalRule = getOrdinalRuleName(rbnf);
    }

    /**
     * Spells the given number as a cardinal one.
     * <p/>
     * Ex: an English NumberSpeller would work like:
     * <blockquote>
     * "9" results "nine";<br/>
     * "25" results "twenty-five";
     * </blockquote>
     * etc.
     *
     * @param number The number to be spelled.
     * @return The full spelled cardinal number.
     */
    public String spellCardinal(final Number number) {
        rbnf.setDefaultRuleSet(cardinalRule);
        String s = rbnf.format(number);
        if (rbnf.getLocale(ULocale.ACTUAL_LOCALE).toString().startsWith("pt")) {
            // workaround to a typo bug on ICU4j on portuguese languages.
            s = s.replaceAll("\\bdezasseis\\b", "dezesseis").
                    replaceAll("\\bdezassete\\b", "dezessete").
                    replaceAll("\\bdezanove\\b", "dezenove").
                    replaceAll("\\bduzcentos\\b", "duzentos");
        }
        return s;
    }

    /**
     * Spells the given number as a ordinal one.
     * <p/>
     * Ex: an English NumberSpeller would work like:
     * <blockquote>
     * "9" results "ninth";<br/>
     * "25" results "twenty-fifth";
     * </blockquote>
     * etc.
     *
     * @param number The number to be spelled.
     * @return The full spelled ordinal number.
     */
    public String spellOrdinal(final Number number) {
        rbnf.setDefaultRuleSet(ordinalRule);
        return rbnf.format(number);
    }

    /**
     * Parse a spelled out cardinal number to a Number instance.
     * <p/>
     * Ex: an English NumberSpeller would work like:
     * <blockquote>
     * "nine" results "9";<br/>
     * "twenty-five" results "25";
     * </blockquote>
     * etc.
     *
     * @param number The spelled out number to parsed.
     * @return The parsed cardinal number.
     */
    public Number parseCardinal(final String number) {
        try {
            rbnf.setDefaultRuleSet(cardinalRule);
            return rbnf.parse(number);
        } catch (ParseException ex) {
            throw new RuntimeParseException(ex);
        }
    }

    /**
     * Parse a spelled out ordinal number to a Number instance.
     * <p/>
     * Ex: an English NumberSpeller would work like:
     * <blockquote>
     * "ninth" results "9";<br/>
     * "twenty-fifth" results "25";
     * </blockquote>
     * etc.
     *
     * @param number The spelled out number to parsed.
     * @return The parsed ordinal number.
     */
    public Number parseOrdinal(final String number) {
        try {
            rbnf.setDefaultRuleSet(ordinalRule);
            return rbnf.parse(number);
        } catch (ParseException ex) {
            throw new RuntimeParseException(ex);
        }
    }

    /**
     * Try to extract the rule name for "ordinal spell out" from the given RuleBasedNumberFormat.
     * <p/>
     * The rule name is locale sensitive, but usually starts with "%spellout-ordinal".
     *
     * @param rbnf The RuleBasedNumberFormat from where we will try to extract the rule name.
     * @return The rule name for "ordinal spell out".
     */
    protected static String getOrdinalRuleName(final RuleBasedNumberFormat rbnf) {
        List<String> l = Arrays.asList(rbnf.getRuleSetNames());
        if (l.contains("%spellout-ordinal")) {
            return "%spellout-ordinal";
        } else if (l.contains("%spellout-ordinal-masculine")) {
            return "%spellout-ordinal-masculine";
        } else {
            for (String string : l) {
                if (string.startsWith("%spellout-ordinal")) {
                    return string;
                }
            }
        }
        throw new UnsupportedOperationException("The locale "
                                                + rbnf.getLocale(ULocale.ACTUAL_LOCALE)
                                                + " doesn't supports ordinal spelling.");
    }

    /**
     * Returns a new instance of NumberFormat, using the default locale.
     *
     * @return A new instance of NumberFormat, using the default locale.
     */
    public static NumberSpeller getInstance() {
        return new NumberSpeller();
    }

    /**
     * Returns a new instance of NumberFormat, using the given locale.
     *
     * @param locale
     * @return A new instance of NumberFormat, using the given locale.
     */
    public static NumberSpeller getInstance(final Locale locale) {
        return new NumberSpeller(locale);
    }

    /**
     * Static method to spell the given number as a cardinal one.
     * <p/>
     * Ex: an English NumberSpeller would work like:
     * <blockquote>
     * "9" results "nine";<br/>
     * "25" results "twenty-five";
     * </blockquote>
     * etc.
     *
     * @param number The number to be spelled.
     * @param locale The locale to use while spelling out numbers.
     * @return The full spelled cardinal number.
     */
    public static String spellCardinal(final Number number, final Locale locale) {
        return getInstance(locale).spellCardinal(number);
    }

    /**
     * Static method to spell the given number as a ordinal one.
     * <p/>
     * Ex: an English NumberSpeller would work like:
     * <blockquote>
     * "9" results "ninth";<br/>
     * "25" results "twenty-fifth";
     * </blockquote>
     * etc.
     *
     * @param number The number to be spelled.
     * @param locale The locale to use while spelling out numbers.
     * @return The full spelled ordinal number.
     */
    public static String spellOrdinal(final Number number, final Locale locale) {
        return getInstance(locale).spellOrdinal(number);
    }
}
