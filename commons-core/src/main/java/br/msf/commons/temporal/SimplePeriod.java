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
package br.msf.commons.temporal;

import br.msf.commons.util.CalendarUtils;
import br.msf.commons.util.ReflectionUtils;
import java.util.Calendar;
import java.util.Date;

/**
 * A class that represents a time lapse, having a start date and an end date.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public class SimplePeriod implements Period {

    private static final long serialVersionUID = -1923056702062001114L;
    protected Calendar start;
    protected Calendar end;

    /**
     * Default constructor.
     */
    public SimplePeriod() {
    }

    /**
     * Constructor that defines the start and end dates.
     * <p>
     * @param start The start date. Can be a {@link Date} or a {@link Calendar}.
     * @param end   The end date. Can be a {@link Date} or a {@link Calendar}.
     */
    public SimplePeriod(final Object start, final Object end) {
        this.start = CalendarUtils.castToCalendar(start);
        this.end = CalendarUtils.castToCalendar(end);
    }

    /**
     * Returns the start of the period.
     *
     * @return The start of the period.
     */
    @Override
    public Calendar getStart() {
        return this.start;
    }

    /**
     * Sets the start of the period, in Calendar format.
     *
     * @param start The start of the period.
     */
    @Override
    public void setStart(final Calendar start) {
        this.start = start;
    }

    /**
     * Sets the start of the period, in Date format.
     *
     * @param start The start of the period.
     */
    public void setStartDate(final Date start) {
        this.start = CalendarUtils.castToCalendar(start);
    }

    /**
     * Returns the end of the period.
     *
     * @return The end of the period.
     */
    @Override
    public Calendar getEnd() {
        return this.end;
    }

    /**
     * Sets the end of the period, in Calendar format.
     *
     * @param end The end of the period.
     */
    @Override
    public void setEnd(final Calendar end) {
        this.end = end;
    }

    /**
     * Sets the end of the period, in Date format.
     *
     * @param end The end of the period.
     */
    public void setEndDate(final Date end) {
        this.end = CalendarUtils.castToCalendar(end);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.start != null ? this.start.hashCode() : 0);
        hash = 97 * hash + (this.end != null ? this.end.hashCode() : 0);
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
        final Period other = (Period) obj;
        if (this.start != other.getStart() && (this.start == null || !this.start.equals(other.getStart()))) {
            return false;
        }
        return this.end == other.getEnd() || (this.end != null && this.end.equals(other.getEnd()));
    }

    @Override
    public int compareTo(final Period another) {
        int compare = CalendarUtils.compare(this.start, another.getStart());
        if (compare == 0) {
            compare = CalendarUtils.compare(this.end, another.getEnd());
        }
        return compare;
    }
}
