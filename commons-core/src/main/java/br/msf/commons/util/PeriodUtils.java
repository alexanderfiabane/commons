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

import br.msf.commons.temporal.DateComparator;
import br.msf.commons.temporal.Period;
import br.msf.commons.temporal.PeriodComparator;
import br.msf.commons.temporal.SimplePeriod;
import br.msf.commons.text.CustomDateFormat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public abstract class PeriodUtils {

    public static final String DEFAULT_PERIOD_SEPARATOR = " - ";
    public static final String DEFAULT_NULL_DATE_TEXT = "   ";

    public static Period truncate(final Period period, final int field) {
        if (period == null) {
            return null;
        }
        final Period newPeriod = (Period) ReflectionUtils.cloneObject(period);
        newPeriod.setStart(CalendarUtils.truncate(period.getStart(), field));
        newPeriod.setEnd(CalendarUtils.truncate(period.getEnd(), field));
        return newPeriod;
    }

    public static boolean isEmpty(final Period period) {
        return period == null || (period.getStart() == null && period.getEnd() == null);
    }

    public static boolean isBefore(final Period period, final Object data) {
        return isBefore(period, data, false, false);
    }

    public static boolean isBefore(final Period period, final Object data, final boolean inclusive, final boolean ignoreTime) {
        if (period == null || data == null || period.getEnd() == null) {
            return false;
        }
        final int compare;
        if (ignoreTime) {
            compare = CalendarUtils.truncatedCompareTo(period.getEnd(), CalendarUtils.castToCalendar(data), Calendar.DAY_OF_MONTH);
        } else {
            compare = period.getEnd().compareTo(CalendarUtils.castToCalendar(data));
        }
        return inclusive ? compare <= 0 : compare < 0;
    }

    public static boolean isAfter(final Period period, final Object data) {
        return isAfter(period, data, false, false);
    }

    public static boolean isAfter(final Period period, final Object data, final boolean inclusive, final boolean ignoreTime) {
        if (period == null || data == null || period.getStart() == null) {
            return false;
        }
        final int compare;
        if (ignoreTime) {
            compare = CalendarUtils.truncatedCompareTo(period.getStart(), CalendarUtils.castToCalendar(data), Calendar.DAY_OF_MONTH);
        } else {
            compare = period.getStart().compareTo(CalendarUtils.castToCalendar(data));
        }
        return inclusive ? compare >= 0 : compare > 0;
    }

    public static boolean isCurrent(final Period period) {
        return contains(period, CalendarUtils.now(), false);
    }

    public static boolean isCurrent(final Period period, final boolean ignoreTime) {
        return contains(period, CalendarUtils.now(), ignoreTime);
    }

    public static boolean isValid(final Period period) {
        return isValid(period, false);
    }

    public static boolean isValid(final Period period, final boolean acceptNullDate) {
        if (acceptNullDate) {
            return period != null
                   && (period.getStart() == null || period.getEnd() == null
                       || CalendarUtils.isSameOrBefore(period.getStart(), period.getEnd(), false));

        } else {
            return period != null
                   && (period.getStart() != null && period.getEnd() != null
                       && (CalendarUtils.isSameOrBefore(period.getStart(), period.getEnd(), false)));
        }
    }

    public static Period getMonthPeriod() {
        return getMonthPeriod(CalendarUtils.month(), CalendarUtils.year());
    }

    public static Period getMonthPeriod(final int month, final int year) {
        final Period period = new SimplePeriod();
        period.setStart(CalendarUtils.getFirstDateOfMonth(month, year));
        period.setEnd(CalendarUtils.getLastDateOfMonth(month, year));
        return period;
    }

    public static Period getYearPeriod() {
        return getYearPeriod(CalendarUtils.year());
    }

    public static Period getYearPeriod(final int year) {
        final Period period = new SimplePeriod();
        period.setStart(CalendarUtils.getFirstDateOfMonth(Calendar.JANUARY, year));
        period.setEnd(CalendarUtils.getLastDateOfMonth(Calendar.DECEMBER, year));
        return period;
    }

    public static Period getMonthPeriod(final Object date) {
        final Calendar c = CalendarUtils.castToCalendar(date);
        return getMonthPeriod(c.get(Calendar.MONTH), c.get(Calendar.YEAR));
    }

    public static Period getContainerPeriod(final boolean ignoreTime, final Period... periods) {
        return getContainerPeriod(ignoreTime, Arrays.asList(periods));
    }

    public static Period getContainerPeriod(final boolean ignoreTime, final Collection<Period> periods) {
        if (CollectionUtils.isEmptyOrNull(periods)) {
            return null;
        }
        final DateComparator comparator = new DateComparator(ignoreTime);
        Calendar smallestInicio = null;
        Calendar largestFim = null;
        for (Period p : periods) {
            if (p != null) {
                final Calendar currentInicio = p.getStart();
                final Calendar currentFim = p.getEnd();
                if (smallestInicio == null) {
                    smallestInicio = currentInicio;
                } else {
                    if (comparator.compare(smallestInicio, currentInicio) > 0) {
                        // currently 'smallestInicio' isn't the smallest. swap!
                        smallestInicio = currentInicio;
                    }
                }
                if (largestFim == null) {
                    largestFim = currentFim;
                } else {
                    if (comparator.compare(largestFim, currentFim) > 0) {
                        // currently 'largestFim' isn't the smallest. swap!
                        largestFim = currentFim;
                    }
                }
            }
        }
        final Period p = new SimplePeriod();
        p.setStart(smallestInicio);
        p.setEnd(largestFim);
        return p;
    }

    public static Period getCurrentMonthPeriod() {
        return getMonthPeriod(CalendarUtils.now());
    }

    public static BigDecimal getTimeInDays(final Period period) {
        if (!isValid(period, false)) {
            throw new IllegalArgumentException("Invalid period: " + period);
        }
        return CalendarUtils.getDifferenceInDays(period.getStart(), period.getEnd(), false);
    }

    public static BigDecimal getDaysElapsedSinceBegin(final Period period) {
        if (period == null) {
            throw new IllegalArgumentException("Null period.");
        }
        Calendar hoje = CalendarUtils.now();
        if (period.getStart() != null && CalendarUtils.isAfter(hoje, period.getStart())) {
            return CalendarUtils.getDifferenceInDays(hoje, period.getStart());
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal getDaysElapsedSinceEnd(final Period period) {
        if (period == null) {
            throw new IllegalArgumentException("Null period.");
        }
        Calendar hoje = CalendarUtils.now();
        if (period.getEnd() != null && CalendarUtils.isAfter(hoje, period.getEnd())) {
            return CalendarUtils.getDifferenceInDays(hoje, period.getEnd());
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal getDaysRemainingToBegin(final Period period) {
        if (period == null) {
            throw new IllegalArgumentException("Null period.");
        }
        Calendar hoje = CalendarUtils.now();
        if (period.getStart() != null && CalendarUtils.isBefore(hoje, period.getStart())) {
            return CalendarUtils.getDifferenceInDays(period.getStart(), hoje);
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal getDaysRemainingToEnd(final Period period) {
        if (period == null) {
            throw new IllegalArgumentException("Null period.");
        }
        Calendar hoje = CalendarUtils.now();
        if (period.getEnd() != null && CalendarUtils.isBefore(hoje, period.getEnd())) {
            return CalendarUtils.getDifferenceInDays(period.getEnd(), hoje);
        }
        return BigDecimal.ZERO;
    }

    public static Integer getQuantDays(final Period period) {
        if (!isValid(period, false)) {
            throw new IllegalArgumentException("Invalid period: " + period);
        }
        return CalendarUtils.getQuantDaysBetween(period.getStart(), period.getEnd());
    }

    public static boolean contains(final Period period, final Period outroPeriod) {
        // by default, do 'inclusive' of the extremities and doesnt ignores the time info
        return contains(period, outroPeriod, true, true, false);
    }

    public static boolean contains(final Period period, final Period outroPeriod, final boolean leftInclusive,
                                   final boolean rightInclusive, final boolean ignoreTime) {
        if (!isValid(period) || !isValid(outroPeriod)) {
            throw new IllegalArgumentException("Invalid period: " + period);
        }
        final boolean left;
        if (leftInclusive) {
            left = CalendarUtils.isSameOrAfter(outroPeriod.getStart(), period.getStart(), ignoreTime);
        } else {
            left = CalendarUtils.isAfter(outroPeriod.getStart(), period.getStart(), ignoreTime);
        }

        final boolean right;
        if (rightInclusive) {
            right = CalendarUtils.isSameOrBefore(outroPeriod.getEnd(), period.getEnd(), ignoreTime);
        } else {
            right = CalendarUtils.isBefore(outroPeriod.getEnd(), period.getEnd(), ignoreTime);
        }
        return left && right;
    }

    public static boolean contains(final Period period, final Object data) {
        return contains(period, data, false);
    }

    public static boolean contains(final Period period, final Object data, final boolean ignoreTime) {
        if (period == null || data == null) {
            return false;
        }
        Calendar inicio = period.getStart();
        Calendar fim = period.getEnd();
        if (inicio != null && CalendarUtils.isBefore(data, inicio, ignoreTime)) {
            return false; // ainda nao chegou no prazo inicial
        } else {
            return (fim == null || !CalendarUtils.isAfter(data, fim, ignoreTime));
        }
    }

    public static Period intersection(final Period... periods) {
        return intersection(Arrays.asList(periods));
    }

    public static Period intersection(final Collection<Period> periods) {
        if (CollectionUtils.isEmptyOrNull(periods)) {
            return null;
        }
        final Period intersection = (Period) ReflectionUtils.cloneObject(periods.iterator().next());
        if (periods.size() > 1) {
            /*
             * visit all periods, searching for the biggest start and smallest end.
             * If it is a valid period, we have a intersection.
             * Else, there is no intersection.
             */
            for (Period current : periods) {
                if ((intersection.getStart() == null && current.getStart() != null)
                    || (intersection.getStart() != null && current.getStart() != null
                        && CalendarUtils.isBefore(intersection.getStart(), current.getStart()))) {
                    intersection.setStart(current.getStart());
                }
                if ((intersection.getEnd() == null && current.getEnd() != null)
                    || (intersection.getEnd() != null && current.getEnd() != null
                        && CalendarUtils.isAfter(intersection.getEnd(), current.getEnd()))) {
                    intersection.setEnd(current.getEnd());
                }
            }
            if (!PeriodUtils.isValid(intersection, true)) {
                return null;
            }
        }
        return intersection;
    }

    public static boolean hasConcurrence(final Collection<Period> periods) {
        if (CollectionUtils.isEmptyOrNull(periods) || periods.size() == 1) {
            return false;
        }
        final List<Period> tmp = asList(periods);
        for (int i = 0; i < tmp.size(); i++) {
            final Period p1 = tmp.get(i);
            for (int j = i + 1; j < tmp.size(); j++) {
                final Period p2 = tmp.get(j);
                if (intersection(p1, p2) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String format(final Period period, final String dateTimePattern) {
        return format(period, dateTimePattern, DEFAULT_PERIOD_SEPARATOR, false);
    }

    public static String format(final Period period, final String dateTimePattern, final String entreDatas) {
        return format(period, dateTimePattern, entreDatas, false);
    }

    public static String format(final Period period, final String dateTimePattern, final String entreDatas, final boolean ordinalOnFirstDayOfMonth) {
        if (period == null) {
            return null;
        }
        if (CharSequenceUtils.isEmptyOrNull(dateTimePattern)) {
            throw new IllegalArgumentException("DateTimePattern null or empty.");
        }
        final CustomDateFormat formatter = new CustomDateFormat(dateTimePattern);
        formatter.setOrdinalOnFirstDayOfMonth(ordinalOnFirstDayOfMonth);
        return mountPeriod(period, formatter, entreDatas);
    }

    public static Period parse(final CharSequence inicio, final CharSequence fim, final CharSequence pattern) {
        final Period p = new SimplePeriod();
        p.setStart(CalendarUtils.parse(inicio, pattern));
        p.setEnd(CalendarUtils.parse(fim, pattern));
        return p;
    }

    public static int compare(final Period period1, final Period period2) {
        return compare(period1, period2, false);
    }

    public static int compare(final Period period1, final Period period2, final boolean ignoreTime) {
        return (new PeriodComparator(ignoreTime)).compare(period1, period2);
    }

    private static String mountPeriod(final Period period, final CustomDateFormat formatter, final String entreDatas) {
        StringBuilder builder = new StringBuilder();
        if (period.getStart() != null) {
            builder.append(formatter.format(period.getStart()));
        } else {
            builder.append(DEFAULT_NULL_DATE_TEXT);
        }
        if (CharSequenceUtils.isEmptyOrNull(entreDatas)) {
            builder.append(DEFAULT_PERIOD_SEPARATOR);
        } else {
            builder.append(entreDatas);
        }
        if (period.getEnd() != null) {
            builder.append(formatter.format(period.getEnd()));
        } else {
            builder.append(DEFAULT_NULL_DATE_TEXT);
        }
        return builder.toString();
    }

    private static List<Period> asList(final Collection<Period> collection) {
        if (collection == null) {
            return null;
        }
        if (List.class.isAssignableFrom(collection.getClass())) {
            return (List<Period>) collection;
        } else {
            return new ArrayList<Period>(collection);
        }
    }

    public static String formatDuration(final Period period) {
        return formatDuration(period, true, 5);
    }

    public static String formatDuration(final Period period, final int numFields) {
        return formatDuration(period, true, numFields);
    }

    public static String formatDuration(final Period period, final boolean ignoreZeroes, final int numFields) {
        if (!isValid(period, false)) {
            throw new IllegalArgumentException("Invalid period");
        }
        long diff, rest;
        final Long[] decomp = {null, null, null, null, null};
        final String[] names = {"dia", "hora", "minuto", "segundo", "milissegundo"};

        diff = period.getEnd().getTimeInMillis() - period.getStart().getTimeInMillis();
        rest = diff % CalendarUtils.MILLIS_PER_DAY;
        decomp[0] = (diff - rest) / CalendarUtils.MILLIS_PER_DAY;

        diff = rest;
        rest = diff % CalendarUtils.MILLIS_PER_HOUR;
        if (numFields > 1) {
            decomp[1] = (diff - rest) / CalendarUtils.MILLIS_PER_HOUR;
        }

        diff = rest;
        rest = diff % CalendarUtils.MILLIS_PER_MINUTE;
        if (numFields > 2) {
            decomp[2] = (diff - rest) / CalendarUtils.MILLIS_PER_MINUTE;
        }

        diff = rest;
        rest = diff % CalendarUtils.MILLIS_PER_SECOND;
        if (numFields > 3) {
            decomp[3] = (diff - rest) / CalendarUtils.MILLIS_PER_SECOND;
        }

        if (numFields > 4) {
            decomp[4] = rest;
        }

        final List<String> tmp = new ArrayList<String>(decomp.length);
        for (int i = 0; i < decomp.length; i++) {
            if (decomp[i] != null && (decomp[i] > 0 || !ignoreZeroes)) {
                final StringBuilder b = new StringBuilder().
                        append(decomp[i]).append(" ").append(names[i]);
                if (decomp[i] != 1) {
                    // plural
                    b.append("s");
                }
                tmp.add(b.toString());
            }
        }
        if (tmp.isEmpty()) {
            return "0 dias";
        }

        final StringBuilder builder = new StringBuilder(tmp.get(0));
        for (int i = 1; i < tmp.size(); i++) {
            // append separator
            builder.append((i < tmp.size() - 1) ? ", " : " e ");
            builder.append(tmp.get(i));
        }
        return builder.toString();
    }
}
