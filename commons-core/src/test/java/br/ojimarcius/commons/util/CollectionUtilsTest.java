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

import br.ojimarcius.commons.util.CollectionUtils;
import br.ojimarcius.commons.BaseTest;
import br.ojimarcius.commons.constants.Constants;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CollectionUtilsTest extends BaseTest {

    @SuppressWarnings("unchecked")
    private static final Collection<String> EMPTY_WORDS = CollectionUtils.EMPTY_LIST;
    private static final Collection<String> WORDS = Arrays.asList(null, "test", "test ", " ", "\n", "");

    @Test
    public void testIsEmpty() {
        // TODO : implement
    }

    @Test
    public void testIsNotEmpty() {
        // TODO : implement
    }

    @Test
    public void testIsList() {
        // TODO : implement
    }

    @Test
    public void testIsSet() {
        // TODO : implement
    }

    @Test
    public void testIsSortedSet() {
        // TODO : implement
    }

    @Test
    public void testIsQueue() {
        // TODO : implement
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testContains() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, word, collection, caseSensitivity]
            {Boolean.TRUE, null, WORDS,Constants.CASE_SENSITIVE},
            {Boolean.TRUE, "test", WORDS,Constants.CASE_SENSITIVE},
            {Boolean.TRUE, "test ", WORDS,Constants.CASE_SENSITIVE},
            {Boolean.TRUE, " ", WORDS,Constants.CASE_SENSITIVE},
            {Boolean.TRUE, "\n", WORDS,Constants.CASE_SENSITIVE},
            {Boolean.TRUE, "", WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "Test", WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "\t", WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "null", WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, null, EMPTY_WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "test", EMPTY_WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "test ", EMPTY_WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, " ", EMPTY_WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "\n", EMPTY_WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "", EMPTY_WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "Test", EMPTY_WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "\t", EMPTY_WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "null", EMPTY_WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, null, null,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "test", null,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "test ", null,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, " ", null,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "\n", null,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "", null,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "Test", null,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "\t", null,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, "null", null,Constants.CASE_SENSITIVE},
            {Boolean.TRUE, new StringBuilder("test"), WORDS,Constants.CASE_SENSITIVE},
            {Boolean.FALSE, new StringBuffer("Test"), WORDS,Constants.CASE_SENSITIVE},
            {Boolean.TRUE, new StringBuffer("Test"), WORDS,Constants.CASE_INSENSITIVE},
            {Boolean.TRUE, "Test", WORDS,Constants.CASE_INSENSITIVE},
            {Boolean.FALSE, "Test", EMPTY_WORDS,Constants.CASE_INSENSITIVE},
            {Boolean.FALSE, "Test", null,Constants.CASE_INSENSITIVE},};
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], CollectionUtils.contains(
                    (Collection<CharSequence>) p[2], (CharSequence) p[1], (Boolean) p[3]));
        }
        long t1 = System.currentTimeMillis();
        infoTime("contains() tested in {0} msecs", t0, t1);
    }
}
