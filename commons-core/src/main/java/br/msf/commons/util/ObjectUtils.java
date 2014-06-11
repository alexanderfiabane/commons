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

import br.msf.commons.text.EnhancedStringBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;

/**
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public abstract class ObjectUtils {

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link Class}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Class}. {@code false} otherwise.
     */
    public static boolean isClass(final Object value) {
        return isClass(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Class}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Class}. {@code false} otherwise.
     */
    public static boolean isClass(final Object value, final boolean acceptNull) {
        return isType(Class.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link Boolean}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Boolean}. {@code false} otherwise.
     */
    public static boolean isBoolean(final Object value) {
        return isBoolean(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Boolean}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Boolean}. {@code false} otherwise.
     */
    public static boolean isBoolean(final Object value, final boolean acceptNull) {
        return isType(Boolean.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link Date}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Date}. {@code false} otherwise.
     */
    public static boolean isDate(final Object value) {
        return isDate(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Date}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Date}. {@code false} otherwise.
     */
    public static boolean isDate(final Object value, final boolean acceptNull) {
        return isType(Date.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link Calendar}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Calendar}. {@code false} otherwise.
     */
    public static boolean isCalendar(final Object value) {
        return isCalendar(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Calendar}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Calendar}. {@code false} otherwise.
     */
    public static boolean isCalendar(final Object value, final boolean acceptNull) {
        return isType(Calendar.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link Number}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Number}. {@code false} otherwise.
     */
    public static boolean isNumber(final Object value) {
        return isNumber(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Number}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Number}. {@code false} otherwise.
     */
    public static boolean isNumber(final Object value, final boolean acceptNull) {
        return isType(Number.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of <b>{@link Byte}</b>.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Byte}. {@code false} otherwise.
     */
    public static boolean isByte(final Object value) {
        return isByte(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Byte}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Byte}. {@code false} otherwise.
     */
    public static boolean isByte(final Object value, final boolean acceptNull) {
        return isType(Byte.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of <b>{@link Short}</b>.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Short}. {@code false} otherwise.
     */
    public static boolean isShort(final Object value) {
        return isShort(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Short}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Short}. {@code false} otherwise.
     */
    public static boolean isShort(final Object value, final boolean acceptNull) {
        return isType(Short.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of <b>{@link Long}</b>.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Long}. {@code false} otherwise.
     */
    public static boolean isLong(final Object value) {
        return isLong(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Long}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Long}. {@code false} otherwise.
     */
    public static boolean isLong(final Object value, final boolean acceptNull) {
        return isType(Long.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of <b>{@link Integer}</b>.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Integer}. {@code false} otherwise.
     */
    public static boolean isInteger(final Object value) {
        return isInteger(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Integer}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Integer}. {@code false} otherwise.
     */
    public static boolean isInteger(final Object value, final boolean acceptNull) {
        return isType(Integer.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of <b>{@link BigInteger}</b>.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link BigInteger}. {@code false} otherwise.
     */
    public static boolean isBigInteger(final Object value) {
        return isBigInteger(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link BigInteger}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link BigInteger}. {@code false} otherwise.
     */
    public static boolean isBigInteger(final Object value, final boolean acceptNull) {
        return isType(BigInteger.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of <b>{@link Float}</b>.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Float}. {@code false} otherwise.
     */
    public static boolean isFloat(final Object value) {
        return isFloat(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Float}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Float}. {@code false} otherwise.
     */
    public static boolean isFloat(final Object value, final boolean acceptNull) {
        return isType(Float.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of <b>{@link Double}</b>.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Double}. {@code false} otherwise.
     */
    public static boolean isDouble(final Object value) {
        return isDouble(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Double}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Double}. {@code false} otherwise.
     */
    public static boolean isDouble(final Object value, final boolean acceptNull) {
        return isType(Double.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of <b>{@link BigDecimal}</b>.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link BigDecimal}. {@code false} otherwise.
     */
    public static boolean isBigDecimal(final Object value) {
        return isBigDecimal(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link BigDecimal}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link BigDecimal}. {@code false} otherwise.
     */
    public static boolean isBigDecimal(final Object value, final boolean acceptNull) {
        return isType(BigDecimal.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link Character}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Character}. {@code false} otherwise.
     */
    public static boolean isCharacter(final Object value) {
        return isCharacter(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Character}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Character}. {@code false} otherwise.
     */
    public static boolean isCharacter(final Object value, final boolean acceptNull) {
        return isType(Character.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link CharSequence}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link CharSequence}. {@code false} otherwise.
     */
    public static boolean isCharSequence(final Object value) {
        return isCharSequence(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link CharSequence}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link CharSequence}. {@code false} otherwise.
     */
    public static boolean isCharSequence(final Object value, final boolean acceptNull) {
        return isType(CharSequence.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link String}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link String}. {@code false} otherwise.
     */
    public static boolean isString(final Object value) {
        return isString(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link String}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link String}. {@code false} otherwise.
     */
    public static boolean isString(final Object value, final boolean acceptNull) {
        return isType(String.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link StringBuilder}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link StringBuilder}. {@code false} otherwise.
     */
    public static boolean isStringBuilder(final Object value) {
        return isStringBuilder(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link StringBuilder}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link StringBuilder}. {@code false} otherwise.
     */
    public static boolean isStringBuilder(final Object value, final boolean acceptNull) {
        return isType(StringBuilder.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link StringBuffer}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link StringBuffer}. {@code false} otherwise.
     */
    public static boolean isStringBuffer(final Object value) {
        return isStringBuffer(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link StringBuffer}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link StringBuffer}. {@code false} otherwise.
     */
    public static boolean isStringBuffer(final Object value, final boolean acceptNull) {
        return isType(StringBuffer.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link EnhancedStringBuilder}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link EnhancedStringBuilder}. {@code false} otherwise.
     */
    public static boolean isEnhancedStringBuilder(final Object value) {
        return isEnhancedStringBuilder(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link EnhancedStringBuilder}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link EnhancedStringBuilder}. {@code false} otherwise.
     */
    public static boolean isEnhancedStringBuilder(final Object value, final boolean acceptNull) {
        return isType(EnhancedStringBuilder.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link Collection}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Collection}. {@code false} otherwise.
     */
    public static boolean isCollection(final Object value) {
        return isCollection(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Collection}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Collection}. {@code false} otherwise.
     */
    public static boolean isCollection(final Object value, final boolean acceptNull) {
        return isType(Collection.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link List}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link List}. {@code false} otherwise.
     */
    public static boolean isList(final Object value) {
        return isList(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link List}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link List}. {@code false} otherwise.
     */
    public static boolean isList(final Object value, final boolean acceptNull) {
        return isType(List.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link Set}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Set}. {@code false} otherwise.
     */
    public static boolean isSet(final Object value) {
        return isSet(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Set}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Set}. {@code false} otherwise.
     */
    public static boolean isSet(final Object value, final boolean acceptNull) {
        return isType(Set.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link SortedSet}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link SortedSet}. {@code false} otherwise.
     */
    public static boolean isSortedSet(final Object value) {
        return isSortedSet(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link SortedSet}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link SortedSet}. {@code false} otherwise.
     */
    public static boolean isSortedSet(final Object value, final boolean acceptNull) {
        return isType(SortedSet.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link Queue}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Queue}. {@code false} otherwise.
     */
    public static boolean isQueue(final Object value) {
        return isQueue(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Queue}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Queue}. {@code false} otherwise.
     */
    public static boolean isQueue(final Object value, final boolean acceptNull) {
        return isType(Queue.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link Map}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Map}. {@code false} otherwise.
     */
    public static boolean isMap(final Object value) {
        return isMap(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Map}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Map}. {@code false} otherwise.
     */
    public static boolean isMap(final Object value, final boolean acceptNull) {
        return isType(Map.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of {@link Properties}.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of {@link Properties}. {@code false} otherwise.
     */
    public static boolean isProperties(final Object value) {
        return isProperties(value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of {@link Properties}.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of {@link Properties}. {@code false} otherwise.
     */
    public static boolean isProperties(final Object value, final boolean acceptNull) {
        return isType(Properties.class, value, acceptNull);
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> array.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null array. {@code false} otherwise.
     */
    public static boolean isArray(final Object value) {
        return isArray(value, false);
    }

    /**
     * Returns {@code true} if the given object is an array.
     *
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an array. {@code false} otherwise.
     */
    public static boolean isArray(final Object value, final boolean acceptNull) {
        return (value == null && acceptNull) || (value != null && value.getClass().isArray());
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> array of primitives.
     *
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null array of primitives. {@code false} otherwise.
     */
    public static boolean isPrimitiveArray(final Object value) {
        return isPrimitiveArray(value, false);
    }

    /**
     * Indicates whether the given object is an array of primitives or not.
     *
     * @param value      The object to be evaluated.
     * @param acceptNull Indicates if null is to be considered a valid array Object.
     * @return {@code true} if the given object is an array of primitives. {@code false} otherwise.
     */
    public static boolean isPrimitiveArray(final Object value, final boolean acceptNull) {
        return (value == null && acceptNull) || (value != null && value.getClass().isArray() && value.getClass().getComponentType().isPrimitive());
    }

    /**
     * Returns {@code true} if the given object is a <b>non-null</b> instance of the given type.
     *
     * @param type  The type to be tested against the object.
     * @param value The object to be evaluated.
     * @return {@code true} if the given object is a non-null instance of the given type. {@code false} otherwise.
     * @throws IllegalArgumentException If the given type is invalid or null.
     */
    public static boolean isType(final Class<?> type, final Object value) {
        return isType(type, value, false);
    }

    /**
     * Returns {@code true} if the given object is an instance of the given type.
     *
     * @param type  The type to be tested against the object. Cannot be null.
     * @param value The object to be evaluated.
     * @param acceptNull Indicates if this method may return {@code true} in case of {@code null} values.
     * @return {@code true} if the given object is an instance of the given type. {@code false} otherwise.
     * @throws IllegalArgumentException If the given type is invalid or null.
     */
    public static boolean isType(final Class<?> type, final Object value, final boolean acceptNull) {
        if (type == null) {
            throw new IllegalArgumentException("The type cannot be null.");
        }
        return (value == null && acceptNull) || (value != null && type.isAssignableFrom(value.getClass()));
    }
}
