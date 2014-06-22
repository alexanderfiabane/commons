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
package br.ojimarcius.commons.lang;

import br.ojimarcius.commons.text.EnhancedStringBuilder;
import br.ojimarcius.commons.text.EnhancedStringBuilder;
import br.ojimarcius.commons.BaseTest;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EnhancedStringBuilderTest extends BaseTest {

    @Test
    public void testMtrim() {
        String arg0 = "    test of    |    trim    ,   in  the\t\tmiddle   ";
        String ret0 = new EnhancedStringBuilder(arg0).mtrim(false).toString();
        String expected0 = "    test of | trim , in the middle   ";
        assertEquals(expected0, ret0);

        String arg1 = "    test of    |    trim    \r\n   in  the\t\tmiddle   ";
        String ret1 = new EnhancedStringBuilder(arg1).mtrim(false).toString();
        String expected1 = "    test of | trim in the middle   ";
        assertEquals(expected1, ret1);

        String arg2 = "    test of    |    trim    \n   in  the\t\tmiddle   ";
        String ret2 = new EnhancedStringBuilder(arg2).mtrim(false).toString();
        String expected2 = "    test of | trim in the middle   ";
        assertEquals(expected2, ret2);

        String arg3 = "    test of    |    trim    \n   \n   in  the\t\tmiddle   ";
        String ret3 = new EnhancedStringBuilder(arg3).mtrim(false).toString();
        String expected3 = "    test of | trim in the middle   ";
        assertEquals(expected3, ret3);

        String arg4 = "    test of    |    trim    ,   in  the\t\tmiddle   ";
        String ret4 = new EnhancedStringBuilder(arg0).mtrim(true).toString();
        String expected4 = "    test of | trim , in the middle   ";
        assertEquals(expected4, ret4);

        String arg5 = "    test of    |    trim    \r\n   in  the\t\tmiddle   ";
        String ret5 = new EnhancedStringBuilder(arg1).mtrim(true).toString();
        String expected5 = "    test of | trim    \r\n   in the middle   ";
        assertEquals(expected5, ret5);

        String arg6 = "    test of    |    trim    \n   in  the\t\tmiddle   ";
        String ret6 = new EnhancedStringBuilder(arg6).mtrim(true).toString();
        String expected6 = "    test of | trim    \n   in the middle   ";
        assertEquals(expected6, ret6);

        String arg7 = "    test of    |    trim    \n   \n   in  the\t\tmiddle   ";
        String ret7 = new EnhancedStringBuilder(arg7).mtrim(true).toString();
        String expected7 = "    test of | trim    \n   \n   in the middle   ";
        assertEquals(expected7, ret7);
    }

    @Test
    public void testToCamelCase() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, charSeqs]
            {"", null},
            {"", ""},
            {"", " "},
            {"", "\n \t"},
            {"Test", "test"},
            {"Test", "  test"},
            {"Test", "test  "},
            {"Test", "  test  "},
            {"TestTest", "test test"},
            {"TestTest", "test_test"},
            {"TestTest001", "test_test001"},
            {"001TestTest", "001test_test"},};
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            final EnhancedStringBuilder builder = new EnhancedStringBuilder(p[1]);
            assertEquals("Failed with params at line " + i, p[0], builder.toCamelCase().toString());
        }
        long t1 = System.currentTimeMillis();
        infoTime("toCamelCase() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testToUnderscore() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, charSeqs]
            {"", null},
            {"", ""},
            {"", " "},
            {"", "\n \t"},
            {"TEST", "test"},
            {"TEST", "  test"},
            {"TEST", "test  "},
            {"TEST", "  test  "},
            {"TEST_TEST", "test test"},
            {"TEST_TEST", "testTest"},
            {"TEST_TEST", "test_test"},
            {"TEST_TEST001", "test_test001"},
            {"001_TEST_TEST", "001test_test"},};
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            final EnhancedStringBuilder builder = new EnhancedStringBuilder(p[1]);
            assertEquals("Failed with params at line " + i, p[0], builder.toUnderscore().toString());
        }
        long t1 = System.currentTimeMillis();
        infoTime("toUnderscore() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testCapitalizeFirst() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, charSeqs]
            {"", null},
            {"", ""},
            {" ", " "},
            {"\n \t", "\n \t"},
            {"Test", "test"},
            {"  Test", "  test"},
            {"Test  ", "test  "},
            {"  Test  ", "  test  "},
            {"Test test", "test test"},
            {"Test   test", "test   test"},
            {"Test_test", "test_test"},
            {"Test_test001", "test_test001"},
            {"001test_test", "001test_test"},
            {"O rato roeu a roupa do rei de roma. Depois fugiu.", "o rato roeu a roupa do rei de roma. depois fugiu."},
            {"   O rato roeu a roupa do rei de roma. Depois fugiu!!!", "   o rato roeu a roupa do rei de roma. depois fugiu!!!"},
            {" -  O rato roeu a roupa do rei de roma. Depois fugiu!!!", " -  o rato roeu a roupa do rei de roma. depois fugiu!!!"},};
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            final EnhancedStringBuilder builder = new EnhancedStringBuilder(p[1]);
            assertEquals("Failed with params at line " + i, p[0], builder.capitalizeFirst().toString());
        }
        long t1 = System.currentTimeMillis();
        infoTime("capitalizeFirst() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testCapitalizeAll() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, charSeqs]
            {"", null},
            {"", ""},
            {" ", " "},
            {"\n \t", "\n \t"},
            {"Test", "test"},
            {"  Test", "  test"},
            {"Test  ", "test  "},
            {"  Test  ", "  test  "},
            {"Test Test", "test test"},
            {"Test   Test", "test   test"},
            {"Test_Test", "test_test"},
            {"Test_Test001", "test_test001"},
            {"001test_Test", "001test_test"},
            {"O Rato Roeu A Roupa Do Rei De Roma. Depois Fugiu.", "o rato roeu a roupa do rei de roma. depois fugiu."},
            {"   O Rato Roeu A Roupa Do Rei De Roma. Depois Fugiu.", "   o rato roeu a roupa do rei de roma. depois fugiu."},
            {" -  O Rato Roeu A Roupa Do Rei De Roma. Depois Fugiu.", " -  o rato roeu a roupa do rei de roma. depois fugiu."}
        };
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            final EnhancedStringBuilder builder = new EnhancedStringBuilder(p[1]);
            assertEquals("Failed with params at line " + i, p[0], builder.capitalizeAll().toString());
        }
        long t1 = System.currentTimeMillis();
        infoTime("capitalizeAll() tested in {0} msecs", t0, t1);
    }
}
