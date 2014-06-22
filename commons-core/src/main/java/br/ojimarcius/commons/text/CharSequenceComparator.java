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
import br.ojimarcius.commons.constants.Constants;
import br.ojimarcius.commons.util.ArgumentUtils;
import java.util.Comparator;
import java.util.logging.Logger;

/**
 * Class that compares the contents of different implementations of {@link CharSequence}.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public class CharSequenceComparator implements Comparator<CharSequence> {
    private static final Logger LOGGER = Logger.getLogger(CharSequenceComparator.class.getName());

    /**
     * Indicates if the comparison will be case sensitive or case insensitive.
     */
    protected boolean caseSensitive;
    /**
     * Indicates what to do when the comparison involves some null value.
     */
    protected boolean assumeEmptyForNullSequences;

    /**
     * Default Constructor.
     * <p/>
     * Uses case sensitive comparisons by default.
     */
    public CharSequenceComparator() {
        this(Constants.CASE_SENSITIVE, false);
    }

    /**
     * Constructor that defines the case sensitivity of the comparison.
     *
     * @param caseSensitive The case sensitivity to be used.
     */
    public CharSequenceComparator(final boolean caseSensitive) {
        this(caseSensitive, false);
    }

    /**
     * Constructor that defines the case sensitivity of the comparison and what to do when it involves a null value.
     *
     * @param caseSensitive               The case sensitivity to be used.
     * @param assumeEmptyForNullSequences Indicates what to do when the comparison involves some null value.
     */
    public CharSequenceComparator(final boolean caseSensitive, final boolean assumeEmptyForNullSequences) {
        this.caseSensitive = caseSensitive;
        this.assumeEmptyForNullSequences = assumeEmptyForNullSequences;
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

    public boolean isAssumeEmptyForNullSequences() {
        return assumeEmptyForNullSequences;
    }

    /**
     * Sets what to do when the comparison involves some null value.
     *
     * @param assumeEmptyForNullSequences {@code true} will make the {@link #compare(java.lang.CharSequence, java.lang.CharSequence) }
     *                                    method to replace any null argument by the empty String <tt>""</tt>, before procceding any comparison.
     * {@code false} will make the {@link #compare(java.lang.CharSequence, java.lang.CharSequence) }
     * to throw an exception if one of its arguments is null (cannot compare null with not-null value).
     */
    public void setAssumeEmptyForNullSequences(final boolean assumeEmptyForNullSequences) {
        this.assumeEmptyForNullSequences = assumeEmptyForNullSequences;
    }

    /**
     * Compares the contents of the given sequences.
     *
     * @param sequence1 One sequence for comparison.
     * @param sequence2 Another sequence for comparison.
     * @return <ul>
     * <li><b><tt>-1</tt></b>: if the <tt>sequence1</tt> comes before <tt>sequence2</tt> on the alphabetical order.</li>
     * <li><b><tt>1</tt></b>: if the <tt>sequence1</tt> comes after <tt>sequence2</tt> on the alphabetical order.</li>
     * <li><b><tt>0</tt></b>: if both sequences have the same content.</li>
     * </ul>
     */
    @Override
    public int compare(final CharSequence sequence1, final CharSequence sequence2) {
        final CharSequence seq1;
        final CharSequence seq2;
        if (assumeEmptyForNullSequences) {
            LOGGER.warning("Null CharSequence! assuming \"\" to compare...");
            seq1 = sequence1 != null ? sequence1 : "";
            seq2 = sequence2 != null ? sequence2 : "";
        } else {
            ArgumentUtils.rejectIfAnyNull(sequence1, sequence2);
            seq1 = sequence1;
            seq2 = sequence2;
        }
        final int max = seq1.length() <= seq2.length() ? seq1.length() : seq2.length();
        final CharComparator comparator = new CharComparator(isCaseSensitive());
        for (int i = 0; i < max; i++) {
            int resp = comparator.compare(seq1.charAt(i), seq2.charAt(i));
            if (resp < 0) {
                return -1;
            } else if (resp > 0) {
                return 1;
            }
        }
        if (seq1.length() < seq2.length()) {
            return -1;
        } else if (seq1.length() > seq2.length()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Returns true if the given arguments have the same value, obeying this Comparator's <tt>caseSensitive</tt>
     * attribute.
     *
     * @param sequence1 The left side argument of the comparison.
     * @param sequence2 The right side argument of the comparison.
     * @return {@code true} if the given arguments have the same value. {@code false} otherwise.
     */
    public boolean isSame(final CharSequence sequence1, final CharSequence sequence2) {
        return compare(sequence1, sequence2) == 0;
    }

    /**
     * Returns true if the given arguments have different values, obeying this Comparator's <tt>caseSensitive</tt>
     * attribute.
     *
     * @param sequence1 The left side argument of the comparison.
     * @param sequence2 The right side argument of the comparison.
     * @return {@code true} if the given arguments have different values. {@code false} otherwise.
     */
    public boolean isNotSame(final CharSequence sequence1, final CharSequence sequence2) {
        return compare(sequence1, sequence2) != 0;
    }

    /**
     * Returns true if the first argument has a lesser value than the second argument, obeying this Comparator's
     * <tt>caseSensitive</tt> attribute.
     *
     * @param sequence1 The left side argument of the comparison.
     * @param sequence2 The right side argument of the comparison.
     * @return {@code true} if the first argument has a lesser value than the second one. {@code false} otherwise.
     */
    public boolean isBefore(final CharSequence sequence1, final CharSequence sequence2) {
        return compare(sequence1, sequence2) < 0;
    }

    /**
     * Returns true if the first argument has a greater value than the second argument, obeying this Comparator's
     * <tt>caseSensitive</tt> attribute.
     *
     * @param sequence1 The left side argument of the comparison.
     * @param sequence2 The right side argument of the comparison.
     * @return {@code true} if the first argument has a greater value than the second one. {@code false} otherwise.
     */
    public boolean isAfter(final CharSequence sequence1, final CharSequence sequence2) {
        return compare(sequence1, sequence2) > 0;
    }

    /**
     * Returns the index within the given sequence of the first occurrence of the specified subsequence, starting at the
     * specified index.
     *
     * @param toSearch The subsequence to search.
     * @param sequence The sequence where the search will be made.
     * @return The index within the given sequence of the first occurrence of the specified subsequence.
     */
    @SuppressWarnings("empty-statement")
    public int indexOf(final CharSequence toSearch, final CharSequence sequence) {
        return indexOf(toSearch, 0, sequence);
    }

    /**
     * Returns the index within the given sequence of the first occurrence of the specified subsequence, starting at the
     * specified index.
     *
     * @param toSearch  The subsequence to search.
     * @param fromIndex The index from which to start the search.
     * @param sequence  The sequence where the search will be made.
     * @return The index within the given sequence of the first occurrence of the specified subsequence, starting at the
     *         specified index.
     */
    @SuppressWarnings("empty-statement")
    public int indexOf(final CharSequence toSearch, final int fromIndex, final CharSequence sequence) {
        int from = fromIndex;
        if (toSearch == null || sequence == null) {
            return -1;
        }
        if (from >= sequence.length()) {
            return (toSearch.length() == 0 ? sequence.length() : -1);
        }
        if (from < 0) {
            from = 0;
        }
        if (toSearch.length() == 0) {
            return from;
        }

        char first = toSearch.charAt(0);
        int max = 0 + (sequence.length() - toSearch.length());

        final CharComparator comparator = new CharComparator(isCaseSensitive());
        for (int i = 0 + from; i <= max; i++) {
            /* Look for first character. */
            if (comparator.isNotSame(sequence.charAt(i), first)) {
                do {
                    i++;
                } while (i <= max && comparator.isNotSame(sequence.charAt(i), first));
                //while (++i <= max && comparator.isNotSame(sequence.charAt(i), first));
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + CharSequenceUtils.indexOfLastChar(toSearch);
                int k = 1;
                while (j < end && comparator.isSame(sequence.charAt(j), toSearch.charAt(k))) {
                    j++;
                    k++;
                }
                //for (int k = 0 + 1; j < end && comparator.isSame(sequence.charAt(j), toSearch.charAt(k)); j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    return i - 0;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index within the given sequence of the last occurrence of the specified subsequence.
     *
     * @param toSearch The subsequence to search.
     * @param sequence The sequence where the search will be made.
     * @return The index within the given sequence of the last occurrence of the specified subsequence.
     */
    public int lastIndexOf(final CharSequence toSearch, final CharSequence sequence) {
        return lastIndexOf(toSearch, CharSequenceUtils.length(sequence), sequence);
    }

    /**
     * Returns the index within the given sequence of the last occurrence of the specified subsequence, <b>searching
     * backward</b> starting at the specified index.
     *
     * @param toSearch  The subsequence to search.
     * @param fromIndex The index from which to start the search, <b>backwardly</b>.
     * @param sequence  The sequence where the search will be made.
     * @return The index within the given sequence of the last occurrence of the specified subsequence, starting at the
     *         specified index (backwardly).
     */
    public int lastIndexOf(final CharSequence toSearch, final int fromIndex, final CharSequence sequence) {
        if (toSearch == null || sequence == null) {
            return -1;
        }
        int rightIndex = sequence.length() - toSearch.length();
        int from = fromIndex;
        if (from < 0) {
            return -1;
        }
        if (from > rightIndex) {
            from = rightIndex;
        }
        /* Empty string always matches. */
        if (toSearch.length() == 0) {
            return from;
        }

        int strLastIndex = 0 + CharSequenceUtils.indexOfLastChar(toSearch);
        char strLastChar = toSearch.charAt(strLastIndex);
        int min = 0 + CharSequenceUtils.indexOfLastChar(toSearch);
        int i = min + from;

        final CharComparator comparator = new CharComparator(isCaseSensitive());
        startSearchForLastChar:
        while (true) {
            while (i >= min && comparator.isNotSame(sequence.charAt(i), strLastChar)) {
                i--;
            }
            if (i < min) {
                return -1;
            }
            int j = i - 1;
            int start = j - CharSequenceUtils.indexOfLastChar(toSearch);
            int k = strLastIndex - 1;

            while (j > start) {
                if (comparator.isNotSame(sequence.charAt(j--), toSearch.charAt(k--))) {
                    i--;
                    continue startSearchForLastChar;
                }
            }
            return start - 0 + 1;
        }
    }
}
