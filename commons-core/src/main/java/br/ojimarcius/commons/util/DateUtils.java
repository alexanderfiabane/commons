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

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * Class containing utility methods to handle Dates and Calendars.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public final class DateUtils extends AbstractDateUtils {

    /**
     * Util files cannot be instantiated.
     */
    private DateUtils() {
    }

    /**
     * Returns the current date, <b>truncating</b> time info.
     *
     * @return The current date.
     */
    public static Date today() {
        return castToDate(CalendarUtils.today());
    }

    /**
     * Returns the yesterday date, <b>truncating</b> time info.
     *
     * @return The yesterday date.
     */
    public static Date yesterday() {
        return castToDate(CalendarUtils.yesterday());
    }

    /**
     * Returns the tomorrow date, <b>truncating</b> time info.
     *
     * @return The tomorrow date.
     */
    public static Date tomorrow() {
        return castToDate(CalendarUtils.tomorrow());
    }

    /**
     * Returns the current date, <b>including</b> time info.
     *
     * @return The current date, including time info.
     */
    public static Date now() {
        return castToDate(CalendarUtils.now());
    }

    public static Date getTimeTruncated(final Object date) {
        return castToDate(CalendarUtils.getTimeTruncated(date));
    }

    public static Date getTimeMaxed(final Object date) {
        return castToDate(CalendarUtils.getTimeMaxed(date));
    }

    public static Date getFirstDateOfMonth(final Integer month, final Integer year) {
        return castToDate(CalendarUtils.getFirstDateOfMonth(month, year));
    }

    public static Date getFirstDateOfMonth(final Object date) {
        return castToDate(CalendarUtils.getFirstDateOfMonth(date));
    }

    public static Date getLastDateOfMonth(final Integer month, final Integer year) {
        return castToDate(CalendarUtils.getLastDateOfMonth(month, year));
    }

    public static Date getLastDateOfMonth(final Object date) {
        return castToDate(CalendarUtils.getLastDateOfMonth(date));
    }

    public static Date getSmallest(final boolean ignoreTime, final Object... dates) {
        return castToDate(CalendarUtils.getSmallest(ignoreTime, dates));
    }

    public static Date getSmallest(final boolean ignoreTime, final Collection<Object> dates) {
        return castToDate(CalendarUtils.getSmallest(ignoreTime, dates));
    }

    public static Date getLargest(final boolean ignoreTime, final Object... dates) {
        return castToDate(CalendarUtils.getLargest(ignoreTime, dates));
    }

    public static Date getLargest(final boolean ignoreTime, final Collection<Object> dates) {
        return castToDate(CalendarUtils.getLargest(ignoreTime, dates));
    }

    public static Date parse(final CharSequence date, final CharSequence pattern) {
        return castToDate(CalendarUtils.parse(date, pattern));
    }

    public static Date parse(final CharSequence date, final CharSequence pattern, final Locale locale) {
        return castToDate(CalendarUtils.parse(date, pattern, locale));
    }
}
