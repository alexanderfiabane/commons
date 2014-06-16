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
import br.msf.commons.util.ObjectUtils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Operations on arrays.
 * <p/>
 * This class tries to handle null input gracefully. An exception will not be thrown for a null array input.
 * However, an Object array that contains a null element may throw an exception. Each method documents its behaviour.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 * @see org.apache.commons.lang.ArrayUtils
 */
public abstract class ArrayUtils {

    /**
     * Returns the length of the given array.
     *
     * @param array The array to be evaluated.
     * @return The array length.
     */
    public static int getSize(final Object array) {
        return ObjectUtils.isArray(array) ? Array.getLength(array) : 0;
    }

    /**
     * Returns the combined length of the given arrays.
     *
     * @param arrays The arrays to be evaluated.
     * @return The sum of all arrays lengths.
     */
    public static int getTotalSize(final Object... arrays) {
        if (getSize(arrays) == 0) {
            return 0;
        }
        int count = 0;
        if (isNotEmpty(arrays)) {
            for (final Object array : arrays) {
                count += getSize(array);
            }
        }
        return count;
    }

    public static boolean isArray(final Object value) {
        return ObjectUtils.isArray(value);
    }

    public static boolean isArray(final Object value, final boolean acceptNull) {
        return ObjectUtils.isArray(value, acceptNull);
    }

    public static boolean isPrimitiveArray(final Object value) {
        return ObjectUtils.isPrimitiveArray(value);
    }

    public static boolean isPrimitiveArray(final Object value, final boolean acceptNull) {
        return ObjectUtils.isPrimitiveArray(value, acceptNull);
    }

    /**
     * Indicates whether the given object is a non-empty array.
     *
     * @param array The array to test.
     * @return {@code true} if the array is not null and not empty;
     */
    public static boolean isNotEmpty(final Object array) {
        return ObjectUtils.isArray(array) && Array.getLength(array) > 0;
    }

    /**
     * Tells if a given array is null or empty.
     *
     * @param array The array to be evaluated.
     * @return {@code true} if the given array is null or empty.
     */
    public static boolean isEmptyOrNull(final Object array) {
        return array == null || (ObjectUtils.isArray(array, false) && getSize(array) == 0);
    }

    /**
     * Tells if a given array is null or empty, or has just one element.
     *
     * @param array The array to be evaluated.
     * @return {@code true} if the given array has just one element or none at all.
     */
    public static boolean isEmptyOrSingleton(final Object array) {
        return getSize(array) <= 1;
    }

    public static <T extends Object> T getSingleton(final T[] array) {
        if (isEmptyOrNull(array)) {
            return null;
        } else if (array.length > 1) {
            throw new IllegalStateException("Collection has more than one element in it.");
        } else {
            return array[0];
        }
    }

    /**
     * Merge the contents of all given arrays in a single, new one.
     * <p/>
     * Null elements are ignored.
     *
     * @param <T>       Type of array elements.
     * @param itemClass Class of the elements for the resulting array.
     * @param arrays    The arrays to be merged.
     * @return The generated array containing all non-null elements, or null if no array is given.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T[] merge(final Class<T> itemClass, final Object... arrays) {
        return merge(itemClass, true, arrays);
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static <T extends Object> T[] merge(final Class<T> itemClass, final boolean ignoreNulls, final Object... arrays) {
        if (getSize(arrays) == 0) {
            return null;
        }
        final T[] ret = (T[]) Array.newInstance(itemClass, getTotalSize(arrays));
        int pos = 0;
        for (Object array : arrays) {
            final int len = getSize(array);
            if (len > 0) {
                if (ignoreNulls) {
                    /* trata manualmente cada posição, ignorando os nulls */
                    for (int i = 0; i < len; i++) {
                        T item = (T) Array.get(array, i);
                        if (item != null) {
                            ret[pos] = item;
                            pos++;
                        }
                    }
                } else {
                    /* faz a cópia nativa */
                    System.arraycopy(array, 0, ret, pos, len);
                    pos += len;
                }
            }
        }
        return ret;
    }

    /**
     * Returns the dimension count of the given array.
     *
     * @param array The array to be evaluated.
     * @return The dimension count of the given array.
     */
    public static int getDimension(final Object array) {
        if (array == null) {
            return 0;
        }
        int dim = 0;
        Class<?> c = array.getClass();
        while (c.isArray()) {
            c = c.getComponentType();
            dim++;
        }
        return dim;
    }

    /**
     * Returns an empty array (non-null) if the given one was null, else, return the given array itself.
     *
     * @param <T>       Type of array elements.
     * @param itemClass Class of the elements for the resulting array.
     * @param array     The array to be evaluated.
     * @return An empty array (non-null) if the given one was null, or the given array itself otherwise.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T[] getNullSafeArray(final Class<T> itemClass, final T[] array) {
        return array != null ? array : (T[]) Array.newInstance(itemClass, getTotalSize(array));
    }

    public static String toString(final Object array, final String separator) {
        return toString(array, separator, separator);
    }

    public static String toString(final Object array, final String separator, final String lastSeparator) {
        return toString(array, null, separator, lastSeparator);
    }

    public static String toString(final Object array, final String descriptionPath, final String separator, final String lastSeparator) {
        if (separator == null || lastSeparator == null) {
            throw new IllegalArgumentException("Argumento nulo.");
        }
        if (isEmptyOrNull(array)) {
            return "";
        }
        final EnhancedStringBuilder builder = new EnhancedStringBuilder();
        final int size = getSize(array);
        for (int i = 0; i < size; i++) {
            Object toAdd = Array.get(array, i);
            if (CharSequenceUtils.isNotEmpty(descriptionPath)) {
                toAdd = ReflectionUtils.invokeCascadeGetterFor(toAdd, descriptionPath);
            }
            builder.append(toAdd).
                    appendIfTrue((i < (size - 2)), separator).
                    appendIfTrue((i == (size - 2)), lastSeparator);
        }
        return builder.toString();
    }

    public static <T extends Object> List<T> asNullSafeList(final T... objects) {
        if (ArrayUtils.isEmptyOrNull(objects)) {
            return CollectionUtils.EMPTY_LIST;
        }
        final ArrayList<T> wrapper = new ArrayList<T>(objects.length);
        for (T item : objects) {
            if (item != null) {
                wrapper.add(item);
            }
        }
        return wrapper;
    }
}
