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

import br.msf.commons.temporal.Age;
import br.msf.commons.temporal.DateComparator;
import br.msf.commons.text.CustomDateFormat;
import br.msf.commons.util.ArgumentUtils;
import br.msf.commons.util.CollectionUtils;
import br.msf.commons.util.LocaleUtils;
import br.msf.commons.util.NumberUtils;
import br.msf.commons.util.ObjectUtils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 * Various utilities for {@link Date Dates} and {@link Calendar Calendars}.
 * <p/>
 * All methods accepts {@link Date} and {@link Calendar} objects and tries to handle
 * null values gracefully.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public abstract class AbstractDateUtils extends org.apache.commons.lang3.time.DateUtils {

    protected static final Logger LOGGER = Logger.getLogger(DateUtils.class.getName());

    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
    /**
     * BigDecimal representing the quantity of contained in one day .
     * <p/>
     * value = (1000 * 60 * 60 * 24) = 86.400.000;
     */
    private static final BigDecimal MILLISECS_PER_DAY = new BigDecimal(MILLIS_PER_DAY);
    /**
     * Business time.
     * <p/>
     * Value = from [8:00 - 12:00[ and [14:00 - 18:00[
     */
    public static final int[] BUSINESS_TIME = {8, 12, 14, 18};

    /**
     * Returns the current year.
     *
     * @return The current year.
     */
    public static Integer year() {
        return year(Calendar.getInstance());
    }

    /**
     * Returns the year of the given date.
     *
     * @param date The date to be evaluated.
     * @return The year of the given date.
     */
    public static Integer year(final Object date) {
        ArgumentUtils.rejectIfNull(date);
        return castToCalendar(date).get(Calendar.YEAR);
    }

    /**
     * Returns the current day of month.
     *
     * @return The current day of month.
     */
    public static Integer dayOfMonth() {
        return dayOfMonth(Calendar.getInstance());
    }

    /**
     * Returns the day of month of the given date.
     *
     * @param date The date to be evaluated.
     * @return The day of month of the given date.
     */
    public static Integer dayOfMonth(final Object date) {
        ArgumentUtils.rejectIfNull(date);
        return castToCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Returns the current day of week name.
     *
     * @return The current day of week name.
     */
    public static String dayOfWeek() {
        return dayOfWeek(Calendar.getInstance(), null);
    }

    /**
     * Returns the day of week name for the given date.
     *
     * @param date The date to be evaluated.
     * @return The day of week name for the given date.
     */
    public static String dayOfWeek(final Object date) {
        return dayOfWeek(date, null);
    }

    /**
     * Returns the day of week name for the given date.
     *
     * @param date   The date to be evaluated.
     * @param locale The desired locale.
     * @return The day of week name for the given date.
     */
    public static String dayOfWeek(final Object date, final Locale locale) {
        ArgumentUtils.rejectIfNull(date);
        return castToCalendar(date).getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, LocaleUtils.getNullSafeLocale(locale));
    }

    /**
     * Returns the current month.
     * <p/>
     * Beware! its zero indexed, so JANUARY = 0, FEBRUARY = 1 and so on...
     *
     * @return The current month.
     */
    public static int month() {
        return month(Calendar.getInstance());
    }

    /**
     * Returns the month for the given date.
     * <p/>
     * Beware! its zero indexed, so JANUARY = 0, FEBRUARY = 1 and so on...
     *
     * @param date The date to be evaluated.
     * @return The month for the given date.
     */
    public static int month(final Object date) {
        ArgumentUtils.rejectIfNull(date);
        return castToCalendar(date).get(Calendar.MONTH);
    }

    /**
     * Returns the current month name.
     *
     * @return The current month name.
     */
    public static String monthName() {
        return monthName(Calendar.getInstance(), null);
    }

    /**
     * Returns the month name for the given date.
     *
     * @param date The date to be evaluated.
     * @return The month name for the given date.
     */
    public static String monthName(final Object date) {
        return monthName(date, null);
    }

    /**
     * Returns the month name for the given date.
     *
     * @param date   The date to be evaluated.
     * @param locale The desired locale.
     * @return The month name for the given date.
     */
    public static String monthName(final Object date, final Locale locale) {
        ArgumentUtils.rejectIfNull(date);
        return castToCalendar(date).getDisplayName(Calendar.MONTH, Calendar.LONG, LocaleUtils.getNullSafeLocale(locale));
    }

    /**
     * Returns the month name for the given month index.
     * <p/>
     * Beware! its zero indexed, so JANUARY = 0, FEBRUARY = 1 and so on...
     *
     * @param month The month index.
     * @return The month name for the given index.
     */
    public static String monthName(final int month) {
        return monthName(month, null);
    }

    /**
     * Returns the month name for the given month index.
     * <p/>
     * Beware! its zero indexed, so JANUARY = 0, FEBRUARY = 1 and so on...
     *
     * @param month  The month index.
     * @param locale The desired locale.
     * @return The month name for the given index.
     */
    public static String monthName(final int month, final Locale locale) {
        if (month < Calendar.JANUARY || month > Calendar.DECEMBER) {
            throw new IllegalArgumentException("Month must be between [Calendar.JANUARY, Calendar.DECEMBER].");
        }
        return (new GregorianCalendar(year(), month, 1)).getDisplayName(Calendar.MONTH, Calendar.LONG, LocaleUtils.getNullSafeLocale(locale));
    }

    public static boolean isSame(final Object date1, final Object date2) {
        return isSame(date1, date2, false);
    }

    public static boolean isSame(final Object date1, final Object date2, final boolean ignoreTimeInfo) {
        ArgumentUtils.rejectIfAnyNull(date1, date2);
        return compare(date1, date2, ignoreTimeInfo) == 0;
    }

    public static boolean isAfter(final Object date1, final Object date2) {
        return isAfter(date1, date2, false);
    }

    public static boolean isAfter(final Object date1, final Object date2, final boolean ignoreTimeInfo) {
        ArgumentUtils.rejectIfAnyNull(date1, date2);
        return compare(date1, date2, ignoreTimeInfo) > 0;
    }

    public static boolean isSameOrAfter(final Object date1, final Object date2) {
        return isSameOrAfter(date1, date2, false);
    }

    public static boolean isSameOrAfter(final Object date1, final Object date2, final boolean ignoreTimeInfo) {
        ArgumentUtils.rejectIfAnyNull(date1, date2);
        return compare(date1, date2, ignoreTimeInfo) >= 0;
    }

    public static boolean isBefore(final Object date1, final Object date2) {
        return isBefore(date1, date2, false);
    }

    public static boolean isBefore(final Object date1, final Object date2, final boolean ignoreTimeInfo) {
        ArgumentUtils.rejectIfAnyNull(date1, date2);
        return compare(date1, date2, ignoreTimeInfo) < 0;
    }

    public static boolean isSameOrBefore(final Object date1, final Object date2) {
        return isSameOrBefore(date1, date2, false);
    }

    public static boolean isSameOrBefore(final Object date1, final Object date2, final boolean ignoreTimeInfo) {
        ArgumentUtils.rejectIfAnyNull(date1, date2);
        return compare(date1, date2, ignoreTimeInfo) <= 0;
    }

    public static boolean isFirstDayOfMonth(final Object date) {
        ArgumentUtils.rejectIfNull(date);
        return castToCalendar(date).get(Calendar.DAY_OF_MONTH) == 1;
    }

    public static boolean isLastDayOfMonth(final Object date) {
        return getLastDayOfMonth(date).equals(castToCalendar(date).get(Calendar.DAY_OF_MONTH));
    }

    public static boolean isBusinessDay(final Object date) {
        return isBusinessDay(date, getBrasilianHolidays(year(date)));
    }

    public static boolean isBusinessDay(final Object date, final Collection<? extends Object> holidays) {
        if (date == null) {
            return false;
        }
        int dayOfWeek = castToCalendar(date).get(Calendar.DAY_OF_WEEK);
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY && !isHoliday(date, holidays);
    }

    public static boolean isHoliday(final Object date) {
        return isHoliday(date, getBrasilianHolidays(year(date)));
    }

    public static boolean isHoliday(final Object date, final Collection<? extends Object> holidays) {
        ArgumentUtils.rejectIfNull(date);
        if (CollectionUtils.isEmptyOrNull(holidays)) {
            return false;
        }
        Calendar c = castToCalendar(date);
        boolean isHoliday = false;
        if (!CollectionUtils.isEmptyOrNull(holidays)) {
            for (Object tmp : holidays) {
                Calendar holiday = castToCalendar(tmp);
                isHoliday = isSameDay(holiday, c);
                if (isHoliday) {
                    break;
                }
            }
        }
        return isHoliday;
    }

    public static boolean isBusinessTime(final Object date) {
        if (date == null) {
            return false;
        }
        int hour = castToCalendar(date).get(Calendar.HOUR_OF_DAY);
        return (hour >= BUSINESS_TIME[0] && hour < BUSINESS_TIME[1]) || (hour >= BUSINESS_TIME[2] && hour < BUSINESS_TIME[3]);
    }

    public static BigDecimal getDifferenceInDays(final Object date1, final Object date2) {
        return getDifferenceInDays(date1, date2, false);
    }

    public static BigDecimal getDifferenceInDays(final Object date1, final Object date2, final boolean ignoreTimeInfo) {
        ArgumentUtils.rejectIfAnyNull(date1, date2);

        Calendar c1 = toUtcCalendar(date1);
        Calendar c2 = toUtcCalendar(date2);
        if (ignoreTimeInfo) {
            c1 = getTimeTruncatedInternal(c1);
            c2 = getTimeTruncatedInternal(c2);
        }
        final long l1 = c1.getTimeInMillis();
        final long l2 = c2.getTimeInMillis();
        BigDecimal res;
        if (l1 > l2) {
            res = new BigDecimal(l1 - l2);
        } else {
            res = new BigDecimal(l2 - l1);
        }
        return NumberUtils.round(res.divide(MILLISECS_PER_DAY, MathContext.DECIMAL128), 2);
    }

    public static Integer getQuantDaysBetween(final Object date1, final Object date2) {
        ArgumentUtils.rejectIfAnyNull(date1, date2);
        return getDifferenceInDays(date1, date2, true).intValue() + 1;
    }

    public static Integer getLastDayOfMonth(final Integer month, final Integer year) {
        ArgumentUtils.rejectIfAnyNull(month, year);
        ArgumentUtils.rejectIfOutOfBounds(month, Calendar.JANUARY, Calendar.DECEMBER);
        return getLastDayOfMonth(new GregorianCalendar(year, month, 1));
    }

    public static Integer getLastDayOfMonth(final Object date) {
        ArgumentUtils.rejectIfNull(date);
        Calendar c = castToCalendar(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Age getAge(final Object date1, final Object date2) {
        final Age age = new Age();
        age.setYears(count(Calendar.YEAR, addAge(date1, age), date2));
        age.setMonths(count(Calendar.MONTH, addAge(date1, age), date2));
        age.setDays(count(Calendar.DAY_OF_MONTH, addAge(date1, age), date2));
        age.setHours(count(Calendar.HOUR_OF_DAY, addAge(date1, age), date2));
        age.setMinutes(count(Calendar.MINUTE, addAge(date1, age), date2));
        age.setSeconds(count(Calendar.SECOND, addAge(date1, age), date2));
        age.setMilliseconds(count(Calendar.MILLISECOND, addAge(date1, age), date2));
        return age;
    }

    public static String format(final Object date, final String pattern) {
        return format(date, pattern, null);
    }

    public static String format(final Object date, final String pattern, final Locale locale) {
        return (date != null) ? new CustomDateFormat(pattern, locale).format(date) : null;
    }

    public static String formatDate(final Object date) {
        return formatDate(date, null);
    }

    public static String formatDate(final Object date, final Locale locale) {
        final DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, LocaleUtils.getNullSafeLocale(locale));
        return df.format(castToDate(date));
    }

    public static String formatTime(final Object date) {
        return formatTime(date, null);
    }

    public static String formatTime(final Object date, final Locale locale) {
        final DateFormat df = SimpleDateFormat.getTimeInstance(DateFormat.MEDIUM, LocaleUtils.getNullSafeLocale(locale));
        return df.format(castToDate(date));
    }

    public static String formatDateTime(final Object date) {
        return formatDateTime(date, null);
    }

    public static String formatDateTime(final Object date, final Locale locale) {
        final DateFormat df = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, LocaleUtils.getNullSafeLocale(locale));
        return df.format(castToDate(date));
    }

    public static int compare(final Object date1, final Object date2) {
        return compare(date1, date2, false);
    }

    public static int compare(final Object date1, final Object date2, final boolean ignoreTime) {
        return new DateComparator(ignoreTime).compare(castToCalendar(date1), castToCalendar(date2));
    }

    public static Calendar castToCalendar(final Object date) {
        if (date == null) {
            return null;
        }
        if (ObjectUtils.isCalendar(date)) {
            return (Calendar) date;
        } else if (ObjectUtils.isDate(date)) {
            Calendar c = Calendar.getInstance();
            c.setTime((Date) date);
            return c;
        } else {
            throw new IllegalArgumentException("Argument must be Date or Calendar");
        }
    }

    /**
     * Converte a data para {@link Calendar}. O argumento deve ser um {@link Date} ou {@link Calendar}.
     * <p/>
     * Se date for do tipo Calendar, apenas retorna o cast: <code>return (Calendar) date</code>.
     * <p/>
     * Senão, retorna um <code>(new Calendar()).setTime((Date) date);</code>
     *
     * @param date          O objeto a ser convertido.
     * @param assureLenient Indica se o Calendar retornado deve ter a propriedade "lenient" forçada para "true".
     * @return O Calendar convertido de date.
     */
    public static Calendar castToCalendar(final Object date, final boolean assureLenient) {
        if (date == null) {
            return null;
        }
        final Calendar c;
        if (isCalendar(date)) {
            c = (Calendar) date;
        } else if (isDate(date)) {
            c = Calendar.getInstance();
            c.setTime((Date) date);
        } else {
            throw new IllegalArgumentException("Argument must be Date or Calendar");
        }
        if (assureLenient) {
            c.setLenient(true);
        }
        return c;
    }

    public static Date castToDate(final Object date) {
        if (date == null) {
            return null;
        } else if (ObjectUtils.isCalendar(date)) {
            return ((Calendar) date).getTime();
        } else if (ObjectUtils.isDate(date)) {
            return (Date) date;
        } else {
            throw new IllegalArgumentException("Argument must be Date or Calendar");
        }
    }

    public static boolean isDate(final Object date) {
        return ObjectUtils.isDate(date);
    }

    public static boolean isDate(final Object date, final boolean acceptNull) {
        return ObjectUtils.isDate(date, acceptNull);
    }

    public static boolean isCalendar(final Object date) {
        return ObjectUtils.isCalendar(date);
    }

    public static boolean isCalendar(final Object date, final boolean acceptNull) {
        return ObjectUtils.isCalendar(date, acceptNull);
    }

    public static CharSequence getNullSafePattern(final CharSequence pattern) {
        return CharSequenceUtils.isNotBlank(pattern) ? pattern : DATE_PATTERN;
    }

    protected static Calendar getTimeTruncatedInternal(final Object date) {
        if (date == null) {
            return null;
        }
        return truncate(castToCalendar(date), Calendar.DAY_OF_MONTH);
    }

    protected static Collection<Calendar> getBrasilianHolidays(final int year) {
        Collection<Calendar> holidays = new ArrayList<Calendar>();

        // feriados fixos
        holidays.add(new GregorianCalendar(year, Calendar.JANUARY, 1)); // Confraternizacao Universal
        holidays.add(new GregorianCalendar(year, Calendar.APRIL, 21)); // Tiradentes
        holidays.add(new GregorianCalendar(year, Calendar.MAY, 1)); // Dia do Trabalho
        holidays.add(new GregorianCalendar(year, Calendar.OCTOBER, 12)); // Nossa Senhora Aparecida
        holidays.add(new GregorianCalendar(year, Calendar.NOVEMBER, 2)); // Finados
        holidays.add(new GregorianCalendar(year, Calendar.NOVEMBER, 15)); // Proclamacao da Republica
        holidays.add(new GregorianCalendar(year, Calendar.DECEMBER, 25)); // Natal

        // feriados calculados
        Calendar pascoa = getSundayOfEaster(year);
        Calendar carnaval = (Calendar) pascoa.clone();
        carnaval.add(Calendar.DAY_OF_MONTH, -47);
        Calendar corpusChristi = (Calendar) pascoa.clone();
        corpusChristi.add(Calendar.DAY_OF_MONTH, 60);

        holidays.add(carnaval); // Carnaval
        holidays.add(pascoa); // Pascoa
        holidays.add(corpusChristi); // Corpus Christi

        return holidays;
    }

    protected static Calendar getSundayOfEaster(final int year) {
        // step 2
        int a = year % 19;
        // step 3
        int b = year / 100;
        int c = year % 100;
        // step 4
        int d = b / 4;
        int e = b % 4;
        // step 5
        int g = (8 * b + 13) / 25;
        // step 6
        int h = (19 * a + b - d - g + 15) % 30;
        // step 7
        int j = c / 4;
        int k = c % 4;
        // step 8
        int m = (a + 11 * h) / 319;
        // step 9
        int r = (2 * e + 2 * j - k - h + m + 32) % 7;
        // step 10
        int n = (h - m + r + 90) / 25;
        // step 11 (finally)
        int p = (h - m + r + n + 19) % 32;
        return new GregorianCalendar(year, n - 1, p);
    }

    protected static Calendar toUtcCalendar(final Object date) {
        Calendar c = new GregorianCalendar(UTC_TIME_ZONE);
        Calendar tmp = castToCalendar(date);
        c.set(Calendar.YEAR, tmp.get(Calendar.YEAR));
        c.set(Calendar.MONTH, tmp.get(Calendar.MONTH));
        c.set(Calendar.DAY_OF_MONTH, tmp.get(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, tmp.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, tmp.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, tmp.get(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, tmp.get(Calendar.MILLISECOND));
        return c;
    }

    protected static int count(final int field, final Object date1, final Object date2) {
        ArgumentUtils.rejectIfAnyNull(date1, date2);
        final Calendar c1 = toUtcCalendar(date1);
        final Calendar c2 = toUtcCalendar(date2);
        int count = 0;
        while (true) {
            c1.add(field, 1);
            if (isSameOrBefore(c1, c2)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    protected static Calendar addAge(final Object date, final Age age) {
        if (date == null) {
            return null;
        }
        final Calendar c = toUtcCalendar(date);
        c.setLenient(true);
        if (age != null) {
            c.add(Calendar.YEAR, age.getYears());
            c.add(Calendar.MONTH, age.getMonths());
            c.add(Calendar.DAY_OF_MONTH, age.getDays());
            c.add(Calendar.HOUR_OF_DAY, age.getHours());
            c.add(Calendar.MINUTE, age.getMinutes());
            c.add(Calendar.SECOND, age.getSeconds());
            c.add(Calendar.MILLISECOND, age.getMilliseconds());
        }
        return c;
    }

    public static void main(String[] args) {
        Age age;
        Calendar c1, c2;

        c1 = new GregorianCalendar(2012, Calendar.JANUARY, 1);
        c2 = new GregorianCalendar(2012, Calendar.JANUARY, 1);
        age = getAge(c1, c2);
        System.out.println(age);

        c1 = new GregorianCalendar(2012, Calendar.JANUARY, 1);
        c2 = new GregorianCalendar(2012, Calendar.FEBRUARY, 1);
        age = getAge(c1, c2);
        System.out.println(age);

        c1 = new GregorianCalendar(2012, Calendar.JANUARY, 1);
        c2 = Calendar.getInstance();
        age = getAge(c1, c2);
        System.out.println(age);
    }
}
