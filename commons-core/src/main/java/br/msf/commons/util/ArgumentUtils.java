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

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Class used to check arguments passed to methods.
 * <p/>
 * Basically it serves to evict lots of <tt>if statements</tt> to check method arguments.
 * <p/>
 * If an argument is rejected, it will throw {@link IllegalArgumentException}.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 * Date: 21/08/12
 * Time: 16:45
 */
public abstract class ArgumentUtils {

    /**
     * Throws an IllegalArgumentException if the given object is null.
     *
     * @param arg The object to be checked.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfNull(final Object arg) {
        if (arg == null) {
            throw new IllegalArgumentException("Argument can't be null.");
        }
    }

    /**
     * Throws an IllegalArgumentException if any object in the given array is null.
     * <p/>
     * If the array itself is null, an IllegalArgumentException will be thrown too.
     *
     * @param args The array of objects to be checked.
     *
     * @throws IllegalArgumentException If any of the the given arguments are rejected.
     */
    public static void rejectIfAnyNull(final Object... args) {
        rejectIfNull(args);
        for (Object obj : args) {
            if (obj == null) {
                throw new IllegalArgumentException("Argument can't be null.");
            }
        }
    }

    /**
     * Throws an IllegalArgumentException if the given sequence is empty (null or "").
     *
     * @param arg The sequence to be checked.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfEmptyOrNull(final CharSequence arg) {
        if (CharSequenceUtils.isEmptyOrNull(arg)) {
            throw new IllegalArgumentException("Argument can't be empty.");
        }
    }

    /**
     * Throws an IllegalArgumentException if any sequence in the given array is empty (null or "").
     * <p/>
     * If the array itself is null, an IllegalArgumentException will be thrown too.
     *
     * @param args The array of sequences to be checked.
     *
     * @throws IllegalArgumentException If any of the the given arguments are rejected.
     */
    public static void rejectIfAnyEmptyOrNull(final CharSequence... args) {
        rejectIfNull(args);
        for (CharSequence seq : args) {
            rejectIfEmptyOrNull(seq);
        }
    }

    /**
     * Throws an IllegalArgumentException if the given sequence is blank (null, "" or has only "[\s]+" chars).
     *
     * @param arg The sequence to be checked.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfBlankOrNull(final CharSequence arg) {
        if (CharSequenceUtils.isBlankOrNull(arg)) {
            throw new IllegalArgumentException("Argument can't be blank.");
        }
    }

    /**
     * Throws an IllegalArgumentException if any sequence in the given array is blank (null, "" or has only "[\s]+" chars).
     * <p/>
     * If the array itself is null, an IllegalArgumentException will be thrown too.
     *
     * @param args The array of sequences to be checked.
     *
     * @throws IllegalArgumentException If any of the the given arguments are rejected.
     */
    public static void rejectIfAnyBlankOrNull(final CharSequence... args) {
        rejectIfNull(args);
        for (CharSequence seq : args) {
            rejectIfBlankOrNull(seq);
        }
    }

    /**
     * Throws an IllegalArgumentException if the given collection is empty (null or don't has any elements in it).
     *
     * @param arg The collection to be checked.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfEmptyOrNull(final Collection<?> arg) {
        if (CollectionUtils.isEmptyOrNull(arg)) {
            throw new IllegalArgumentException("Argument can't be empty.");
        }
    }

    /**
     * Throws an IllegalArgumentException if the given collection is empty (null or don't has any elements in it).
     *
     * @param arg The collection to be checked.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfEmptyOrNull(final Map<?, ?> arg) {
        if (CollectionUtils.isEmptyOrNull(arg)) {
            throw new IllegalArgumentException("Argument can't be empty.");
        }
    }

    /**
     * Throws an IllegalArgumentException if the given array is empty (null or its length is zero).
     *
     * @param arg The array to be checked.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfEmptyOrNull(final Object[] arg) {
        if (ArrayUtils.isEmptyOrNull(arg)) {
            throw new IllegalArgumentException("Argument can't be empty.");
        }
    }

    /**
     * Throws an IllegalArgumentException if the given sequence doesn't matches the given sequence.
     * <p/>
     * If the sequence or the pattern are null, IllegalArgumentException will be thrown too.
     *
     * @param arg     The sequence to be checked.
     * @param pattern The pattern witch the sequence must match.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfDontMatches(final CharSequence arg, final Pattern pattern) {
        rejectIfAnyNull(arg, pattern);
        if (!pattern.matcher(arg).matches()) {
            throw new IllegalArgumentException("Argument don't matches expected pattern.");
        }
    }

    /**
     * Throws an IllegalArgumentException if both objects are null or are equals to each other.
     *
     * @param arg0 The object to be checked.
     * @param arg1 The comparing object.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfEquals(final Object arg0, final Object arg1) {
        if ((arg0 == arg1) || ((arg0 != null) && arg0.equals(arg1)) || ((arg1 != null) && arg1.equals(arg0))) {
            throw new IllegalArgumentException("Argument must be " + String.valueOf(arg1) + ".");
        }
    }

    /**
     * Throws an IllegalArgumentException if the given objects aren't equals to each other.
     *
     * @param arg0 The object to be checked.
     * @param arg1 The comparing object.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfNotEquals(final Object arg0, final Object arg1) {
        if (((arg0 != null) && !arg0.equals(arg1)) || ((arg1 != null) && !arg1.equals(arg0))) {
            throw new IllegalArgumentException("Argument must be " + String.valueOf(arg1) + ".");
        }
    }

    /**
     * Throws an IllegalArgumentException if the given number is zero.
     *
     * @param arg The number to be checked.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfZero(final Number arg) {
        rejectIfNull(arg);
        if (arg.doubleValue() == 0) {
            throw new IllegalArgumentException("Argument can't be zero.");
        }
    }

    /**
     * Throws an IllegalArgumentException if the number's doubleValue() are equals to each other.
     * <p/>
     * If any of the given numbers are null, IllegalArgumentException will be thrown too.
     *
     * @param number    The number to be checked.
     * @param threshold The comparing number.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfEquals(final Number number, final Number threshold) {
        rejectIfAnyNull(number, threshold);
        if (number.doubleValue() == threshold.doubleValue()) {
            throw new IllegalArgumentException("Argument can't be == " + threshold + ".");
        }
    }

    /**
     * Throws an IllegalArgumentException if the number's doubleValue() aren't equals to each other.
     * <p/>
     * If any of the given numbers are null, IllegalArgumentException will be thrown too.
     *
     * @param number    The number to be checked.
     * @param threshold The comparing number.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfNotEquals(final Number number, final Number threshold) {
        rejectIfAnyNull(number, threshold);
        if (number.doubleValue() != threshold.doubleValue()) {
            throw new IllegalArgumentException("Argument can't be != " + threshold + ".");
        }
    }

    /**
     * Throws an IllegalArgumentException if the number's doubleValue() is less than the given threshold.
     * <p/>
     * If any of the given numbers are null, IllegalArgumentException will be thrown too.
     *
     * @param number    The number to be checked.
     * @param threshold The comparing number.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfLessThan(final Number number, final Number threshold) {
        rejectIfAnyNull(number, threshold);
        if (number.doubleValue() < threshold.doubleValue()) {
            throw new IllegalArgumentException("Argument can't be < " + threshold + ".");
        }
    }

    /**
     * Throws an IllegalArgumentException if the number's doubleValue() is less or equals than the given threshold.
     * <p/>
     * If any of the given numbers are null, IllegalArgumentException will be thrown too.
     *
     * @param number    The number to be checked.
     * @param threshold The comparing number.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfLessEquals(final Number number, final Number threshold) {
        rejectIfAnyNull(number, threshold);
        if (number.doubleValue() <= threshold.doubleValue()) {
            throw new IllegalArgumentException("Argument can't be <= " + threshold + ".");
        }
    }

    /**
     * Throws an IllegalArgumentException if the number's doubleValue() is greater than the given threshold.
     * <p/>
     * If any of the given numbers are null, IllegalArgumentException will be thrown too.
     *
     * @param number    The number to be checked.
     * @param threshold The comparing number.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfGreaterThan(final Number number, final Number threshold) {
        rejectIfAnyNull(number, threshold);
        if (number.doubleValue() > threshold.doubleValue()) {
            throw new IllegalArgumentException("Argument can't be > " + threshold + ".");
        }
    }

    /**
     * Throws an IllegalArgumentException if the number's doubleValue() is greater or equals than the given threshold.
     * <p/>
     * If any of the given numbers are null, IllegalArgumentException will be thrown too.
     *
     * @param number    The number to be checked.
     * @param threshold The comparing number.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfGreaterEqual(final Number number, final Number threshold) {
        rejectIfAnyNull(number, threshold);
        if (number.doubleValue() >= threshold.doubleValue()) {
            throw new IllegalArgumentException("Argument can't be >= " + threshold + ".");
        }
    }

    /**
     * Throws an IllegalArgumentException if the given number is out of given bounds.
     * <p/>
     * If any of the given numbers are null, or <tt>start</tt> is greater than <tt>end</tt>,
     * IllegalArgumentException will be thrown too.
     *
     * @param number The number to be checked.
     * @param start  The start number of the range.
     * @param end    The end number of the range.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfOutOfBounds(final Number number, final Number start, final Number end) {
        rejectIfGreaterThan(start, end); // start must be <= end
        rejectIfLessThan(number, start);
        rejectIfGreaterThan(number, end);
    }

    /**
     * Throws an IllegalArgumentException if any of the given classes are null or the first one
     * is not assignable from the second.
     *
     * @param leftSideClass  The left parameter of the {class}.isAssignableFrom({class}) statement.
     * @param rightSideClass The right parameter of the {class}.isAssignableFrom({class}) statement.
     *
     * @throws IllegalArgumentException If the given argument is rejected.
     */
    public static void rejectIfNotAssignableFrom(final Class<?> leftSideClass, final Class<?> rightSideClass) {
        rejectIfAnyNull(leftSideClass, rightSideClass);
        if (!leftSideClass.isAssignableFrom(rightSideClass)) {
            throw new IllegalArgumentException(leftSideClass.getName() + " is not assignable from " + rightSideClass.getName());
        }
    }

    /**
     * Throws an IllegalArgumentException if ALL objects in the given array are null.
     * <p/>
     * In other words, at least one must be not null.
     * <p/>
     * If the array itself is null, an IllegalArgumentException will be thrown too.
     *
     * @param args The array of objects to be checked.
     *
     * @throws IllegalArgumentException If all of the the given arguments are null.
     */
    public static void rejectIfAllNull(final Object... args) {
        rejectIfNull(args);
        boolean notNullFound = false;
        for (Object obj : args) {
            if (obj != null) {
                notNullFound = true;
                break;
            }
        }
        if (!notNullFound) {
            throw new IllegalArgumentException("At least one argument must be not null.");
        }
    }
}
