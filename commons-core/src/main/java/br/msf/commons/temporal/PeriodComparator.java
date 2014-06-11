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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package br.msf.commons.temporal;

import br.msf.commons.util.CalendarUtils;
import java.util.Comparator;

/**
 * Compares two Periods.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public class PeriodComparator implements Comparator<Period> {

    private boolean ignoreTime = false;

    public PeriodComparator() {
    }

    public PeriodComparator(final boolean ignoreTime) {
        this.ignoreTime = ignoreTime;
    }

    public boolean isIgnoreTime() {
        return ignoreTime;
    }

    public void setIgnoreTime(final boolean ignoreTime) {
        this.ignoreTime = ignoreTime;
    }

    @Override
    public int compare(final Period period1, final Period period2) {
        if (period1 == period2) {
            // both null or same instance.
            return 0;
        }
        if (period1 == null || period2 == null) {
            throw new IllegalArgumentException("Cannot compare with null.");
        }
        int compare = 0;
        if (period1.getStart() != null && period2.getStart() != null) {
            // compare start
            compare = CalendarUtils.compare(period1.getStart(), period2.getStart(), ignoreTime);
        } else if (period1.getStart() == null) {
            // period1 start is null. Consider period.start=null as -infinite
            compare = -1;
        } else if (period2.getStart() == null) {
            // period2 start is null. Consider period.start=null as -infinite
            compare = 1;
        }
        if (compare == 0) {
            // same starts...
            if (period1.getEnd() != null && period2.getEnd() != null) {
                // compare end
                compare = CalendarUtils.compare(period1.getEnd(), period2.getEnd(), ignoreTime);
            } else if (period1.getEnd() == null) {
                // period1 end is null. Consider period.end=null as +infinite
                compare = 1;
            } else if (period2.getEnd() == null) {
                // period2 end is null. Consider period.end=null as +infinite
                compare = -1;
            }
        }
        return compare;
    }
}
