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

import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.ArgumentUtils;
import java.util.Comparator;

public class CharComparator implements Comparator<Character> {

    /**
     * Indicates if the comparison will be case sensitive or case insensitive.
     */
    protected boolean caseSensitive;

    /**
     * Default Constructor.
     * <p/>
     * Uses case sensitive comparisons by default.
     */
    public CharComparator() {
        this(false);
    }

    /**
     * Constructor that defines the case sensitivity of the comparison.
     *
     * @param caseSensitive The case sensitivity to be used.
     */
    public CharComparator(final boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * Gets the case sensitivity of the comparison currently in use.
     *
     * @return The case sensitivity in use.
     */
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    /**
     * Sets the case sensitivity of the comparison.
     *
     * @param caseSensitive The case sensitivity to be used.
     */
    public void setCaseSensitive(final boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * Compares two {@link Character Characters}, obeying this Comparator's <tt>caseSensitive</tt> attribute.
     * <p/>
     *
     * @param char1 The left Character of the comparison.
     * @param char2 The right Character of the comparison.
     * @return <ul>
     * <li><b><tt>&lt; 0</tt></b>: if the <tt>char1</tt> comes before <tt>char2</tt> on the alphabetical order.</li>
     * <li><b><tt>&gt; 0</tt></b>: if the <tt>char1</tt> comes after <tt>char2</tt> on the alphabetical order.</li>
     * <li><b><tt>0</tt></b>: if both chars have the same content.</li>
     * </ul>
     * @throws IllegalArgumentException If any of the given Characters is null.
     */
    @Override
    public int compare(final Character char1, final Character char2) {
        ArgumentUtils.rejectIfAnyNull(char1, char2);
        return compare(char1.charValue(), char2.charValue());
    }

    /**
     * Compares two primitive chars, obeying this Comparator's <tt>caseSensitive</tt> attribute.
     *
     * @param char1 The left char of the comparison.
     * @param char2 The right char of the comparison.
     * @return <ul>
     * <li><b><tt>&lt; 0</tt></b>: if the <tt>char1</tt> comes before <tt>char2</tt> on the alphabetical order.</li>
     * <li><b><tt>&gt; 0</tt></b>: if the <tt>char1</tt> comes after <tt>char2</tt> on the alphabetical order.</li>
     * <li><b><tt>0</tt></b>: if both chars have the same content.</li>
     * </ul>
     */
    public int compare(final char char1, final char char2) {
        char c1 = char1, c2 = char2;
        if (!caseSensitive) {
            c1 = Character.toLowerCase(c1);
            c2 = Character.toLowerCase(c2);
        }
        return c1 - c2;
    }

    /**
     * Returns true if the given arguments have the same value, obeying this Comparator's <tt>caseSensitive</tt>
     * attribute.
     * <p/>
     * @param char1 The left side argument of the comparison.
     * @param char2 The right side argument of the comparison.
     * @return {@code true} if the given arguments have the same value. {@code false} otherwise.
     */
    public boolean isSame(final char char1, final char char2) {
        return compare(char1, char2) == 0;
    }

    /**
     * Returns true if the given arguments have different values, obeying this Comparator's <tt>caseSensitive</tt>
     * attribute.
     * <p/>
     * @param char1 The left side argument of the comparison.
     * @param char2 The right side argument of the comparison.
     * @return {@code true} if the given arguments have different values. {@code false} otherwise.
     */
    public boolean isNotSame(final char char1, final char char2) {
        return compare(char1, char2) != 0;
    }

    /**
     * Returns true if the first argument has a lesser value than the second argument, obeying this Comparator's
     * <tt>caseSensitive</tt> attribute.
     * <p/>
     * @param char1 The left side argument of the comparison.
     * @param char2 The right side argument of the comparison.
     * @return {@code true} if the first argument has a lesser value than the second one. {@code false} otherwise.
     */
    public boolean isBefore(final char char1, final char char2) {
        return compare(char1, char2) < 0;
    }

    /**
     * Returns true if the first argument has a greater value than the second argument, obeying this Comparator's
     * <tt>caseSensitive</tt> attribute.
     * <p/>
     * @param char1 The left side argument of the comparison.
     * @param char2 The right side argument of the comparison.
     * @return {@code true} if the first argument has a greater value than the second one. {@code false} otherwise.
     */
    public boolean isAfter(final char char1, final char char2) {
        return compare(char1, char2) > 0;
    }

    /**
     * Returns the index within the given sequence of the first occurrence of the specified char, starting at the
     * specified index.
     *
     * @param toSearch The char to search.
     * @param sequence The sequence where the search will be made.
     * @return The index within the given sequence of the first occurrence of the specified char.
     */
    public int indexOf(final char toSearch, final CharSequence sequence) {
        return indexOf(toSearch, 0, sequence);
    }

    /**
     * Returns the index within the given sequence of the first occurrence of the specified char, starting at the
     * specified index.
     *
     * @param toSearch  The char to search.
     * @param fromIndex The index from which to start the search.
     * @param sequence  The sequence where the search will be made.
     * @return The index within the given sequence of the first occurrence of the specified char, starting at the
     *         specified index.
     */
    public int indexOf(final char toSearch, final int fromIndex, final CharSequence sequence) {
        if (CharSequenceUtils.isEmptyOrNull(sequence)) {
            return -1;
        }
        int from = (fromIndex >= 0) ? fromIndex : 0;
        for (int i = from; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (compare(toSearch, c) == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index within the given sequence of the last occurrence of the specified char.
     *
     * @param toSearch  The char to search.
     * @param sequence  The sequence where the search will be made.
     * @return The index within the given sequence of the last occurrence of the specified char.
     */
    public int lastIndexOf(final char toSearch, final CharSequence sequence) {
        return lastIndexOf(toSearch, CharSequenceUtils.length(sequence), sequence);
    }

    /**
     * Returns the index within the given sequence of the last occurrence of the specified char, <b>searching
     * backward</b> starting at the specified index.
     *
     * @param toSearch  The char to search.
     * @param fromIndex The index from which to start the search, <b>backwardly</b>.
     * @param sequence  The sequence where the search will be made.
     * @return The index within the given sequence of the last occurrence of the specified char, starting at the
     *         specified index (backwardly).
     */
    public int lastIndexOf(final char toSearch, final int fromIndex, final CharSequence sequence) {
        if (fromIndex < 0 || CharSequenceUtils.isEmptyOrNull(sequence)) {
            return -1;
        }
        final int from = (fromIndex < sequence.length()) ? fromIndex : CharSequenceUtils.indexOfLastChar(sequence);
        for (int i = from; i >= 0; i--) {
            char c = sequence.charAt(i);
            if (compare(toSearch, c) == 0) {
                return i;
            }
        }
        return -1;
    }
}
