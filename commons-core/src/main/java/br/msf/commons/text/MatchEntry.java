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
import java.io.Serializable;

/**
 * Class that represents a subsequence coordinates inside a CharSequence.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public class MatchEntry implements Serializable, Comparable<MatchEntry> {

    private static final long serialVersionUID = 9112650717993537982L;
    /**
     * The start index of the matching subsequence.
     */
    private final int start;
    /**
     * The end index of the matching subsequence.
     */
    private final int end;

    /**
     * Creates a new MatchEntry with its <tt>start</tt> and <tt>end</tt> indexes.
     *
     * @param start The start index of the matching subsequence.
     * @param end   The end index of the matching subsequence.
     */
    public MatchEntry(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start index of the matching subsequence.
     *
     * @return The start index of the matching subsequence.
     */
    public int getStart() {
        return start;
    }

    /**
     * Returns the end index of the matching subsequence.
     *
     * @return The end index of the matching subsequence.
     */
    public int getEnd() {
        return end;
    }

    /**
     * Indicates if this MatchEntry is within the given sequences bounds.
     *
     * @param sequence The target sequence to test.
     * @return {@code true} if this MatchEntry is within the given sequences bounds. {@code false} otherwise.
     */
    public boolean isValidFor(final CharSequence sequence) {
        return !CharSequenceUtils.isBlankOrNull(sequence) && start >= 0 && start <= end && end <= sequence.length();
    }

    /**
     * Compares this MatchEntry with the given one.
     *
     * @param o The MatchEntry for comparison.
     * @return <tt>&lt; 0</tt> if this sequence starts before the given one.
     * <tt>0</tt> if both have the same coordinates or
     * <tt>&gt; 0</tt> if this sequence starts after the given one.
     */
    @Override
    public int compareTo(final MatchEntry o) {
        ArgumentUtils.rejectIfNull(o);
        int result = this.getStart() - o.getStart();
        return result == 0 ? this.getEnd() - o.getEnd() : result;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.start;
        hash = 53 * hash + this.end;
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MatchEntry other = (MatchEntry) obj;
        if (this.start != other.start) {
            return false;
        }
        return this.end == other.end;
    }

    @Override
    public String toString() {
        return "<" + start + "-" + end + ">";
    }
}
