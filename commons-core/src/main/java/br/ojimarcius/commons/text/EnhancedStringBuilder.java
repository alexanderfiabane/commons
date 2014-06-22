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
package br.ojimarcius.commons.text;

import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.LatinCharacterUtils;
import br.ojimarcius.commons.constants.Constants;
import br.ojimarcius.commons.util.ArgumentUtils;
import br.ojimarcius.commons.util.ArrayUtils;
import br.ojimarcius.commons.util.CollectionUtils;
import br.ojimarcius.commons.util.DateUtils;
import br.ojimarcius.commons.util.NumberUtils;
import br.ojimarcius.commons.util.ObjectUtils;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * A StringBuilder Wrapper that adds some new useful methods to the Java's standard {@link StringBuilder}.
 * <p/>
 * Uses the Delegation design pattern, where every method is dispatched to the delegate object, witch can
 * be accessed via <tt>this.getDelegate()</tt>.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public class EnhancedStringBuilder implements Iterable<Character>, CharSequence, Appendable, Serializable, Comparable<CharSequence> {

    private static final long serialVersionUID = -8028376141448717457L;
    private static final String CAPITALIZE_ALL_REGEX = "[\\s\\p{P}\\p{S}]+";
    private static final String CAPITALIZE_FIRST_REGEX = "^[\\s\\p{P}\\p{S}]+|[.!?]+[\\s\\p{P}\\p{S}]*";
    private static final String TOKENIZER_REGEX = "(?<!(^|[\\p{Lu}0-9]))(?=[\\p{Lu}0-9])"
                                                  + "|(?<!(^|[^\\p{Lu}]))(?=[0-9])"
                                                  + "|(?<!(^|[^0-9]))(?=[\\p{Lu}\\p{Ll}])"
                                                  + "|(?<!^)(?=[\\p{Lu}][\\p{Ll}])"
                                                  + "|([_]+)"
                                                  + "|([\\s]+)";

    /**
     * A list of conjunctions on the Brazilian Portuguese language, witch are ignored on the capitalize process.
     */
    static final Collection<String> IGNORE_LIST_PT = Arrays.asList("o", "a", "os", "as", "do", "da", "dos", "das", "em", "no", "na", "nos", "nas", "de");
    /**
     * A list of conjunctions on the English language, witch are ignored on the capitalize process.
     */
    static final Collection<String> IGNORE_LIST_EN = Arrays.asList("the", "of", "in", "on", "at", "as");
    /**
     * Regex pattern used to left trim the sequence as a whole (SL stands for Single Line).
     */
    static final Pattern SL_LTRIM = Pattern.compile("^[\\s]+");
    /**
     * Regex pattern used to right trim the sequence as a whole (SL stands for Single Line).
     */
    static final Pattern SL_RTRIM = Pattern.compile("[\\s]+$");
    /**
     * Regex pattern used to left and right trim the sequence as a whole (SL stands for Single Line).
     */
    static final Pattern SL_LRTRIM = Pattern.compile("^[\\s]+|[\\s]+$");
    /**
     * Regex pattern used to middle trim (process also known as space normalization) the sequence as a whole (SL stands for Single Line).
     */
    static final Pattern SL_MTRIM = Pattern.compile("(?<=\\S)(\\s+)(?=\\S)");
    /**
     * Regex pattern used to left trim the sequence, line by line (ML stands for Multi Line).
     */
    static final Pattern ML_LTRIM = Pattern.compile("^[ \\f\\t]+", Pattern.MULTILINE);
    /**
     * Regex pattern used to right trim the sequence, line by line (ML stands for Multi Line).
     */
    static final Pattern ML_RTRIM = Pattern.compile("[ \\f\\t]+$", Pattern.MULTILINE);
    /**
     * Regex pattern used to left and right trim the sequence, line by line (ML stands for Multi Line).
     */
    static final Pattern ML_LRTRIM = Pattern.compile("^[ \\f\\t]+|[ \\f\\t]+$", Pattern.MULTILINE);
    /**
     * Regex pattern used to middle trim (process also known as space normalization) the sequence, line by line (ML stands for Multi Line).
     */
    static final Pattern ML_MTRIM = Pattern.compile("(?<=\\S)([ \\f\\t]+)(?=\\S)", Pattern.MULTILINE);

    /**
     * The underlying StringBuilder, whose all know methods are delegated and the new ones takes place.
     */
    protected final StringBuilder delegate;
    /**
     * The line break mode, used by <tt>this.appendln()</tt> methods.
     */
    protected LineBreakMode lineBreakMode = LineBreakMode.SYSTEM_DETECT;

    /**
     * Default constructor.
     * <p/>
     * Creates a new, empty, underlying StringBuilder.
     */
    public EnhancedStringBuilder() {
        this.delegate = new StringBuilder();
    }

    /**
     * Constructor that defines the initial capacity.
     * <p/>
     * Creates a new, empty, underlying StringBuilder, with the given capacity.
     *
     * @param capacity The initial capacity to the underlying StringBuilder.
     */
    public EnhancedStringBuilder(final int capacity) {
        ArgumentUtils.rejectIfLessEquals(capacity, 0);
        this.delegate = new StringBuilder(capacity);
    }

    /**
     * Constructor that defines the initial content.
     * <p/>
     * Creates a new underlying StringBuilder, with the given content.
     *
     * @param string The initial content of the underlying StringBuilder, in String format.
     */
    public EnhancedStringBuilder(final Object string) {
        this.delegate = (string != null) ? new StringBuilder(format(string)) : new StringBuilder();
    }

    /**
     * Constructor that defines the wrapped StringBuilder.
     * <p/>
     * Sets the given StringBuilder as the underlying.
     * <p/>
     * The given StringBuilder will be modified by calls to this wrapper.
     *
     * @param delegate The StringBuilder to be used as wrapped object.
     */
    public EnhancedStringBuilder(final StringBuilder delegate) {
        this.delegate = (delegate != null) ? delegate : new StringBuilder();
    }

    /**
     * Returns the underlying StringBuilder.
     *
     * @return The underlying, wrapped StringBuilder.
     */
    public StringBuilder getDelegate() {
        return delegate;
    }

    public String getLineBreak() {
        return lineBreakMode.getLineBreakString();
    }

    public EnhancedStringBuilder setLineBreakMode(final LineBreakMode lineBreakMode) {
        this.lineBreakMode = lineBreakMode;
        return this;
    }

    public EnhancedStringBuilder trim() {
        return trim(false);
    }

    public EnhancedStringBuilder ltrim() {
        return ltrim(false);
    }

    public EnhancedStringBuilder rtrim() {
        return rtrim(false);
    }

    public EnhancedStringBuilder mtrim() {
        return mtrim(false);
    }

    public EnhancedStringBuilder trim(final boolean multiLine) {
        return (this.isEmpty()) ? this : deletePattern(multiLine ? ML_LRTRIM : SL_LRTRIM);
    }

    public EnhancedStringBuilder ltrim(final boolean multiLine) {
        return (this.isEmpty()) ? this : deletePattern(multiLine ? ML_LTRIM : SL_LTRIM);
    }

    public EnhancedStringBuilder rtrim(final boolean multiLine) {
        return (this.isEmpty()) ? this : deletePattern(multiLine ? ML_RTRIM : SL_RTRIM);
    }

    public EnhancedStringBuilder mtrim(final boolean multiLine) {
        return (this.isEmpty()) ? this : replacePattern(multiLine ? ML_MTRIM : SL_MTRIM, " ");
    }

    public EnhancedStringBuilder clear() {
        delegate.delete(0, length());
        return this;
    }

    public boolean isEmpty() {
        return CharSequenceUtils.isEmpty(delegate);
    }

    public boolean isNotEmpty() {
        return CharSequenceUtils.isNotEmpty(delegate);
    }

    public boolean isBlank() {
        return CharSequenceUtils.isBlank(delegate);
    }

    public boolean isNotBlank() {
        return CharSequenceUtils.isNotBlank(delegate);
    }

    public boolean isSmallerThan(final CharSequence sequence) {
        return CharSequenceUtils.isSmaller(delegate, sequence);
    }

    public boolean isBiggerThan(final CharSequence sequence) {
        return CharSequenceUtils.isBigger(delegate, sequence);
    }

    public boolean isSameOrSmallerThan(final CharSequence sequence) {
        return CharSequenceUtils.isSameOrSmaller(delegate, sequence);
    }

    public boolean isSameOrBiggerThan(final CharSequence sequence) {
        return CharSequenceUtils.isSameOrBigger(delegate, sequence);
    }

    public boolean isSameLengthThan(final CharSequence sequence) {
        return CharSequenceUtils.isSameLength(delegate, sequence);
    }

    public boolean isLengthBetween(final int low, final int high) {
        return CharSequenceUtils.isLengthBetween(delegate, low, high);
    }

    public boolean isInsideBounds(final int index) {
        return CharSequenceUtils.isInsideBounds(delegate, index);
    }

    public boolean containsPattern(final CharSequence regex) {
        return CharSequenceUtils.containsPattern(regex, delegate);
    }

    public boolean containsPattern(final Pattern pattern) {
        return CharSequenceUtils.containsPattern(pattern, delegate);
    }

    public boolean hasAccents() {
        return CharSequenceUtils.hasAccents(delegate);
    }

    public EnhancedStringBuilder append(final CharSequence sequence) {
        return append((Object) sequence);
    }

    public EnhancedStringBuilder append(final CharSequence sequence, final int start, final int end) {
        return append((Object) sequence, start, end);
    }

    public EnhancedStringBuilder append(final char c) {
        return append((Object) c);
    }

    public EnhancedStringBuilder append(final Object... sequences) {
        if (sequences != null) {
            for (Object sequence : sequences) {
                if (sequence != null) {
                    delegate.append(format(sequence));
                }
            }
        }
        return this;
    }

    public EnhancedStringBuilder append(final Object sequence, final int start, final int end) {
        if (sequence != null) {
            delegate.append(format(sequence), start, end);
        }
        return this;
    }

    public EnhancedStringBuilder append(final char[] chars, final int offset, final int len) {
        if (chars != null && chars.length > 0) {
            delegate.append(chars, offset, len);
        }
        return this;
    }

    public EnhancedStringBuilder append(final boolean condition, final Object sequenceIfTrue, final Object sequenceIfFalse) {
        return condition ? this.append(sequenceIfTrue) : this.append(sequenceIfFalse);
    }

    public EnhancedStringBuilder appendIfTrue(final boolean condition, final Object... sequences) {
        return condition ? this.append(sequences) : this;
    }

    public EnhancedStringBuilder appendIfFalse(final boolean condition, final Object... sequences) {
        return !condition ? this.append(sequences) : this;
    }

    public EnhancedStringBuilder appendIfNotBlank(final CharSequence sequence) {
        return (CharSequenceUtils.isBlankOrNull(sequence)) ? this : this.append(sequence);
    }

    public EnhancedStringBuilder appendIfDontEndWith(final CharSequence sequence) {
        if (CharSequenceUtils.isNotEmpty(sequence)) {
            if (isSmallerThan(sequence) || !endsWith(sequence)) {
                this.append(sequence);
            }
        }
        return this;
    }

    public EnhancedStringBuilder appendln(final Object... sequences) {
        return this.append(sequences).append(lineBreakMode.getLineBreakString());
    }

    public EnhancedStringBuilder appendln(final Object sequence, final int start, final int end) {
        return this.append(sequence, start, end).appendln();

    }

    public EnhancedStringBuilder appendln(final char[] chars, final int offset, final int len) {
        return this.append(chars, offset, len).appendln();
    }

    public EnhancedStringBuilder appendln(final boolean condition, final Object sequenceIfTrue, final Object sequenceIfFalse) {
        return condition ? this.appendln(sequenceIfTrue) : this.appendln(sequenceIfFalse);
    }

    public EnhancedStringBuilder appendlnIfTrue(final boolean condition, final Object... sequences) {
        return condition ? this.appendln(sequences) : this;
    }

    public EnhancedStringBuilder appendlnIfFalse(final boolean condition, final Object... sequences) {
        return !condition ? this.appendln(sequences) : this;
    }

    public EnhancedStringBuilder appendlnIfNotBlank(final CharSequence sequence) {
        return (CharSequenceUtils.isBlankOrNull(sequence)) ? this : this.appendln(sequence);
    }

    public EnhancedStringBuilder appendlnIfDontEndWith(final CharSequence sequence) {
        if (CharSequenceUtils.isNotEmpty(sequence)) {
            if (isSmallerThan(sequence) || !endsWith(sequence)) {
                this.appendln(sequence);
            }
        }
        return this;
    }

    public EnhancedStringBuilder appendCodePoint(final int codePoint) {
        delegate.appendCodePoint(codePoint);
        return this;
    }

    public EnhancedStringBuilder putLeading(final Object leading, final int n) {
        if (n <= 0 || CharSequenceUtils.isEmptyOrNull(format(leading))) {
            return this;
        }
        final StringBuilder leadingSeq = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            leadingSeq.append(leading);
        }
        return this.insert(0, leadingSeq);
    }

    public EnhancedStringBuilder putTrailing(final Object trailing, final int n) {
        if (n <= 0 || CharSequenceUtils.isEmptyOrNull(format(trailing))) {
            return this;
        }
        final StringBuilder trailingSeq = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            trailingSeq.append(trailing);
        }
        return this.append(trailingSeq);
    }

    public EnhancedStringBuilder insert(final int offset, final Object obj) {
        if (obj != null) {
            delegate.insert(offset, obj);
        }
        return this;
    }

    public EnhancedStringBuilder insert(final int offset, final Object sequence, final int start, final int end) {
        if (sequence != null) {
            delegate.insert(offset, format(sequence), start, end);
        }
        return this;
    }

    public EnhancedStringBuilder insert(final int index, final char[] chars, final int offset, final int len) {
        if (chars != null && chars.length > 0) {
            delegate.insert(index, chars, offset, len);
        }
        return this;
    }

    public EnhancedStringBuilder removeAccents() {
        for (int i = 0; i < delegate.length(); i++) {
            delegate.setCharAt(i, LatinCharacterUtils.undecorateLetter(delegate.charAt(i)));
        }
        return this;
    }

    public EnhancedStringBuilder delete(final int start, final int end) {
        delegate.delete(start, end);
        return this;
    }

    public EnhancedStringBuilder delete(final CharSequence toDelete) {
        return delete(toDelete, Constants.CASE_SENSITIVE);
    }

    public EnhancedStringBuilder delete(final CharSequence toDelete, final Boolean caseSensitive) {
        if (CharSequenceUtils.isEmptyOrNull(toDelete) || this.isEmpty()) {
            return this;
        }
        final List<MatchEntry> occurrences = new ArrayList<MatchEntry>(find(toDelete, caseSensitive));
        Collections.reverse(occurrences);
        for (MatchEntry occurrence : occurrences) {
            delegate.delete(occurrence.getStart(), occurrence.getEnd());
        }
        return this;
    }

    public EnhancedStringBuilder deleteCharAt(final int index) {
        delegate.deleteCharAt(index);
        return this;
    }

    public EnhancedStringBuilder deletePattern(final CharSequence regex) {
        return (CharSequenceUtils.isEmptyOrNull(regex) || this.isEmpty()) ? this : deletePattern(Pattern.compile(regex.toString()));
    }

    public EnhancedStringBuilder deletePattern(final Pattern pattern) {
        if (pattern == null || this.isEmpty()) {
            return this;
        }
        final List<MatchEntry> occurrences = new ArrayList<MatchEntry>(findPattern(pattern));
        Collections.reverse(occurrences);
        for (MatchEntry occurrence : occurrences) {
            delegate.delete(occurrence.getStart(), occurrence.getEnd());
        }
        return this;
    }

    public EnhancedStringBuilder replace(final int start, final int end, final Object string) {
        delegate.replace(start, end, format(string).toString());
        return this;
    }

    public EnhancedStringBuilder replace(final MatchEntry matchEntry, final Object replacement) {
        ArgumentUtils.rejectIfNull(matchEntry);
        if (!matchEntry.isValidFor(delegate)) {
            throw new IllegalArgumentException("MatchEntry not valid.");
        }
        delegate.replace(matchEntry.getStart(), matchEntry.getEnd(), replacement.toString());
        return this;
    }

    public EnhancedStringBuilder replace(final CharSequence paramName, final Object replacement, final String startDelimiter, final String endDelimiter) {
        if (CharSequenceUtils.isEmptyOrNull(paramName) || this.isEmpty()) {
            return this;
        }
        // TODO : cross-reference resolving, when has delimiters
        // merge delimiters on the param key, if applicable
        int keyLen = paramName.length() + length(startDelimiter, endDelimiter);
        final String key = new StringBuilder(keyLen).append(startDelimiter).append(paramName).append(endDelimiter).toString();
        // get the param value as a String, to do the replacement
        final CharSequence value = format(replacement);
        int idx = indexOf(key);
        while (idx >= 0) {
            replace(idx, idx + key.length(), value);
            idx = indexOf(key, idx + value.length());
        }
        return this;
    }

    public EnhancedStringBuilder replace(final String startDelimiter, final String endDelimiter, final Map<? extends CharSequence, ?> params) {
        if (this.isEmpty() || CollectionUtils.isEmptyOrNull(params)) {
            return this;
        }
        for (Map.Entry<? extends CharSequence, ?> entry : params.entrySet()) {
            replace(entry.getKey(), format(entry.getValue()), startDelimiter, endDelimiter);
        }
        return this;
    }

    public EnhancedStringBuilder replace(final String startDelimiter, final String endDelimiter, final Object[] params) {
        if (this.isEmpty() || ArrayUtils.isEmptyOrNull(params)) {
            return this;
        }
        for (int i = 0; i < params.length; i++) {
            replace(Integer.toString(i), format(params[i]), startDelimiter, endDelimiter);
        }
        return this;
    }

    public EnhancedStringBuilder replacePlain(final CharSequence original, final Object replacement) {
        return replace(original, replacement, null, null);
    }

    public EnhancedStringBuilder replacePlain(final Map<? extends CharSequence, ?> params) {
        return replace(null, null, params);
    }

    public EnhancedStringBuilder replaceParam(final CharSequence paramName, final Object value) {
        final Map<CharSequence, Object> params = new LinkedHashMap<CharSequence, Object>();
        params.put(paramName, value);
        return this.replaceParams(params);
    }

    public EnhancedStringBuilder replaceParams(final Map<? extends CharSequence, ?> params) {
        return replace(Constants.DEFAULT_PARAM_START, Constants.DEFAULT_PARAM_END, params);
    }

    public EnhancedStringBuilder replaceParams(final Object[] params) {
        return replace(Constants.DEFAULT_PARAM_START, Constants.DEFAULT_PARAM_END, params);
    }

    public EnhancedStringBuilder replacePattern(final CharSequence regex, final Object replacement) {
        return (CharSequenceUtils.isEmptyOrNull(regex) || this.isEmpty())
               ? this : replacePattern(Pattern.compile(regex.toString()), format(replacement));
    }

    public EnhancedStringBuilder replacePattern(final Pattern pattern, final Object replacement) {
        if (pattern == null || this.isEmpty()) {
            return this;
        }
        final List<MatchEntry> occurrences = new ArrayList<MatchEntry>(findPattern(pattern));
        Collections.reverse(occurrences);
        for (MatchEntry occurrence : occurrences) {
            replace(occurrence.getStart(), occurrence.getEnd(), format(replacement));
        }
        return this;
    }

    public EnhancedStringBuilder replacePattern(final Map<CharSequence, ?> patterns) {
        if (this.isEmpty() || CollectionUtils.isEmptyOrNull(patterns)) {
            return this;
        }
        for (Map.Entry<CharSequence, ?> entry : patterns.entrySet()) {
            replacePattern(entry.getKey(), format(entry.getValue()));
        }
        return this;
    }

    public EnhancedStringBuilder reverse() {
        delegate.reverse();
        return this;
    }

    public List<String> split(final CharSequence regex) {
        return CharSequenceUtils.split(delegate, regex);
    }

    public List<String> split(final Pattern pattern) {
        return CharSequenceUtils.split(delegate, pattern);
    }

    public List<String> split(final CharSequence regex, final boolean ignoreBlank) {
        return CharSequenceUtils.split(delegate, regex, ignoreBlank);
    }

    public List<String> split(final Pattern pattern, final boolean ignoreBlank) {
        return CharSequenceUtils.split(delegate, pattern, ignoreBlank);
    }

    public List<MatchEntry> find(final CharSequence toSearch) {
        return CharSequenceUtils.find(toSearch, delegate);
    }

    public List<MatchEntry> find(final CharSequence toSearch, final Boolean caseSensitive) {
        return CharSequenceUtils.find(toSearch, delegate, caseSensitive);
    }

    @SuppressWarnings("unchecked")
    public List<MatchEntry> findPattern(final CharSequence regex) {
        return CharSequenceUtils.findPattern(regex, delegate);
    }

    @SuppressWarnings("unchecked")
    public List<MatchEntry> findPattern(final Pattern pattern) {
        return CharSequenceUtils.findPattern(pattern, delegate);
    }

    public Set<String> listParams() {
        return CharSequenceUtils.listParams(delegate);
    }

    @SuppressWarnings("unchecked")
    public Set<String> listParams(final String startDelimiter, final String endDelimiter) {
        return CharSequenceUtils.listParams(delegate, startDelimiter, endDelimiter);
    }

    public EnhancedStringBuilder toUpperCase() {
        return toUpperCase(0, length());
    }

    public EnhancedStringBuilder toUpperCase(final int start, final int end) {
        ArgumentUtils.rejectIfLessThan(start, 0);
        ArgumentUtils.rejectIfGreaterEqual(start, end);
        for (int i = start; i < length() && i < end; i++) {
            setCharAt(i, Character.toUpperCase(charAt(i)));
        }
        return this;
    }

    public EnhancedStringBuilder toLowerCase() {
        return toLowerCase(0, length());
    }

    public EnhancedStringBuilder toLowerCase(final int start, final int end) {
        ArgumentUtils.rejectIfLessThan(start, 0);
        ArgumentUtils.rejectIfGreaterEqual(start, end);
        for (int i = start; i < length() && i < end; i++) {
            setCharAt(i, Character.toLowerCase(charAt(i)));
        }
        return this;
    }

    public EnhancedStringBuilder toCamelCase() {
        return toCamelCase(true, true);
    }

    public EnhancedStringBuilder toCamelCase(final boolean removeAccents, final boolean capitalizeFirst) {
        if (CharSequenceUtils.isNotEmpty(delegate)) {
            // copy to a tmp
            final EnhancedStringBuilder tmp = new EnhancedStringBuilder(this);
            // find delimiters
            final List<MatchEntry> occurrences = tmp.findPattern(TOKENIZER_REGEX);
            if (CollectionUtils.isEmptyOrNull(occurrences)) {
                // single word. no tokenizing...
                if (removeAccents) {
                    removeAccents();
                }
                if (capitalizeFirst) {
                    capitalizeFirst();
                }
            } else {
                // clear this
                clear();
                if (removeAccents) {
                    tmp.removeAccents();
                }
                int start = 0;
                for (final MatchEntry occurrence : occurrences) {
                    // get token
                    final EnhancedStringBuilder token = tmp.subSequence(start, occurrence.getStart());
                    appendCamelcaseToken(token, capitalizeFirst);
                    // update offset
                    start = occurrence.getEnd();
                }
                final MatchEntry last = CollectionUtils.getLast(occurrences);
                final EnhancedStringBuilder token = tmp.subSequence(last.getEnd());
                appendCamelcaseToken(token, capitalizeFirst);
            }
        }
        return this;
    }

    private void appendCamelcaseToken(final EnhancedStringBuilder token, final boolean capitalizeFirst) {
        if (CharSequenceUtils.isNotEmpty(token)) {
            // uppercase first
            if (!this.isEmpty() || capitalizeFirst) {
                token.capitalizeFirst();
            }
            // append token
            this.append(token);
        }
    }

    public EnhancedStringBuilder toUnderscore() {
        return toUnderscore(true, true, false);
    }

    public EnhancedStringBuilder toUnderscore(final boolean removeAccents, final boolean upperCased, final boolean separateNumbers) {
        if (CharSequenceUtils.isNotEmpty(delegate)) {
            // copy to a tmp
            final EnhancedStringBuilder tmp = new EnhancedStringBuilder(this);
            // find delimiters
            final List<MatchEntry> occurrences = tmp.findPattern(TOKENIZER_REGEX);
            if (CollectionUtils.isEmptyOrNull(occurrences)) {
                // single word. no tokenizing...
                if (removeAccents) {
                    removeAccents();
                }
                if (upperCased) {
                    toUpperCase();
                }
            } else {
                // clear this
                clear();
                if (removeAccents) {
                    tmp.removeAccents();
                }
                // find delimiters
                int start = 0;
                for (final MatchEntry occurrence : occurrences) {
                    // get token
                    final EnhancedStringBuilder token = tmp.subSequence(start, occurrence.getStart());
                    appendUnderscoreToken(token, upperCased, separateNumbers);
                    start = occurrence.getEnd();
                }
                final MatchEntry last = CollectionUtils.getLast(occurrences);
                final EnhancedStringBuilder token = tmp.subSequence(last.getEnd());
                appendUnderscoreToken(token, upperCased, separateNumbers);
            }
        }
        return this;
    }

    private void appendUnderscoreToken(final EnhancedStringBuilder token, final boolean upper, final boolean separateNumbers) {
        if (CharSequenceUtils.isNotEmpty(token)) {
            if (this.isNotEmpty() && (!CharSequenceUtils.isNumber(token) || separateNumbers)) {
                this.append('_');
            }
            this.append(upper, token.toUpperCase(), token);
        }
    }

    public EnhancedStringBuilder capitalizeFirst() {
        return capitalize(CAPITALIZE_FIRST_REGEX);
    }

    public EnhancedStringBuilder capitalizeFirst(final CharSequence delimiterRegex) {
        return capitalize(delimiterRegex);
    }

    public EnhancedStringBuilder capitalizeAll() {
        return capitalize(CAPITALIZE_ALL_REGEX);
    }

    public EnhancedStringBuilder capitalizeAll(final CharSequence delimiterRegex) {
        return capitalize(delimiterRegex);
    }

    private EnhancedStringBuilder capitalize(final CharSequence delimiterRegex) {
        if (CharSequenceUtils.isNotEmpty(delegate)) {
            // copy to a tmp
            final EnhancedStringBuilder tmp = new EnhancedStringBuilder(this);
            // find delimiters
            final List<MatchEntry> occurrences = tmp.findPattern(CharSequenceUtils.isNotEmpty(delimiterRegex) ? delimiterRegex : CAPITALIZE_ALL_REGEX);
            if (CollectionUtils.isEmptyOrNull(occurrences)) {
                // single word. no tokenizing...
                return toUpperCase(0, 1);
            } else {
                // find delimiters
                final MatchEntry first = CollectionUtils.getFirst(occurrences);
                if (first.getStart() > 0) {
                    // doesnt starts with delimiter
                    toUpperCase(0, 1);
                }
                for (final MatchEntry occurrence : occurrences) {
                    int i = occurrence.getEnd();
                    toUpperCase(i, i + 1);
                }
            }
        }
        return this;
    }

    public int indexOf(final CharSequence toSearch) {
        return CharSequenceUtils.indexOf(toSearch, delegate);
    }

    public int indexOf(final CharSequence toSearch, final boolean caseSensitive) {
        return CharSequenceUtils.indexOf(toSearch, delegate, caseSensitive);
    }

    public int indexOf(final CharSequence toSearch, final int fromIndex) {
        return CharSequenceUtils.indexOf(toSearch, fromIndex, delegate);
    }

    public int indexOf(final CharSequence toSearch, final int fromIndex, final boolean caseSensitive) {
        return CharSequenceUtils.indexOf(toSearch, fromIndex, delegate, caseSensitive);
    }

    public int lastIndexOf(final CharSequence toSearch) {
        return CharSequenceUtils.lastIndexOf(toSearch, delegate);
    }

    public int lastIndexOf(final CharSequence toSearch, final boolean caseSensitive) {
        return CharSequenceUtils.lastIndexOf(toSearch, delegate, caseSensitive);
    }

    public int lastIndexOf(final CharSequence toSearch, final int fromIndex) {
        return CharSequenceUtils.lastIndexOf(toSearch, fromIndex, delegate);
    }

    public int lastIndexOf(final CharSequence toSearch, final int fromIndex, final boolean caseSensitive) {
        return CharSequenceUtils.lastIndexOf(toSearch, fromIndex, delegate, caseSensitive);
    }

    public int indexOf(final char toSearch) {
        return CharSequenceUtils.indexOf(toSearch, delegate);
    }

    public int indexOf(final char toSearch, final boolean caseSensitive) {
        return CharSequenceUtils.indexOf(toSearch, delegate, caseSensitive);
    }

    public int indexOf(final char toSearch, final int fromIndex) {
        return CharSequenceUtils.indexOf(toSearch, fromIndex, delegate);
    }

    public int indexOf(final char toSearch, final int fromIndex, final boolean caseSensitive) {
        return CharSequenceUtils.indexOf(toSearch, fromIndex, delegate, caseSensitive);
    }

    public int lastIndexOf(final char toSearch) {
        return CharSequenceUtils.lastIndexOf(toSearch, delegate);
    }

    public int lastIndexOf(final char toSearch, final boolean caseSensitive) {
        return CharSequenceUtils.lastIndexOf(toSearch, delegate, caseSensitive);
    }

    public int lastIndexOf(final char toSearch, final int fromIndex) {
        return CharSequenceUtils.lastIndexOf(toSearch, fromIndex, delegate);
    }

    public int lastIndexOf(final char toSearch, final int fromIndex, final boolean caseSensitive) {
        return CharSequenceUtils.lastIndexOf(toSearch, fromIndex, delegate, caseSensitive);
    }

    public int indexOfLastChar() {
        return CharSequenceUtils.indexOfLastChar(delegate);
    }

    public int countOccurrencesOf(final CharSequence toCount) {
        return CharSequenceUtils.countOccurrencesOf(toCount, delegate);
    }

    public int countOccurrencesOf(final CharSequence toCount, final int fromIndex) {
        return CharSequenceUtils.countOccurrencesOf(toCount, fromIndex, delegate);
    }

    public int countOccurrencesOf(final CharSequence toCount, final int fromIndex, final Boolean caseSensitive) {
        return CharSequenceUtils.countOccurrencesOf(toCount, fromIndex, delegate, caseSensitive);
    }

    public int countOccurrencesOf(final char toCount) {
        return CharSequenceUtils.countOccurrencesOf(toCount, delegate);
    }

    public int countOccurrencesOf(final char toCount, final int fromIndex) {
        return CharSequenceUtils.countOccurrencesOf(toCount, fromIndex, delegate);
    }

    public int countOccurrencesOf(final char toCount, final int fromIndex, final Boolean caseSensitive) {
        return CharSequenceUtils.countOccurrencesOf(toCount, fromIndex, delegate, caseSensitive);
    }

    public Map<Character, Integer> countCharOccurrences() {
        return CharSequenceUtils.getCharCount(delegate);
    }

    @SuppressWarnings("unchecked")
    public Map<Character, Integer> countCharOccurrences(final int fromIndex) {
        return CharSequenceUtils.getCharCount(fromIndex, delegate);
    }

    public int capacity() {
        return delegate.capacity();
    }

    public void ensureCapacity(final int minimumCapacity) {
        delegate.ensureCapacity(minimumCapacity);
    }

    public void trimToSize() {
        delegate.trimToSize();
    }

    public void setLength(final int newLength) {
        delegate.setLength(newLength);
    }

    public int codePointAt(final int index) {
        return delegate.codePointAt(index);
    }

    public int codePointBefore(final int index) {
        return delegate.codePointBefore(index);
    }

    public int codePointCount(final int start, final int end) {
        return delegate.codePointCount(start, end);
    }

    public int offsetByCodePoints(final int index, final int codePointOffset) {
        return delegate.offsetByCodePoints(index, codePointOffset);
    }

    public void getChars(final int start, final int end, final char[] dest, final int destStart) {
        delegate.getChars(start, end, dest, destStart);
    }

    public void setCharAt(final int index, final char c) {
        delegate.setCharAt(index, c);
    }

    public EnhancedStringBuilder subSequence(final int start) {
        return subSequence(start, length());
    }

    public EnhancedStringBuilder subSequence(final int start, final int end) {
        int len = end - start;
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        } else if (end > delegate.length()) {
            throw new StringIndexOutOfBoundsException(end);
        } else if (len < 0) {
            throw new StringIndexOutOfBoundsException(len);
        }
        final EnhancedStringBuilder builder = new EnhancedStringBuilder(len + 100);
        for (int i = start; i < end; i++) {
            builder.append(delegate.charAt(i));
        }
        return builder;
    }

    public String substring(final int start) {
        return delegate.substring(start);
    }

    public String substring(final int start, final int end) {
        return delegate.substring(start, end);
    }

    public String substring(final MatchEntry matchEntry) {
        return delegate.substring(matchEntry.getStart(), matchEntry.getEnd());
    }

    public String substringBeforeFirst(final CharSequence toSearch) {
        return substringBeforeFirst(toSearch, true);
    }

    public String substringBeforeFirst(final CharSequence toSearch, final Boolean caseSensitive) {
        final int idx = indexOf(toSearch, caseSensitive);
        return (idx < 0) ? null : substring(0, idx);
    }

    public String substringBeforeLast(final CharSequence toSearch) {
        return substringBeforeLast(toSearch, true);
    }

    public String substringBeforeLast(final CharSequence toSearch, final Boolean caseSensitive) {
        final int idx = lastIndexOf(toSearch, caseSensitive);
        return (idx < 0) ? null : substring(0, idx);
    }

    public String substringAfterFirst(final CharSequence toSearch) {
        return substringAfterFirst(toSearch, true);
    }

    public String substringAfterFirst(final CharSequence toSearch, final Boolean caseSensitive) {
        final int idx = indexOf(toSearch, caseSensitive);
        return (idx < 0) ? null : substring(idx + toSearch.length(), length());
    }

    public String substringAfterLast(final CharSequence toSearch) {
        return substringAfterLast(toSearch, true);
    }

    public String substringAfterLast(final CharSequence toSearch, final Boolean caseSensitive) {
        final int idx = lastIndexOf(toSearch, caseSensitive);
        return (idx < 0) ? null : substring(idx + toSearch.length(), length());
    }

    public boolean contains(final CharSequence s) {
        return indexOf(s) >= 0;
    }

    public boolean startsWith(final CharSequence preffix) {
        return startsWith(preffix, 0, delegate);
    }

    public boolean endsWith(final CharSequence suffix) {
        return startsWith(suffix, delegate.length() - suffix.length(), delegate);
    }

    public boolean matches(final CharSequence regex) {
        return matches(Pattern.compile(regex.toString(), 0));
    }

    public boolean matches(final CharSequence regex, final int flags) {
        return matches(Pattern.compile(regex.toString(), flags));
    }

    public boolean matches(final Pattern pattern) {
        if (pattern == null || CharSequenceUtils.isEmptyOrNull(delegate)) {
            return false;
        }
        return pattern.matcher(delegate).matches();
    }

    public boolean matchesAny(final CharSequence[] regexes) {
        return matchesAny(Arrays.asList(regexes), 0);
    }

    public boolean matchesAny(final CharSequence[] regexes, final int flags) {
        return matchesAny(Arrays.asList(regexes), flags);
    }

    public boolean matchesAny(final Collection<? extends CharSequence> regexes) {
        return matchesAny(regexes, 0);
    }

    public boolean matchesAny(final Collection<? extends CharSequence> regexes, final int flags) {
        for (final CharSequence regex : regexes) {
            if (matches(regex, flags)) {
                return true;
            }
        }
        return false;
    }

    public char firstChar() {
        return delegate.charAt(0);
    }

    public char lastChar() {
        return delegate.charAt(indexOfLastChar());
    }

    public int length() {
        return delegate.length();
    }

    public char charAt(final int index) {
        return delegate.charAt(index);
    }

    public Iterator<Character> iterator() {
        return new CharIterator();
    }

    public String toString() {
        return delegate.toString();
    }

    public int compareTo(final CharSequence o) {
        return compareTo(o, Constants.CASE_SENSITIVE);
    }

    public int compareTo(final CharSequence o, final Boolean caseSensitive) {
        return new CharSequenceComparator(caseSensitive, true).compare(delegate, o);
    }

    protected static int length(final CharSequence... sequences) {
        int length = 0;
        if (ArrayUtils.isNotEmpty(sequences)) {
            for (final CharSequence item : sequences) {
                if (CharSequenceUtils.isNotEmpty(item)) {
                    length += item.length();
                }
            }
        }
        return length;
    }

    protected static boolean startsWith(final CharSequence prefix, final int offset, final CharSequence sequence) {
        if (CharSequenceUtils.isEmptyOrNull(prefix) || CharSequenceUtils.isEmptyOrNull(sequence) || prefix.length() > sequence.length()) {
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

    protected static CharSequence format(final Object object) {
        final StringBuilder builder = new StringBuilder();
        format(object, builder);
        return builder;
    }

    protected static void format(final Object object, final StringBuilder builder) {
        if (object == null) {
            return;
        }
        if (ObjectUtils.isCharSequence(object)) {
            builder.append((CharSequence) object);
        } else if (ObjectUtils.isCharacter(object)) {
            builder.append((Character) object);
        } else if (ObjectUtils.isNumber(object)) {
            builder.append(NumberUtils.format((Number) object));
        } else if (ObjectUtils.isDate(object) || ObjectUtils.isCalendar(object)) {
            builder.append(DateUtils.formatDateTime(object));
        } else if (ObjectUtils.isArray(object)) {
            int size = Array.getLength(object);
            builder.ensureCapacity(builder.length() + (size));
            for (int i = 0; i < size; i++) {
                format(Array.get(object, i), builder);
            }
        } else if (ObjectUtils.isCollection(object)) {
            Collection<?> collection = (Collection) object;
            for (Object item : collection) {
                format(item, builder);
            }
        } else {
            builder.append(object);
        }
    }

    private class CharIterator implements Iterator<Character> {

        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        public boolean hasNext() {
            return length() > 0 && cursor < length();
        }

        public Character next() {
            if (cursor >= length()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return charAt(cursor++);
        }

        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            deleteCharAt(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }
    }

    public enum LineBreakMode {

        /**
         * The system line break string.
         * <p/>
         * On Windows systems it will be "\r\n"; On Unix systems it will be just "\n".
         */
        SYSTEM_DETECT(org.apache.commons.io.IOUtils.LINE_SEPARATOR),
        /**
         * The Unix line break string.
         * <p/>
         * value = "\n".
         */
        UNIX(org.apache.commons.io.IOUtils.LINE_SEPARATOR_UNIX),
        /**
         * The Windows line break string.
         * <p/>
         * value = "\r\n".
         */
        WINDOWS(org.apache.commons.io.IOUtils.LINE_SEPARATOR_WINDOWS);

        private final String lineBreakString;

        /**
         * Defines the line break String.
         * <p/>
         * @param newLineString The line break String.
         */
        private LineBreakMode(final String newLineString) {
            this.lineBreakString = newLineString;
        }

        /**
         * Returns the line break String.
         * <p/>
         * @return The line break String.
         */
        public String getLineBreakString() {
            return lineBreakString;
        }

        /**
         * Returns the same as {@link #getLineBreakString()}.
         * <p/>
         * @return The line break String.
         */
        @Override
        public String toString() {
            return getLineBreakString();
        }
    }
}
