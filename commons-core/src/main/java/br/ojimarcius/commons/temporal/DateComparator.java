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
package br.ojimarcius.commons.temporal;

import br.ojimarcius.commons.util.CalendarUtils;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Compares two Dates or Calendars.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public class DateComparator implements Comparator<Date> {

    private boolean ignoreTime = false;

    public DateComparator() {
    }

    public DateComparator(final boolean ignoreTime) {
        this.ignoreTime = ignoreTime;
    }

    public boolean isIgnoreTime() {
        return ignoreTime;
    }

    public void setIgnoreTime(final boolean ignoreTime) {
        this.ignoreTime = ignoreTime;
    }

    @Override
    public int compare(final Date d1, final Date d2) {
        return compare(CalendarUtils.castToCalendar(d1), CalendarUtils.castToCalendar(d2));
    }

    public int compare(final Calendar c1, final Calendar c2) {
        if (c1 == c2) {
            // both null or same instance.
            return 0;
        }
        if (c1 == null || c2 == null) {
            throw new IllegalArgumentException("Cannot compare with null.");
        }
        return ignoreTime ? CalendarUtils.truncatedCompareTo(c1, c2, Calendar.DAY_OF_MONTH) : c1.compareTo(c2);
    }
}
