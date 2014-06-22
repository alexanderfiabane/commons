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

import br.ojimarcius.commons.temporal.DateComparator;
import br.ojimarcius.commons.text.CustomDateFormat;
import br.ojimarcius.commons.util.ArgumentUtils;
import br.ojimarcius.commons.util.CollectionUtils;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Class containing utility methods to handle Dates and Calendars.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public abstract class CalendarUtils extends AbstractDateUtils {

    /**
     * Returns the current date, <b>truncating</b> time info.
     *
     * @return The current date.
     */
    public static Calendar today() {
        return truncate(Calendar.getInstance(), Calendar.DAY_OF_MONTH);
    }

    /**
     * Returns the yesterday date, <b>truncating</b> time info.
     *
     * @return The yesterday date.
     */
    public static Calendar yesterday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        return truncate(c, Calendar.DAY_OF_MONTH);
    }

    /**
     * Returns the tomorrow date, <b>truncating</b> time info.
     *
     * @return The tomorrow date.
     */
    public static Calendar tomorrow() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        return truncate(c, Calendar.DAY_OF_MONTH);
    }

    /**
     * Returns the current date, <b>including</b> time info.
     *
     * @return The current date, including time info.
     */
    public static Calendar now() {
        return Calendar.getInstance();
    }

    public static Calendar getTimeTruncated(final Object date) {
        return getTimeTruncatedInternal(date);
    }

    public static Calendar getTimeMaxed(final Object date) {
        ArgumentUtils.rejectIfNull(date);
        Calendar c = (Calendar) castToCalendar(date).clone();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c;
    }

    public static Calendar getFirstDateOfMonth(final Integer month, final Integer year) {
        ArgumentUtils.rejectIfAnyNull(month, year);
        ArgumentUtils.rejectIfOutOfBounds(month, Calendar.JANUARY, Calendar.DECEMBER);
        return (new GregorianCalendar(year, month, 1, 0, 0, 0));
    }

    public static Calendar getFirstDateOfMonth(final Object date) {
        ArgumentUtils.rejectIfNull(date);
        final Calendar c = (Calendar) castToCalendar(date).clone();
        return getFirstDateOfMonth(c.get(Calendar.MONTH), c.get(Calendar.YEAR));
    }

    public static Calendar getLastDateOfMonth(final Integer month, final Integer year) {
        ArgumentUtils.rejectIfAnyNull(month, year);
        ArgumentUtils.rejectIfOutOfBounds(month, Calendar.JANUARY, Calendar.DECEMBER);
        return getLastDateOfMonth(new GregorianCalendar(year, month, 1));
    }

    public static Calendar getLastDateOfMonth(final Object date) {
        ArgumentUtils.rejectIfNull(date);
        Calendar c = getTimeMaxed(date);
        c.set(Calendar.DAY_OF_MONTH, getLastDayOfMonth(date));
        return c;
    }

    public static Calendar getSmallest(final boolean ignoreTime, final Object... dates) {
        return getSmallest(ignoreTime, Arrays.asList(dates));
    }

    public static Calendar getSmallest(final boolean ignoreTime, final Collection<Object> dates) {
        if (CollectionUtils.isEmptyOrNull(dates)) {
            return null;
        }
        final DateComparator comparator = new DateComparator(ignoreTime);
        Calendar smallest = null;
        for (Object obj : dates) {
            if (obj != null) {
                final Calendar current = castToCalendar(obj);
                if (smallest == null) {
                    smallest = current;
                } else {
                    if (comparator.compare(smallest, current) > 0) {
                        // currently 'smallest' isn't the smallest. swap!
                        smallest = current;
                    }
                }
            }
        }
        return smallest;
    }

    public static Calendar getLargest(final boolean ignoreTime, final Object... dates) {
        return getLargest(ignoreTime, Arrays.asList(dates));
    }

    public static Calendar getLargest(final boolean ignoreTime, final Collection<Object> dates) {
        if (CollectionUtils.isEmptyOrNull(dates)) {
            return null;
        }
        final DateComparator comparator = new DateComparator(ignoreTime);
        Calendar largest = null;
        for (Object obj : dates) {
            if (obj != null) {
                final Calendar current = castToCalendar(obj);
                if (largest == null) {
                    largest = current;
                } else {
                    if (comparator.compare(largest, current) < 0) {
                        // currently 'largest' isn't the smallest. swap!
                        largest = current;
                    }
                }
            }
        }
        return largest;
    }

    public static Calendar parse(final CharSequence date, final CharSequence pattern) {
        return parse(date, pattern, Locale.getDefault());
    }

    public static Calendar parse(final CharSequence date, final CharSequence pattern, final Locale locale) {
        return (new CustomDateFormat(pattern.toString(), locale)).parseCalendar(date);
    }
}
