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

import br.ojimarcius.commons.text.CharSequenceComparator;
import br.ojimarcius.commons.text.EnhancedStringBuilder;
import br.ojimarcius.commons.util.ArgumentUtils;
import br.ojimarcius.commons.util.ObjectUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Various Collection API utilities.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public abstract class CollectionUtils {

    protected static final Logger LOGGER = Logger.getLogger(CollectionUtils.class.getName());
    public static final Set EMPTY_SET = Collections.EMPTY_SET;
    public static final List EMPTY_LIST = Collections.EMPTY_LIST;
    public static final Map EMPTY_MAP = Collections.EMPTY_MAP;

    /**
     * Returns the size of the given collection, null safely.
     * <p/>
     * If the given collection is empty or null, returns zero.
     *
     * @param collections The collections to evaluate.
     * @return {@code true} if the given map is null or empty.
     */
    public static int size(final Collection... collections) {
        int total = 0;
        if (ArrayUtils.isNotEmpty(collections)) {
            for (final Collection<?> c : collections) {
                total += (isEmptyOrNull(c)) ? 0 : c.size();
            }
        }
        return total;
    }

    /**
     * Returns the size of the given collection, null safely.
     * <p/>
     * If the given map(s) is/are empty or null, returns zero.
     *
     * @param maps The maps to evaluate.
     * @return {@code true} if the given map is null or empty.
     */
    public static int size(final Map... maps) {
        int total = 0;
        if (ArrayUtils.isNotEmpty(maps)) {
            for (final Map<?,?> m : maps) {
                total += (isEmptyOrNull(m)) ? 0 : m.size();
            }
        }
        return total;
    }
    
    /**
     * Tells if a given collection is null or empty.
     *
     * @param collection The collection to be evaluated.
     * @return {@code true} if the given collection is null or empty.
     */
    public static boolean isEmptyOrNull(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    
    /**
     * Tells if a given collection is not null and has something.
     *
     * @param collection The collection to be evaluated.
     * @return {@code true} if the given map is not null and has something.
     */
    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmptyOrNull(collection);
    }
    
    /**
     * Tells if a given map is null or empty.
     *
     * @param map The map to be evaluated.
     * @return {@code true} if the given map is null or empty.
     */
    public static boolean isEmptyOrNull(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Tells if a given map is not null and has something.
     *
     * @param map The map to be evaluated.
     * @return {@code true} if the given map is not null and has something.
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmptyOrNull(map);
    }

    /**
     * Tells if a given collection has EXACTLY one element.
     *
     * @param collection The collection to be evaluated.
     * @return {@code true} if the given collection has EXACTLY one element.
     */
    public static boolean isSingleton(final Collection<?> collection) {
        return size(collection) == 1;
    }

    /**
     * Tells if a given collection is null or empty, or has one element.
     *
     * @param collection The collection to be evaluated.
     * @return {@code true} if the given collection has one element or none at all.
     */
    public static boolean isEmptyOrSingleton(final Collection<?> collection) {
        return isEmptyOrNull(collection) || isSingleton(collection);
    }

    /**
     * Tells if a given map has EXACTLY one entry.
     *
     * @param map The collection to be evaluated.
     * @return {@code true} if the given map has EXACTLY one entry.
     */
    public static boolean isSingleton(final Map<?, ?> map) {
        return size(map) == 1;
    }

    /**
     * Tells if a given map is null or empty, or has just one entry.
     *
     * @param map The collection to be evaluated.
     * @return {@code true} if the given map has just one entry or none at all.
     */
    public static boolean isEmptyOrSingleton(final Map<?, ?> map) {
        return isEmptyOrNull(map) || isSingleton(map);
    }

    /**
     * Asks an iterator if there is next value.
     *
     * @param it The iterator to analise.
     * @return true if the given iterator is not null and has next element. false otherwise.
     */
    public static boolean hasNext(final Iterator<?> it) {
        return it != null && it.hasNext();
    }

    public static <T> T get(final Iterable<T> iterable, final int index) {
        ArgumentUtils.rejectIfLessThan(index, 0);
        if (iterable instanceof List<?>) {
            return ((List<T>) iterable).get(index);
        }
        return get(iterable.iterator(), index);
    }
    
    /**
     * Returns the <code>index</code>-th value in {@link Iterator}, throwing
     * <code>IndexOutOfBoundsException</code> if there is no such element.
     * <p>
     * The Iterator is advanced to <code>index</code> (or to the end, if
     * <code>index</code> exceeds the number of entries) as a side effect of this method.
     *
     * @param iterator  the iterator to get a value from
     * @param index  the index to get
     * @param <T> the type of object in the {@link Iterator}
     * @return the object at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     * @throws IllegalArgumentException if the object type is invalid
     */
    public static <T> T get(final Iterator<T> iterator, final int index) {
        int i = index;
        ArgumentUtils.rejectIfLessThan(index, 0);
        while (iterator.hasNext()) {
            i--;
            if (i == -1) {
                return iterator.next();
            }
            iterator.next();
        }
        throw new IndexOutOfBoundsException("Entry does not exist: " + i);
    }
    
    public static String toString(final Collection<? extends Object> collection, final String separator) {
        return toString(collection, separator, separator);
    }

    public static String toString(final Collection<? extends Object> collection, final String separator, final String lastSeparator) {
        return toString(collection, null, separator, lastSeparator);
    }

    public static String toString(final Collection<? extends Object> collection,
                                  final String descriptionPath, final String separator, final String lastSeparator) {
        if (separator == null || lastSeparator == null) {
            throw new IllegalArgumentException("Argumento nulo.");
        }
        if (isEmptyOrNull(collection)) {
            return "";
        }
        final EnhancedStringBuilder builder = new EnhancedStringBuilder();
        int i = 0;

        for (final Object object : collection) {
            Object toAdd = object;
            if (CharSequenceUtils.isNotEmpty(descriptionPath)) {
                toAdd = ReflectionUtils.invokeCascadeGetterFor(object, descriptionPath);
            }
            builder.append(toAdd).
                    appendIfTrue((i < (collection.size() - 2)), separator).
                    appendIfTrue((i == (collection.size() - 2)), lastSeparator);
            i++;
        }
        return builder.toString();
    }

    /**
     * Casts the given collection to List, if possible.
     * <p/>
     * If the given collection is not a List, a new one (ArrayList) is created
     * with the given collection contents.
     *
     * @param <T>        The type of objects on the list.
     * @param collection The collection to convert.
     * @return The contents of the given collection as a list.
     */
    public static <T extends Object> List<T> asList(final Collection<T> collection) {
        return (ObjectUtils.isList(collection, true)) ? (List<T>) collection : new ArrayList<T>(collection);
    }

    /**
     * Casts the given collection to Set, if possible.
     * <p/>
     * If the given collection is not a Set, a new one (LinkedHashSet) is
     * created with the given collection contents.
     *
     * @param <T>        The type of objects on the list.
     * @param collection The collection to convert.
     * @return The contents of the given collection as a Set.
     */
    public static <T extends Object> Set<T> asSet(final Collection<T> collection) {
        return (ObjectUtils.isSet(collection, true)) ? (Set<T>) collection : new LinkedHashSet<T>(collection);
    }

    /**
     * Indicates if the given collection contains the given sequence.
     * <p/>
     * If the given sequence is null, it will return true only if the collection
     * has a null element in it.
     * <p/>
     * Null collections are assumed as empty collections.
     *
     *
     * @param collection    The collection containing all the sequences.
     * @param sequence      The sequence you are looking for.
     * @param caseSensitive Indicates if the search must be case
     *                      sensitive/insensitive.
     * @return {@code true} if the given collection contains the sequence you
     *         are looking for.
     * {@code false} otherwise.
     */
    public static boolean contains(final Collection<? extends CharSequence> collection, final CharSequence sequence, final Boolean caseSensitive) {
        return indexOf(asList(collection), sequence, 0, caseSensitive) >= 0;
    }

    public static <T extends Object> T getFirst(final Collection<T> collection) {
        if (isEmptyOrNull(collection)) {
            return null;
        } else {
            return (T) get(collection, 0);
        }
    }

    public static <T extends Object> T getLast(final Collection<T> collection) {
        if (isEmptyOrNull(collection)) {
            return null;
        } else {
            return (T) get(collection, indexOfLast(collection));
        }
    }

    public static <T extends Object> T getSingleton(final Collection<T> collection) {
        if (isEmptyOrNull(collection)) {
            return null;
        } else if (collection.size() > 1) {
            throw new IllegalStateException("Collection has more than one element in it.");
        } else {
            return (T) collection.iterator().next();
        }
    }

    public static <T extends Object> T remove(final int index, final Collection<T> collection) {
        if (!isEmptyOrNull(collection)) {
            int i = 0;
            for (Iterator<T> it = collection.iterator(); it.hasNext();) {
                final T removed = it.next();
                if (i == index) {
                    it.remove();
                    return removed;
                }
                i++;
            }
        }
        return null;
    }

    public static <T extends Object> T removeLast(final Collection<T> collection) {
        return remove(indexOfLast(collection), collection);
    }

    public static <T extends Object> T removeFirst(final Collection<T> collection) {
        return remove(0, collection);
    }

    public static int indexOfLast(final Collection collection) {
        return (isNotEmpty(collection)) ? (collection.size() - 1) : -1;
    }

    /**
     * Returns the index of the first occurrence of the given sequence on the
     * given collection.
     * <p/>
     * If the given sequence is null, it will return the index of the first null
     * element of the collection.
     * <p/>
     * Null collections are assumed as empty collections.
     * <p/>
     * If the given sequence is not found, <tt>-1</tt> will be returned.
     *
     * @param collection    The collection containing all the sequences.
     * @param sequence      The sequence you are looking for.
     * @param caseSensitive Indicates if the search must be case
     *                      sensitive/insensitive.
     * @return The index of the first occurrence of the given sequence on the
     *         given collection, starting from the given <tt>fromIndex</tt>, or
     * <tt>-1</tt> if none.
     */
    public static int indexOf(final List<? extends CharSequence> collection, final CharSequence sequence, final boolean caseSensitive) {
        return indexOf(collection, sequence, 0, caseSensitive);
    }

    /**
     * Returns the index of the first occurrence of the given sequence on the
     * given collection, starting from the given <tt>fromIndex</tt>.
     * <p/>
     * If the given sequence is null, it will return the index of the first null
     * element of the collection, starting from the given <tt>fromIndex</tt>.
     * <p/>
     * Null collections are assumed as empty collections.
     * <p/>
     * If the given sequence is not found, <tt>-1</tt> will be returned.
     *
     * @param collection    The collection containing all the sequences.
     * @param sequence      The sequence you are looking for.
     * @param fromIndex     The index to start the search from.
     * @param caseSensitive Indicates if the search must be case
     *                      sensitive/insensitive.
     * @return The index of the first occurrence of the given sequence on the
     *         given collection, starting from the given <tt>fromIndex</tt>, or
     * <tt>-1</tt> if none.
     */
    public static int indexOf(final List<? extends CharSequence> collection, final CharSequence sequence, final int fromIndex, final boolean caseSensitive) {
        if (CollectionUtils.isEmptyOrNull(collection) || fromIndex >= collection.size()) {
            return -1;
        }
        int from = (fromIndex >= 0) ? fromIndex : 0;
        final CharSequenceComparator comparator = new CharSequenceComparator(caseSensitive, false);
        for (int i = from; i < collection.size(); i++) {
            CharSequence currWord = collection.get(i);
            if (sequence == currWord || (sequence != null && currWord != null && comparator.compare(sequence, currWord) == 0)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index within the given collection of the last occurrence of
     * the specified sequence.
     * <p/>
     * If the given sequence is null, it will return the index of the first null
     * element of the collection.
     * <p/>
     * Null collections are assumed as empty collections.
     * <p/>
     * If the given sequence is not found, <tt>-1</tt> will be returned.
     *
     * @param collection    The collection containing all the sequences.
     * @param sequence      The sequence you are looking for.
     * @param caseSensitive Indicates if the search must be case
     *                      sensitive/insensitive.
     * @return The index of the first occurrence of the given sequence on the
     *         given collection, starting from the given <tt>fromIndex</tt>, or
     * <tt>-1</tt> if none.
     */
    public static int lastIndexOf(final List<? extends CharSequence> collection, final CharSequence sequence, final boolean caseSensitive) {
        return lastIndexOf(collection, sequence, size(collection), caseSensitive);
    }

    /**
     * Returns the index within the given collection of the last occurrence of
     * the specified sequence, <b>searching backward</b> starting at the
     * specified index.
     * <p/>
     * If the given sequence is null, it will return the index of the first null
     * element of the collection, starting from the given <tt>fromIndex</tt>.
     * <p/>
     * Null collections are assumed as empty collections.
     * <p/>
     * If the given sequence is not found, <tt>-1</tt> will be returned.
     *
     * @param collection    The collection containing all the sequences.
     * @param sequence      The sequence you are looking for.
     * @param fromIndex     The index to start the search from.
     * @param caseSensitive Indicates if the search must be case
     *                      sensitive/insensitive.
     * @return The index of the first occurrence of the given sequence on the
     *         given collection, starting from the given <tt>fromIndex</tt>, or
     * <tt>-1</tt> if none.
     */
    public static int lastIndexOf(final List<? extends CharSequence> collection,
                                  final CharSequence sequence, final int fromIndex, final boolean caseSensitive) {
        if (CollectionUtils.isEmptyOrNull(collection) || fromIndex < 0) {
            return -1;
        }
        int from = (fromIndex < collection.size()) ? fromIndex : indexOfLast(collection);
        final CharSequenceComparator comparator = new CharSequenceComparator(caseSensitive, false);
        for (int i = from; i >= 0; i--) {
            CharSequence currWord = collection.get(i);
            if (sequence != currWord && (sequence == null || currWord == null)) {
            } else if (sequence == currWord || comparator.compare(sequence, currWord) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static <T extends Object> Collection<T> projection(final Collection<?> targetCollection, final String attributePath) {
        return projection(targetCollection, attributePath, true);
    }

    public static <T extends Object> Collection<T> projection(final Collection<?> targetCollection,
                                                              final String attributePath, final boolean ignoreNullValues) {
        if (CollectionUtils.isEmptyOrNull(targetCollection)) {
            return EMPTY_LIST;
        }
        final Collection<T> projection = new ArrayList<T>(targetCollection.size());
        for (Object element : targetCollection) {
            final Object toAdd = ReflectionUtils.invokeCascadeGetterFor(element, attributePath);
            if (toAdd != null || !ignoreNullValues) {
                projection.add((T) toAdd);
            }
        }
        return projection;
    }

    public static <T extends Object> Collection<T> merge(final Collection<T>... collections) {
        final int size = size(collections);
        if (size <= 0) {
            return EMPTY_LIST;
        }
        final Collection<T> all = new ArrayList<T>(size);
        for (final Collection<T> c : collections) {
            if (isNotEmpty(c)) {
                all.addAll(c);
            }
        }
        return all;
    }

    public static <T extends Object> Collection<T> getNullSafeCollection(final Collection<T> collection) {
        return collection != null ? collection : EMPTY_LIST;
    }

    public static <T extends Object> List<T> getNullSafeList(final List<T> collection) {
        return collection != null ? collection : EMPTY_LIST;
    }

    public static <T extends Object> Set<T> getNullSafeSet(final Set<T> collection) {
        return collection != null ? collection : EMPTY_SET;
    }

    public static void clear(final Collection<?> collection) {
        if (isNotEmpty(collection)) {
            collection.clear();
        }
    }

    public static void clear(final Map<?, ?> map) {
        if (isNotEmpty(map)) {
            map.clear();
        }
    }

    public static <T extends Object> Collection<T> getCopy(final Collection<T> collection) {
        Collection<T> clone = null;
        if (collection != null) {
            clone = getCompatibleInstance(collection);
            clone.addAll(collection);
        }
        return clone;
    }

    public static <T extends Object> Collection<T> getCompatibleInstance(final Collection<T> collection) {
        Collection<T> instance = null;
        if (collection != null) {
            try {
                // try to use the same type of collection of the source
                instance = collection.getClass().newInstance();
            } catch (Exception ex) {
                /**
                 * Could not instantiate via reflection. probably is a
                 * Unmodifiable Collection (not meant to be instantiated
                 * directly) Fallback to common implementations...
                 */
                if (ObjectUtils.isSortedSet(collection)) {
                    instance = new TreeSet<T>();
                } else if (ObjectUtils.isSet(collection)) {
                    instance = new LinkedHashSet<T>(collection.size());
                } else if (ObjectUtils.isQueue(collection)) {
                    instance = new LinkedList<T>(); // implements List, Queue and Deque (deque is a queue)
                } else {
                    instance = new ArrayList<T>(collection.size());
                }
            }
        }
        return instance;
    }

// "Proxy" for java.util.Collections methods. ////////////////////////////////////////////////////////////////////////////////////

    public static <T extends Comparable<? super T>> void sort(final List<T> list) {
        Collections.sort(list);
    }

    public static <T extends Object> void sort(final List<T> list, final Comparator<? super T> comparator) {
        Collections.sort(list, comparator);
    }

    public static <T extends Object> int binarySearch(final List<? extends Comparable<? super T>> list, final T element) {
        return Collections.binarySearch(list, element);
    }

    public static <T extends Object> int binarySearch(final List<? extends T> list, final T element, final Comparator<? super T> comparator) {
        return Collections.binarySearch(list, element, comparator);
    }

    public static void reverse(final List<?> list) {
        Collections.reverse(list);
    }

    public static void shuffle(final List<?> list) {
        Collections.shuffle(list);
    }

    public static void shuffle(final List<?> list, final Random random) {
        Collections.shuffle(list, random);
    }

    public static void swap(final List<?> list, final int index0, final int index1) {
        Collections.swap(list, index0, index1);
    }

    public static <T extends Object> void fill(final List<? super T> list, final T element) {
        Collections.fill(list, element);
    }

    public static <T extends Object> void copy(final List<? super T> list0, final List<? extends T> list1) {
        Collections.copy(list0, list1);
    }

    public static <T extends Object & Comparable<? super T>> T min(final Collection<? extends T> clctn) {
        return Collections.min(clctn);
    }

    public static <T extends Object> T min(final Collection<? extends T> collection, final Comparator<? super T> comparator) {
        return Collections.min(collection, comparator);
    }

    public static <T extends Object & Comparable<? super T>> T max(final Collection<? extends T> collection) {
        return Collections.max(collection);
    }

    public static <T extends Object> T max(final Collection<? extends T> collection, final Comparator<? super T> comparator) {
        return Collections.max(collection, comparator);
    }

    public static <T extends Object> boolean replaceAll(final List<T> list, final T element0, final T element1) {
        return Collections.replaceAll(list, element0, element1);
    }

    public static int indexOfSubList(final List<?> list0, final List<?> list1) {
        return Collections.indexOfSubList(list0, list1);
    }

    public static int lastIndexOfSubList(final List<?> list0, final List<?> list1) {
        return Collections.lastIndexOfSubList(list0, list1);
    }

    public static <K extends Object, V extends Object> Map<K, V> unmodifiableMap(final Map<? extends K, ? extends V> map) {
        return Collections.unmodifiableMap(map);
    }

    public static <K extends Object, V extends Object> Map<K, V> synchronizedMap(final Map<K, V> map) {
        return Collections.synchronizedMap(map);
    }

    public static <T extends Object> List<T> asList(final T... ts) {
        return Arrays.asList(ts);
    }

    public static <K extends Object, V extends Object> Map<K, V> asMap(final Object[][] params) {
        return asMap(true, params);
    }

    public static <K extends Object, V extends Object> Map<K, V> asMap(final boolean ignoreNullValues, final Object[][] params) {
        if (params == null) {
            return null;
        } else if (ArrayUtils.isEmptyOrNull(params)) {
            return CollectionUtils.EMPTY_MAP;
        }
        final Map<K, V> map = new LinkedHashMap<K, V>(params.length);
        for (final Object[] entry : params) {
            if (entry == null || entry.length != 2) {
                throw new IllegalArgumentException("The 'params' array may contain non-null two-element arrays (entries) only.");
            }
            if (!ignoreNullValues || entry[1] != null) {
                map.put((K) entry[0], (V) entry[1]);
            }
        }
        return map;
    }

    public static String resolveKey(final String key, final Map<String, String> properties) {
        if (isNotEmpty(properties)) {
            return resolveValue(properties.get(key), properties);
        }
        return null;
    }

    public static String resolveValue(final CharSequence value, final Map<String, String> properties) {
        if (value == null) {
            return null;
        }
        final EnhancedStringBuilder builder = new EnhancedStringBuilder(value);
        if (CharSequenceUtils.isNotBlank(value)) {
            // create the properties map to use in the crossreference resolution.
            Map<String, String> supportedProps = new LinkedHashMap<String, String>();
            // adds system props
            supportedProps.putAll(getSystemProps());
            // adds convenience props
            supportedProps.putAll(getUtilProps());
            // adds instance props, including defaults
            supportedProps.putAll(properties);
            // resolve crossreference props
            builder.replaceParams(supportedProps);
        }
        return builder.toString();
    }

    public static Map<String, String> asStringMap(final Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        if (isNotEmpty(map)) {
            final Map<String, String> converted = new LinkedHashMap<String, String>(map.size());
            if (Properties.class.isAssignableFrom(map.getClass())) {
                // Properties are a case apart, because it can have 'defaults'.
                final Properties p = (Properties) map;
                for (String key : p.stringPropertyNames()) {
                    converted.put(key, p.getProperty(key));
                }
            } else {
                for (Entry<? extends Object, ? extends Object> entry : map.entrySet()) {
                    converted.put(CharSequenceUtils.toNullSafeString(entry.getKey()), CharSequenceUtils.toString(entry.getValue()));
                }
            }
            return converted;
        }
        return EMPTY_MAP;
    }

    public static Map<String, String> getSystemProps() {
        try {
            final Properties props = System.getProperties();
            final Map<String, String> map = new LinkedHashMap<String, String>();
            for (Entry<Object, Object> entry : props.entrySet()) {
                map.put(entry.getKey().toString(), entry.getValue().toString());
            }
            return map;
        } catch (SecurityException ex) {
            LOGGER.log(Level.WARNING, "Could not use System Properties.", ex);
            return Collections.EMPTY_MAP;
        }
    }

    public static Map<String, String> getUtilProps() {
        final Map<String, String> utilProps = new LinkedHashMap<String, String>(8);
        utilProps.put("date", DateUtils.formatDate(DateUtils.today()));
        utilProps.put("time", DateUtils.formatTime(DateUtils.now()));
        utilProps.put("year", CharSequenceUtils.toNullSafeString(DateUtils.year()));
        utilProps.put("month", CharSequenceUtils.toNullSafeString(DateUtils.month()));
        utilProps.put("dayOfMonth", CharSequenceUtils.toNullSafeString(DateUtils.dayOfMonth()));
        utilProps.put("dayOfWeek", CharSequenceUtils.toNullSafeString(DateUtils.dayOfWeek()));
        return utilProps;
    }
    
    public static boolean isEqualCollection(final Collection<?> a, final Collection<?> b) {
        return org.apache.commons.collections4.CollectionUtils.isEqualCollection(a, b);
    }
}
