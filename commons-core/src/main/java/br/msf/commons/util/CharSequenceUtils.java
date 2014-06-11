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
package br.msf.commons.util;

import br.msf.commons.text.CharComparator;
import br.msf.commons.text.CharSequenceComparator;
import br.msf.commons.constants.Constants;
import br.msf.commons.text.EnhancedStringBuilder;
import br.msf.commons.text.MatchEntry;
import br.msf.commons.constants.TextPattern;
import br.msf.commons.text.CharComparator;
import br.msf.commons.text.CharSequenceComparator;
import br.msf.commons.text.EnhancedStringBuilder;
import br.msf.commons.text.MatchEntry;
import br.msf.commons.util.ArgumentUtils;
import br.msf.commons.util.ArrayUtils;
import br.msf.commons.util.CollectionUtils;
import br.msf.commons.util.DateUtils;
import br.msf.commons.util.LocaleUtils;
import br.msf.commons.util.NumberUtils;
import br.msf.commons.util.ObjectUtils;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * @author vbox
 */
public abstract class CharSequenceUtils {

    private static final String[] DEFAULT_CAPITALIZE_EXCLUDES = new String[]{"e", "em", "no", "na", "do", "da", "de", "dos", "das"};
    private static final String CAMELCASE_REGEX = "(?<!(^|[\\p{Lu}0-9]))(?=[\\p{Lu}0-9])"
                                                  + "|(?<!(^|[^\\p{Lu}]))(?=[0-9])"
                                                  + "|(?<!(^|[^0-9]))(?=[\\p{Lu}\\p{Ll}])"
                                                  + "|(?<!^)(?=[\\p{Lu}][\\p{Ll}])"
                                                  + "|([_]+)"
                                                  + "|([\\s]+)";

    public static boolean isCharSequence(final Object value) {
        return ObjectUtils.isCharSequence(value);
    }

    public static boolean isCharSequence(final Object value, final boolean acceptNull) {
        return ObjectUtils.isCharSequence(value, acceptNull);
    }

    public static boolean isString(final Object value) {
        return ObjectUtils.isString(value);
    }

    public static boolean isString(final Object value, final boolean acceptNull) {
        return ObjectUtils.isString(value, acceptNull);
    }

    public static boolean isStringBuilder(final Object value) {
        return ObjectUtils.isStringBuilder(value);
    }

    public static boolean isStringBuilder(final Object value, final boolean acceptNull) {
        return ObjectUtils.isStringBuilder(value, acceptNull);
    }

    public static boolean isStringBuffer(final Object value) {
        return ObjectUtils.isStringBuffer(value);
    }

    public static boolean isStringBuffer(final Object value, final boolean acceptNull) {
        return ObjectUtils.isStringBuffer(value, acceptNull);
    }

    public static boolean isEnhancedStringBuilder(final Object value) {
        return ObjectUtils.isEnhancedStringBuilder(value);
    }

    public static boolean isEnhancedStringBuilder(final Object value, final boolean acceptNull) {
        return ObjectUtils.isEnhancedStringBuilder(value, acceptNull);
    }

    public static StringBuilder castToStringBuilder(final CharSequence sequence) {
        if (sequence != null) {
            return isStringBuilder(sequence) ? (StringBuilder) sequence : new StringBuilder(sequence);
        }
        return null;
    }

    public static StringBuffer castToStringBuffer(final CharSequence sequence) {
        if (sequence != null) {
            return isStringBuffer(sequence) ? (StringBuffer) sequence : new StringBuffer(sequence);
        }
        return null;
    }

    public static String castToString(final CharSequence sequence) {
        if (sequence != null) {
            return isString(sequence) ? (String) sequence : sequence.toString();
        }
        return null;
    }

    public static EnhancedStringBuilder castToEnhancedStringBuilder(final CharSequence sequence) {
        if (sequence != null) {
            return ObjectUtils.isType(EnhancedStringBuilder.class, sequence) ? (EnhancedStringBuilder) sequence : new EnhancedStringBuilder(sequence);
        }
        return null;
    }

    public static boolean isNumber(final CharSequence sequence) {
        return isNumber(sequence, null);
    }

    public static boolean isNumber(final CharSequence sequence, final Locale locale) {
        if (sequence == null) {
            return false;
        }
        try {
            DecimalFormat formater = (DecimalFormat) DecimalFormat.getInstance(LocaleUtils.getNullSafeLocale(locale));
            formater.setParseBigDecimal(true);
            formater.parse(sequence.toString());
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     * Returns the combined length of all non-null given sequences.
     * The null ones are considered zero length.
     * <p/>
     * If no sequence is given (null or empty array), this will return <tt>zero (0)</tt>.
     *
     * @param sequences The sequences to be analysed.
     * @return The sum of the given sequence lengths.
     */
    public static int length(final CharSequence... sequences) {
        int length = 0;
        if (ArrayUtils.isNotEmpty(sequences)) {
            for (CharSequence item : sequences) {
                if (isNotEmpty(item)) {
                    length += item.length();
                }
            }
        }
        return length;
    }

    /**
     * Indicates if the given sequence is a <b>non-null empty</b> one.
     *
     * @param sequence The sequence to be analysed.
     * @return {@code true} if, and only if, the given sequence is a <b>non-null empty</b> one.
     */
    public static boolean isEmpty(final CharSequence sequence) {
        return sequence != null && isEmptyOrNull(sequence);
    }

    /**
     * Indicates if there is <b>at least one non-null empty</b> sequence among the given ones.
     * <p/>
     * If no sequence is given (null or empty array), this will return {@code false}.
     *
     * @param sequences The sequences to be analysed.
     * @return {@code true} if, and only if, there is <b>at least one non-null empty</b> sequence among the given ones.
     */
    public static boolean isAnyEmpty(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = false;
        for (final CharSequence sequence : sequences) {
            value = value || isEmpty(sequence);
            if (value) {
                break;
            }
        }
        return value;
    }

    /**
     * Indicates if <b>all</b> the given sequences are <b>non-null empty</b> ones.
     * <p/>
     * If no sequence is given (null or empty array), this will return {@code false}.
     *
     * @param sequences The sequences to be analysed.
     * @return {@code true} if, and only if, <b>all</b> the given sequences are <b>non-null empty</b> ones.
     */
    public static boolean isAllEmpty(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = true;
        for (final CharSequence sequence : sequences) {
            value = value && isEmpty(sequence);
        }
        return value;
    }

    /**
     * Indicates if the given sequence is <b>null OR empty</b>.
     *
     * @param sequence The sequence to be analysed.
     * @return {@code true} if, and only if, the given sequence is <b>null OR empty</b>.
     */
    public static boolean isEmptyOrNull(final CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }

    /**
     * Indicates if there is <b>at least one null OR empty</b> sequence among the given ones.
     * <p/>
     * If no sequence is given (null or empty array), this will return {@code false}.
     *
     * @param sequences The sequences to be analysed.
     * @return {@code true} if, and only if, there is <b>at least one null OR empty</b> sequence among the given ones.
     */
    public static boolean isAnyEmptyOrNull(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = false;
        for (final CharSequence sequence : sequences) {
            value = value || isEmptyOrNull(sequence);
            if (value) {
                break;
            }
        }
        return value;
    }

    /**
     * Indicates if <b>all</b> the given sequences are <b>null OR empty</b>.
     * <p/>
     * If no sequence is given (null or empty array), this will return {@code false}.
     *
     * @param sequences The sequences to be analysed.
     * @return {@code true} if, and only if, <b>all</b> the given sequences are <b>null OR empty</b>.
     */
    public static boolean isAllEmptyOrNull(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = true;
        for (final CharSequence sequence : sequences) {
            value = value && isEmptyOrNull(sequence);
        }
        return value;
    }

    public static boolean isNotEmpty(final CharSequence sequence) {
        return !isEmptyOrNull(sequence);
    }

    public static boolean isAnyNotEmpty(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = false;
        for (final CharSequence sequence : sequences) {
            value = value || isNotEmpty(sequence);
            if (value) {
                break;
            }
        }
        return value;
    }

    public static boolean isAllNotEmpty(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = true;
        for (final CharSequence sequence : sequences) {
            value = value && isNotEmpty(sequence);
        }
        return value;
    }

    public static boolean isBlank(final CharSequence sequence) {
        return sequence != null && isBlankOrNull(sequence);
    }

    public static boolean isAnyBlank(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = false;
        for (final CharSequence sequence : sequences) {
            value = value || isBlank(sequence);
            if (value) {
                break;
            }
        }
        return value;
    }

    public static boolean isAllBlank(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = true;
        for (final CharSequence sequence : sequences) {
            value = value && isBlank(sequence);
        }
        return value;
    }

    public static boolean isBlankOrNull(final CharSequence sequence) {
        if (isEmptyOrNull(sequence)) {
            return true;
        }
        int first;
        /* finds the first non-space char index */
        for (first = 0; first < sequence.length(); first++) {
            if (!Character.isWhitespace(sequence.charAt(first))) {
                break;
            }
        }
        return first >= sequence.length();
    }

    public static boolean isAnyBlankOrNull(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = false;
        for (final CharSequence sequence : sequences) {
            value = value || isBlankOrNull(sequence);
            if (value) {
                break;
            }
        }
        return value;
    }

    public static boolean isAllBlankOrNull(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = true;
        for (final CharSequence sequence : sequences) {
            value = value && isBlankOrNull(sequence);
        }
        return value;
    }

    public static boolean isNotBlank(final CharSequence sequence) {
        return !isBlankOrNull(sequence);
    }

    public static boolean isAnyNotBlank(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = false;
        for (final CharSequence sequence : sequences) {
            value = value || isNotBlank(sequence);
            if (value) {
                break;
            }
        }
        return value;
    }

    public static boolean isAllNotBlank(final CharSequence... sequences) {
        if (ArrayUtils.isEmptyOrNull(sequences)) {
            return false;
        }
        boolean value = true;
        for (final CharSequence sequence : sequences) {
            value = value && isNotBlank(sequence);
        }
        return value;
    }

    public static boolean isUpperCase(final CharSequence sequence) {
        if (isEmpty(sequence)) {
            return true;
        }
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (c != Character.toUpperCase(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLowerCase(final CharSequence sequence) {
        if (isEmpty(sequence)) {
            return true;
        }
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (c != Character.toLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    public static int compare(final CharSequence sequence1, final CharSequence sequence2) {
        return compare(sequence1, sequence2, Constants.CASE_SENSITIVE);
    }

    public static int compare(final CharSequence sequence1, final CharSequence sequence2, final Boolean caseSensitive) {
        return new CharSequenceComparator(caseSensitive, true).compare(sequence1, sequence2);
    }

    public static boolean equals(final CharSequence sequence1, final CharSequence sequence2) {
        return compare(sequence1, sequence2, Boolean.TRUE) == 0;
    }

    public static boolean equalsIgnoreCase(final CharSequence sequence1, final CharSequence sequence2) {
        return compare(sequence1, sequence2, Boolean.FALSE) == 0;
    }

    public static boolean hasAccents(final CharSequence sequence) {
        if (isBlankOrNull(sequence)) {
            return false;
        }
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (LatinCharacterUtils.isDecoratedLetter(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes accents and graphical symbols from words, like tilde, cute, and so on.
     *
     * @param sequence Sequence witch the its accents must be removed.
     * @return The sequence without accents.
     */
    public static String removeAccents(final CharSequence sequence) {
        return toString(removeAccentsInternal(sequence));
    }

    public static String reverse(final CharSequence sequence) {
        return toString(reverseInternal(sequence));
    }

    public static String toCamelCase(final CharSequence sequence) {
        return toCamelCase(sequence, true, true);
    }

    public static String toCamelCase(final CharSequence sequence, final boolean removeAccents, final boolean capitalizeFirst) {
        if (sequence == null) {
            return null;
        }
        final CharSequence seq = removeAccents ? removeAccents(sequence) : sequence;
        final Collection<String> tokens = split(seq, CAMELCASE_REGEX, true);
        final StringBuilder builder = new StringBuilder(length(seq));
        int i = 0;
        for (final String token : tokens) {
            final String curr = (i > 0 || capitalizeFirst) ? StringUtils.capitalize(token) : token;
            builder.append(curr);
            i++;
        }
        return builder.toString();
    }

    public static String toUnderscore(final CharSequence sequence) {
        return toUnderscore(sequence, true, true, false);
    }

    public static String toUnderscore(final CharSequence sequence, final boolean removeAccents, final boolean upperCased, final boolean separateNumbers) {
        if (sequence == null) {
            return null;
        }
        final CharSequence seq = removeAccents ? removeAccents(sequence) : sequence;
        final Collection<String> tokens = split(seq, CAMELCASE_REGEX, true);
        final StringBuilder builder = new StringBuilder(length(seq));
        int i = 0;
        for (String token : tokens) {
            if (i > 0 && (!isNumber(token) || separateNumbers)) {
                builder.append('_');
            }
            builder.append(token);
            i++;
        }
        return upperCased ? builder.toString().toUpperCase() : builder.toString();
    }

    public static Set<String> listParams(final CharSequence builder) {
        return listParams(builder, Constants.DEFAULT_PARAM_START, Constants.DEFAULT_PARAM_END);
    }

    @SuppressWarnings("unchecked")
    public static Set<String> listParams(final CharSequence sequence, final String startDelimiter, final String endDelimiter) {
        if (isBlankOrNull(sequence)) {
            return Collections.EMPTY_SET;
        }
        String str = sequence.toString();
        int idx0 = str.indexOf(startDelimiter);
        if (idx0 < 0) {
            return Collections.EMPTY_SET;
        }

        Set<String> set = new HashSet<String>();
        while (idx0 >= 0) {
            int idx1 = str.indexOf(endDelimiter, idx0);
            if (idx1 <= idx0) {
                return set;
            }
            set.add(str.substring(idx0 + startDelimiter.length(), idx1));
            idx0 = str.indexOf(startDelimiter, idx1);
        }
        return set;
    }

    public static String toString(final Object sequence) {
        return (sequence != null) ? sequence.toString() : null;
    }

    public static String toNullSafeString(final Object sequence) {
        return (sequence != null) ? sequence.toString() : "";
    }

    public static List<MatchEntry> find(final CharSequence toSearch, final CharSequence sequence) {
        return find(toSearch, sequence, Constants.CASE_SENSITIVE);
    }

    @SuppressWarnings("unchecked")
    public static List<MatchEntry> find(final CharSequence toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        if (isEmptyOrNull(toSearch) || isEmptyOrNull(sequence)) {
            return CollectionUtils.EMPTY_LIST;
        }
        final List<MatchEntry> occurrences = new ArrayList<MatchEntry>();
        int start = indexOf(toSearch, 0, sequence, caseSensitive);
        while (start >= 0) {
            int end = start + toSearch.length();
            occurrences.add(new MatchEntry(start, end));
            start = indexOf(toSearch, end, sequence, caseSensitive);
        }
        return occurrences;
    }

    @SuppressWarnings("unchecked")
    public static List<MatchEntry> findPattern(final CharSequence regex, final CharSequence sequence) {
        return (isEmptyOrNull(regex) || isEmptyOrNull(sequence)) ? Collections.EMPTY_LIST : findPattern(Pattern.compile(regex.toString()), sequence);
    }

    @SuppressWarnings("unchecked")
    public static List<MatchEntry> findPattern(final Pattern pattern, final CharSequence sequence) {
        if (pattern == null || isEmptyOrNull(sequence)) {
            return CollectionUtils.EMPTY_LIST;
        }
        final Matcher matcher = pattern.matcher(sequence);
        final List<MatchEntry> occurrences = new ArrayList<MatchEntry>();
        while (matcher.find()) {
            occurrences.add(new MatchEntry(matcher.start(), matcher.end()));
        }
        return occurrences;
    }

    public static boolean containsPattern(final CharSequence regex, final CharSequence sequence) {
        return CollectionUtils.isNotEmpty(findPattern(regex, sequence));
    }

    public static boolean containsPattern(final Pattern pattern, final CharSequence sequence) {
        return CollectionUtils.isNotEmpty(findPattern(pattern, sequence));
    }

    public static boolean matches(final CharSequence regex, final CharSequence sequence) {
        return matches(regex, sequence, 0);
    }

    public static boolean matches(final CharSequence regex, final CharSequence sequence, final int flags) {
        return matches(Pattern.compile(regex.toString(), flags), sequence);
    }

    public static boolean matches(final Pattern pattern, final CharSequence sequence) {
        if (pattern == null || isEmptyOrNull(sequence)) {
            return false;
        }
        return pattern.matcher(sequence).matches();
    }

    public static boolean matchesAny(final CharSequence[] regexes, final CharSequence sequence) {
        return matchesAny(Arrays.asList(regexes), sequence, 0);
    }

    public static boolean matchesAny(final CharSequence[] regexes, final CharSequence sequence, final int flags) {
        return matchesAny(Arrays.asList(regexes), sequence, flags);
    }

    public static boolean matchesAny(final Collection<? extends CharSequence> regexes, final CharSequence sequence) {
        return matchesAny(regexes, sequence, 0);
    }

    public static boolean matchesAny(final Collection<? extends CharSequence> regexes, final CharSequence sequence, final int flags) {
        for (final CharSequence regex : regexes) {
            if (matches(regex, sequence, flags)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> split(final CharSequence sequence, final CharSequence regex) {
        return split(sequence, regex, false);
    }

    public static List<String> split(final CharSequence sequence, final CharSequence regex, final boolean ignoreBlank) {
        if (isEmptyOrNull(regex)) {
            return split(sequence, (Pattern) null, ignoreBlank);
        } else {
            return split(sequence, Pattern.compile(regex.toString()), ignoreBlank);
        }
    }

    public static List<String> split(final CharSequence sequence, final Pattern pattern) {
        return split(sequence, pattern, false);
    }

    public static List<String> split(final CharSequence sequence, final Pattern pattern, final boolean ignoreBlank) {
        if (isBlankOrNull(sequence)) {
            return CollectionUtils.EMPTY_LIST;
        }
        if (pattern == null) {
            return Collections.singletonList(sequence.toString());
        }
        final Collection<MatchEntry> occurrences = findPattern(pattern, sequence);
        final List<String> split = new ArrayList<String>(occurrences.size() + 1);
        int start = 0;
        for (MatchEntry occurrence : occurrences) {
            final CharSequence sub = sequence.subSequence(start, occurrence.getStart());
            start = occurrence.getEnd();
            if (CharSequenceUtils.isBlankOrNull(sub) && ignoreBlank) {
                continue;
            }
            split.add(sub.toString());
        }
        final CharSequence sub = sequence.subSequence(start, sequence.length());
        if (CharSequenceUtils.isNotBlank(sub) || !ignoreBlank) {
            split.add(sub.toString());
        }
        return split;
    }

    public static int indexOfLastChar(final CharSequence sequence) {
        return (isNotEmpty(sequence)) ? (sequence.length() - 1) : -1;
    }

    public static int indexOf(final CharSequence toSearch, final CharSequence sequence) {
        return indexOf(toSearch, 0, sequence, Constants.CASE_SENSITIVE);
    }

    public static int indexOf(final CharSequence toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        return indexOf(toSearch, 0, sequence, caseSensitive);
    }

    public static int indexOf(final CharSequence toSearch, final int fromIndex, final CharSequence sequence) {
        return indexOf(toSearch, fromIndex, sequence, Constants.CASE_SENSITIVE);
    }

    public static int indexOf(final CharSequence toSearch, final int fromIndex, final CharSequence sequence, final Boolean caseSensitive) {
        return new CharSequenceComparator(caseSensitive).indexOf(toSearch, fromIndex, sequence);
    }

    public static int lastIndexOf(final CharSequence toSearch, final CharSequence sequence) {
        return lastIndexOf(toSearch, length(sequence), sequence, Constants.CASE_SENSITIVE);
    }

    public static int lastIndexOf(final CharSequence toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        return lastIndexOf(toSearch, length(sequence), sequence, caseSensitive);
    }

    public static int lastIndexOf(final CharSequence toSearch, final int fromIndex, final CharSequence sequence) {
        return lastIndexOf(toSearch, fromIndex, sequence, Constants.CASE_SENSITIVE);
    }

    public static int lastIndexOf(final CharSequence toSearch, final int fromIndex, final CharSequence sequence, final Boolean caseSensitive) {
        return new CharSequenceComparator(true).lastIndexOf(toSearch, fromIndex, sequence);
    }

    public static int indexOf(final char toSearch, final CharSequence sequence) {
        return indexOf(toSearch, 0, sequence, Constants.CASE_SENSITIVE);
    }

    public static int indexOf(final char toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        return indexOf(toSearch, 0, sequence, caseSensitive);
    }

    public static int indexOf(final char toSearch, final int fromIndex, final CharSequence sequence) {
        return indexOf(toSearch, fromIndex, sequence, Constants.CASE_SENSITIVE);
    }

    public static int indexOf(final char toSearch, final int fromIndex, final CharSequence sequence, final Boolean caseSensitive) {
        return new CharComparator(caseSensitive).indexOf(toSearch, fromIndex, sequence);
    }

    public static int lastIndexOf(final char toSearch, final CharSequence sequence) {
        return lastIndexOf(toSearch, length(sequence), sequence, Constants.CASE_SENSITIVE);
    }

    public static int lastIndexOf(final char toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        return lastIndexOf(toSearch, length(sequence), sequence, caseSensitive);
    }

    public static int lastIndexOf(final char toSearch, final int fromIndex, final CharSequence sequence) {
        return lastIndexOf(toSearch, fromIndex, sequence, Constants.CASE_SENSITIVE);
    }

    public static int lastIndexOf(final char toSearch, final int fromIndex, final CharSequence sequence, final Boolean caseSensitive) {
        return new CharComparator(true).lastIndexOf(toSearch, fromIndex, sequence);
    }

    public static int countOccurrencesOf(final CharSequence toCount, final CharSequence sequence) {
        return countOccurrencesOf(toCount, 0, sequence, true);
    }

    public static int countOccurrencesOf(final CharSequence toCount, final CharSequence sequence, final Boolean caseSensitive) {
        return countOccurrencesOf(toCount, 0, sequence, caseSensitive);
    }
    
    public static int countOccurrencesOf(final CharSequence toCount, final int fromIndex, final CharSequence sequence) {
        return countOccurrencesOf(toCount, fromIndex, sequence, true);
    }
    
    public static int countOccurrencesOf(final CharSequence toCount, final int fromIndex, final CharSequence sequence, final Boolean caseSensitive) {
        if (isEmpty(toCount)) {
            return isEmpty(sequence) ? 1 : 0;
        }
        int n = 0;
        int idx = indexOf(toCount, fromIndex, sequence, caseSensitive);
        while (idx >= 0) {
            n++;
            idx = indexOf(toCount, idx + 1, sequence, caseSensitive);
        }
        return n;
    }

    
    public static int countOccurrencesOf(final char toCount, final CharSequence sequence) {
        return countOccurrencesOf(toCount, 0, sequence, true);
    }

    public static int countOccurrencesOf(final char toCount, final CharSequence sequence, final Boolean caseSensitive) {
        return countOccurrencesOf(toCount, 0, sequence, caseSensitive);
    }
    
    public static int countOccurrencesOf(final char toCount, final int fromIndex, final CharSequence sequence) {
        return countOccurrencesOf(toCount, fromIndex, sequence, true);
    }
    
    public static int countOccurrencesOf(final char toCount, final int fromIndex, final CharSequence sequence, final Boolean caseSensitive) {
        final Map<Character, Integer> counts = getCharCount(fromIndex, sequence, caseSensitive);
        return counts.containsKey(toCount) ? counts.get(toCount) : 0;
    }
    
    public static boolean contains(final CharSequence toSearch, final CharSequence sequence) {
        return indexOf(toSearch, sequence) >= 0;
    }

    public static boolean startsWith(final CharSequence prefix, final CharSequence sequence) {
        return startsWith(prefix, 0, sequence);
    }

    public static boolean endsWith(final CharSequence suffix, final CharSequence sequence) {
        return startsWith(suffix, length(sequence) - suffix.length(), sequence);
    }

    public static String subStringBeforeFirst(final CharSequence toSearch, final CharSequence sequence) {
        return subStringBeforeFirst(toSearch, sequence, true);
    }

    public static String subStringBeforeFirst(final CharSequence toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        final int idx = indexOf(toSearch, sequence, caseSensitive);
        return (idx < 0) ? null : CharSequenceUtils.castToString(sequence.subSequence(0, idx));
    }

    public static String subStringBeforeLast(final CharSequence toSearch, final CharSequence sequence) {
        return subStringBeforeLast(toSearch, sequence, true);
    }

    public static String subStringBeforeLast(final CharSequence toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        final int idx = lastIndexOf(toSearch, sequence, caseSensitive);
        return (idx < 0) ? null : CharSequenceUtils.castToString(sequence.subSequence(0, idx));
    }

    public static String subStringAfterFirst(final CharSequence toSearch, final CharSequence sequence) {
        return subStringAfterFirst(toSearch, sequence, true);
    }

    public static String subStringAfterFirst(final CharSequence toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        final int idx = indexOf(toSearch, sequence, caseSensitive);
        return (idx < 0) ? null : CharSequenceUtils.castToString(sequence.subSequence(idx + toSearch.length(), length(sequence)));
    }

    public static String subStringAfterLast(final CharSequence toSearch, final CharSequence sequence) {
        return subStringAfterLast(toSearch, sequence, true);
    }

    public static String subStringAfterLast(final CharSequence toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        final int idx = lastIndexOf(toSearch, sequence, caseSensitive);
        return (idx < 0) ? null : CharSequenceUtils.castToString(sequence.subSequence(idx + toSearch.length(), length(sequence)));
    }

    public static Map<Character, Integer> getCharCount(final CharSequence sequence) {
        return getCharCount(0, sequence, true);
    }

    public static Map<Character, Integer> getCharCount(final CharSequence sequence, final Boolean caseSensitive) {
        return getCharCount(0, sequence, caseSensitive);
    }
    
    public static Map<Character, Integer> getCharCount(final int fromIndex, final CharSequence sequence) {
        return getCharCount(fromIndex, sequence, true);
    }
    
    public static Map<Character, Integer> getCharCount(final int fromIndex, final CharSequence sequence, final Boolean caseSensitive) {
        ArgumentUtils.rejectIfOutOfBounds(fromIndex, 0, length(sequence) - 1);
        if (isEmptyOrNull(sequence)) {
            return Collections.EMPTY_MAP;
        }
        final Map<Character, Integer> chars = new TreeMap<Character, Integer>();
        for (int i = fromIndex; i < length(sequence); i++) {
            Character ch = caseSensitive? sequence.charAt(i) : Character.toLowerCase(sequence.charAt(i));
            Integer count = chars.get(ch);
            chars.put(ch, (count == null) ? 1 : count + 1);
        }
        return chars;
    }

    public static char firstChar(final CharSequence sequence) {
        ArgumentUtils.rejectIfNull(sequence);
        return sequence.charAt(0);
    }

    public static char lastChar(final CharSequence sequence) {
        ArgumentUtils.rejectIfNull(sequence);
        return sequence.charAt(length(sequence));
    }

    public static String putLeading(final Object leading, final int n, final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).putLeading(leading, n)).toString();
    }

    public static String putTrailing(final Object trailing, final int n, final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).putTrailing(trailing, n)).toString();
    }

    public static String replace(final MatchEntry coordinates, final Object replacement, final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).replace(coordinates, replacement)).toString();
    }

    public static String replace(final String original, final Object replacement, final String startDelimiter, final String endDelimiter, final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).replace(original, replacement, startDelimiter, endDelimiter)).toString();
    }

    public static String replace(final CharSequence sequence, final String startDelimiter, final String endDelimiter, final Map<? extends String, ?> params) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).replace(startDelimiter, endDelimiter, params)).toString();
    }

    public static String replace(final CharSequence sequence, final String startDelimiter, final String endDelimiter, final Object[] params) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).replace(startDelimiter, endDelimiter, params)).toString();
    }

    public static String replacePlain(final String original, final Object replacement, final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).replacePlain(original, replacement)).toString();
    }

    public static String replacePlain(final CharSequence sequence, final Map<? extends String, ?> params) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).replacePlain(params)).toString();
    }

    public static String replaceParam(final CharSequence sequence, final String paramName, final Object value) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).replaceParam(paramName, value)).toString();
    }

    public static String replaceParams(final CharSequence sequence, final Map<? extends String, ?> params) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).replaceParams(params)).toString();
    }

    public static String replacePattern(final String regex, final Object replacement, final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).replacePattern(regex, replacement)).toString();
    }

    public static String replacePattern(final Pattern pattern, final Object replacement, final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).replacePattern(pattern, replacement)).toString();
    }

    public static String replacePattern(final CharSequence sequence, final Map<CharSequence, ?> patterns) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).replacePattern(patterns)).toString();
    }

    public static String deletePattern(final String regex, final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).deletePattern(regex)).toString();
    }

    public static String deletePattern(final Pattern pattern, final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).deletePattern(pattern)).toString();
    }

    public static String ltrim(final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).ltrim()).toString();
    }

    public static String rtrim(final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).rtrim()).toString();
    }

    public static String mtrim(final CharSequence sequence) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).mtrim()).toString();
    }

    public static String ltrim(final CharSequence sequence, final boolean multiLine) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).ltrim(multiLine)).toString();
    }

    public static String rtrim(final CharSequence sequence, final boolean multiLine) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).rtrim(multiLine)).toString();
    }

    public static String mtrim(final CharSequence sequence, final boolean multiLine) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).mtrim(multiLine)).toString();
    }

    public static String toUpperCase(final CharSequence sequence) {
        return toUpperCase(sequence, 0, length(sequence));
    }
    
    public static String toUpperCase(final CharSequence sequence, final int start, final int end) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).toUpperCase(start, end)).toString();
    }

    public static String toLowerCase(final CharSequence sequence) {
        return toLowerCase(sequence, 0, length(sequence));
    }
    
    public static String toLowerCase(final CharSequence sequence, final int start, final int end) {
        return sequence == null ? null : (new EnhancedStringBuilder(sequence).toLowerCase(start, end)).toString();
    }

    public static String substringBeforeFirst(final CharSequence toSearch, final CharSequence sequence) {
        return subStringBeforeFirst(toSearch, sequence, true);
    }

    public static String substringBeforeFirst(final CharSequence toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        final int idx = indexOf(toSearch, sequence, caseSensitive);
        return (idx < 0) ? null : CharSequenceUtils.castToString(sequence.subSequence(0, idx));
    }

    public static String substringBeforeLast(final CharSequence toSearch, final CharSequence sequence) {
        return subStringBeforeLast(toSearch, sequence, true);
    }

    public static String substringBeforeLast(final CharSequence toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        final int idx = lastIndexOf(toSearch, sequence, caseSensitive);
        return (idx < 0) ? null : CharSequenceUtils.castToString(sequence.subSequence(0, idx));
    }

    public static String substringAfterFirst(final CharSequence toSearch, final CharSequence sequence) {
        return subStringAfterFirst(toSearch, sequence, true);
    }

    public static String substringAfterFirst(final CharSequence toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        final int idx = indexOf(toSearch, sequence, caseSensitive);
        return (idx < 0) ? null : CharSequenceUtils.castToString(sequence.subSequence(idx + toSearch.length(), length(sequence)));
    }

    public static String substringAfterLast(final CharSequence toSearch, final CharSequence sequence) {
        return subStringAfterLast(toSearch, sequence, true);
    }

    public static String substringAfterLast(final CharSequence toSearch, final CharSequence sequence, final Boolean caseSensitive) {
        final int idx = lastIndexOf(toSearch, sequence, caseSensitive);
        return (idx < 0) ? null : CharSequenceUtils.castToString(sequence.subSequence(idx + toSearch.length(), length(sequence)));
    }

    public static boolean isSmaller(final CharSequence sequence0, final CharSequence sequence1) {
        return length(sequence0) < length(sequence1);
    }

    public static boolean isBigger(final CharSequence sequence0, final CharSequence sequence1) {
        return length(sequence0) > length(sequence1);
    }

    public static boolean isSameOrSmaller(final CharSequence sequence0, final CharSequence sequence1) {
        return length(sequence0) <= length(sequence1);
    }

    public static boolean isSameOrBigger(final CharSequence sequence0, final CharSequence sequence1) {
        return length(sequence0) >= length(sequence1);
    }

    public static boolean isSameLength(final CharSequence sequence0, final CharSequence sequence1) {
        return length(sequence0) == length(sequence1);
    }

    public static boolean isLengthBetween(final CharSequence sequence, final int minLength, final int maxLength) {
        final int len = CharSequenceUtils.length(sequence); // null-safe length()
        return (len >= minLength) && (len <= maxLength);
    }

    public static boolean isInsideBounds(final CharSequence sequence, final int index) {
        return index >= 0 && index < length(sequence);
    }

    public static boolean hasLetter(final CharSequence sequence) {
        return isNotBlank(sequence) ? containsPattern(TextPattern.LETTERS, sequence) : false;
    }

    public static boolean hasLowercase(final CharSequence sequence) {
        return isNotBlank(sequence) ? containsPattern(TextPattern.LOWERCASES, sequence) : false;
    }

    public static boolean hasUppercase(final CharSequence sequence) {
        return isNotBlank(sequence) ? containsPattern(TextPattern.UPPERCASES, sequence) : false;
    }

    public static boolean hasDigit(final CharSequence sequence) {
        return isNotBlank(sequence) ? containsPattern(TextPattern.DIGITS, sequence) : false;
    }

    public static boolean hasSpace(final CharSequence sequence) {
        return isNotEmpty(sequence) ? containsPattern(TextPattern.SPACES, sequence) : false;
    }

    public static boolean hasSymbol(final CharSequence sequence) {
        return isNotBlank(sequence) ? containsPattern(TextPattern.SIMBOLS, sequence) : false;
    }

    public static boolean hasPunctuation(final CharSequence sequence) {
        return isNotBlank(sequence) ? containsPattern(TextPattern.PUNCTUATIONS, sequence) : false;
    }

    public static boolean hasVowel(final CharSequence sequence) {
        if (isBlankOrNull(sequence)) {
            return false;
        }
        final StringBuilder builder = removeAccentsInternal(sequence);
        for (int i = 0; i < length(builder); i++) {
            if (isVowel(builder.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasConsonant(final CharSequence sequence) {
        if (isBlankOrNull(sequence)) {
            return false;
        }
        final StringBuilder builder = removeAccentsInternal(sequence);
        for (int i = 0; i < length(builder); i++) {
            if (isConsonant(builder.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static int countRepetitionChain(final CharSequence sequence) {
        if (length(sequence) < 2) {
            return length(sequence);
        }
        int max = 1;
        int count = 1;
        char lookupChar = sequence.charAt(0);
        for (int i = 1; i < sequence.length(); i++) {
            if (sequence.charAt(i) == lookupChar) {
                count++;
            } else {
                lookupChar = sequence.charAt(i);
                if (count > max) {
                    max = count;
                }
                count = 1;
            }
        }
        if (count > max) {
            max = count;
        }
        return max;
    }

    public static int countLowercaseChain(final CharSequence sequence) {
        if (isBlankOrNull(sequence)) {
            return 0;
        } else if (length(sequence) == 1) {
            return hasLowercase(sequence) ? 1 : 0;
        }
        int max = 0;
        int count = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (Character.isLowerCase(sequence.charAt(i))) {
                count++;
            } else {
                if (count > max) {
                    max = count;
                }
                count = 0;
            }
        }
        if (count > max) {
            max = count;
        }
        return max;
    }

    public static int countUppercaseChain(final CharSequence sequence) {
        if (isBlankOrNull(sequence)) {
            return 0;
        } else if (length(sequence) == 1) {
            return hasUppercase(sequence) ? 1 : 0;
        }
        int max = 0;
        int count = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (Character.isUpperCase(sequence.charAt(i))) {
                count++;
            } else {
                if (count > max) {
                    max = count;
                }
                count = 0;
            }
        }
        if (count > max) {
            max = count;
        }
        return max;
    }

    public static int countDigitChain(final CharSequence sequence) {
        if (isBlankOrNull(sequence)) {
            return 0;
        } else if (length(sequence) == 1) {
            return hasDigit(sequence) ? 1 : 0;
        }
        int max = 0;
        int count = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (Character.isDigit(sequence.charAt(i))) {
                count++;
            } else {
                if (count > max) {
                    max = count;
                }
                count = 0;
            }
        }
        if (count > max) {
            max = count;
        }
        return max;
    }

    public static int countVowelChain(final CharSequence sequence) {
        if (isBlankOrNull(sequence)) {
            return 0;
        } else if (length(sequence) == 1) {
            return hasVowel(sequence) ? 1 : 0;
        }
        int max = 0;
        int count = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (isVowel(sequence.charAt(i))) {
                count++;
            } else {
                if (count > max) {
                    max = count;
                }
                count = 0;
            }
        }
        if (count > max) {
            max = count;
        }
        return max;
    }

    public static int countConsonantChain(final CharSequence sequence) {
        if (isBlankOrNull(sequence)) {
            return 0;
        } else if (length(sequence) == 1) {
            return hasConsonant(sequence) ? 1 : 0;
        }
        int max = 0;
        int count = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (isConsonant(sequence.charAt(i))) {
                count++;
            } else {
                if (count > max) {
                    max = count;
                }
                count = 0;
            }
        }
        if (count > max) {
            max = count;
        }
        return max;
    }

    public static int countAscendingChain(final CharSequence sequence) {
        if (length(sequence) < 2) {
            return length(sequence);
        }
        int max = 1;
        int count = 1;
        for (int i = 1; i < sequence.length(); i++) {
            if (sequence.charAt(i) == (sequence.charAt(i - 1) + 1)) {
                count++;
            } else {
                if (count > max) {
                    max = count;
                }
                count = 1;
            }
        }
        if (count > max) {
            max = count;
        }
        return max;
    }

    public static int countDescendingChain(final CharSequence sequence) {
        if (length(sequence) < 2) {
            return length(sequence);
        }
        int max = 1;
        int count = 1;
        for (int i = 1; i < sequence.length(); i++) {
            if (sequence.charAt(i) == (sequence.charAt(i - 1) - 1)) {
                count++;
            } else {
                if (count > max) {
                    max = count;
                }
                count = 1;
            }
        }
        if (count > max) {
            max = count;
        }
        return max;
    }

    public static String toFormattedString(final Object value) {
        if (value == null) {
            return null;
        }
        if (isCharSequence(value)) {
            return castToString((CharSequence) value);
        } else if (ObjectUtils.isNumber(value)) {
            return NumberUtils.format((Number) value);
        } else if (ObjectUtils.isDate(value) || ObjectUtils.isCalendar(value)) {
            return DateUtils.formatDate(value);
        } else if (ObjectUtils.isClass(value)) {
            return ((Class) value).getName();
        } else if (ObjectUtils.isCollection(value)) {
            return CollectionUtils.toString((Collection) value, ", ");
        } else if (ObjectUtils.isArray(value)) {
            return ArrayUtils.toString(value, ", ");
        } else {
            return toString(value);
        }
    }

    public static String toNullSafeFormattedString(final Object value) {
        return (value != null) ? toFormattedString(value) : "";
    }

    private static boolean isVowel(final char c) {
        final StringBuilder builder = new StringBuilder();
        builder.append(LatinCharacterUtils.undecorateLetter(c));
        return matches(TextPattern.VOWELS, builder);
    }

    private static boolean isConsonant(final char c) {
        final StringBuilder builder = new StringBuilder();
        builder.append(LatinCharacterUtils.undecorateLetter(c));
        return matches(TextPattern.CONSONANTS, builder);
    }

    private static boolean startsWith(CharSequence prefix, final int offset, final CharSequence sequence) {
        if (isEmptyOrNull(prefix) || isEmptyOrNull(sequence) || prefix.length() > sequence.length()) {
            return false;
        }
        int i = offset, j = 0;
        int pLen = prefix.length();
        while (--pLen >= 0) {
            if (sequence.charAt(i++) != prefix.charAt(j++)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes accents and graphical symbols from words, like tilde, cute, and so on.
     *
     * @param sequence Sequence witch the its accents must be removed.
     * @return The sequence without accents.
     */
    protected static StringBuilder removeAccentsInternal(final CharSequence sequence) {
        if (sequence == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder(sequence.length());
        for (int i = 0; i < sequence.length(); i++) {
            builder.append(LatinCharacterUtils.undecorateLetter(sequence.charAt(i)));
        }
        return builder;
    }

    /**
     * Removes accents and graphical symbols from words, like tilde, cute, and so on.
     *
     * @param sequence Sequence witch the its accents must be removed.
     * @return The sequence without accents.
     */
    protected static StringBuilder reverseInternal(final CharSequence sequence) {
        if (sequence == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder(sequence.length());
        for (int i = indexOfLastChar(sequence); i >= 0; i--) {
            builder.append(sequence.charAt(i));
        }
        return builder;
    }
}
