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

import br.msf.commons.BaseTest;
import br.msf.commons.constants.Constants;
import br.msf.commons.text.MatchEntry;
import static br.msf.commons.util.CharSequenceUtils.compare;
import static br.msf.commons.util.CharSequenceUtils.containsPattern;
import static br.msf.commons.util.CharSequenceUtils.countOccurrencesOf;
import static br.msf.commons.util.CharSequenceUtils.find;
import static br.msf.commons.util.CharSequenceUtils.findPattern;
import static br.msf.commons.util.CharSequenceUtils.hasAccents;
import static br.msf.commons.util.CharSequenceUtils.indexOf;
import static br.msf.commons.util.CharSequenceUtils.isBlankOrNull;
import static br.msf.commons.util.CharSequenceUtils.isEmptyOrNull;
import static br.msf.commons.util.CharSequenceUtils.isNotBlank;
import static br.msf.commons.util.CharSequenceUtils.isNotEmpty;
import static br.msf.commons.util.CharSequenceUtils.lastIndexOf;
import static br.msf.commons.util.CharSequenceUtils.length;
import static br.msf.commons.util.CharSequenceUtils.listParams;
import static br.msf.commons.util.CharSequenceUtils.removeAccents;
import static br.msf.commons.util.CharSequenceUtils.toNullSafeString;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class CharSequenceUtilsTest extends BaseTest {

    private static final Collection<String> EMPTY_WORDS = CollectionUtils.EMPTY_LIST;
    private static final int K = 1000;

    @Test
    public void testLength() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, charSeqs]
            {0, null},
            {0, new CharSequence[]{}},
            {0, new CharSequence[]{null}},
            {0, new CharSequence[]{""}},
            {1, new CharSequence[]{" "}},
            {1, new CharSequence[]{"\n"}},
            {4, new CharSequence[]{"test"}},
            {6, new CharSequence[]{" test "}},
            {0, new CharSequence[]{new StringBuilder("")}},
            {1, new CharSequence[]{new StringBuilder(" ")}},
            {1, new CharSequence[]{new StringBuilder("\n")}},
            {4, new CharSequence[]{new StringBuilder("test")}},
            {6, new CharSequence[]{new StringBuilder(" test ")}},
            {0, new CharSequence[]{new StringBuffer("")}},
            {1, new CharSequence[]{new StringBuffer(" ")}},
            {1, new CharSequence[]{new StringBuffer("\n")}},
            {4, new CharSequence[]{new StringBuffer("test")}},
            {6, new CharSequence[]{new StringBuffer(" test ")}},
            {11, new CharSequence[]{"another", new StringBuffer("test")}}
        };
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], length((CharSequence[]) p[1]));
        }
        long t1 = System.currentTimeMillis();
        infoTime("length() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testIsEmptyOrNull() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, charSeq]
            {Boolean.TRUE, null},
            {Boolean.TRUE, ""},
            {Boolean.FALSE, " "},
            {Boolean.FALSE, "\n"},
            {Boolean.FALSE, "test"},
            {Boolean.FALSE, " test "},
            {Boolean.TRUE, new StringBuilder("")},
            {Boolean.FALSE, new StringBuilder(" ")},
            {Boolean.FALSE, new StringBuilder("\n")},
            {Boolean.FALSE, new StringBuilder("test")},
            {Boolean.FALSE, new StringBuilder(" test ")},
            {Boolean.TRUE, new StringBuffer("")},
            {Boolean.FALSE, new StringBuffer(" ")},
            {Boolean.FALSE, new StringBuffer("\n")},
            {Boolean.FALSE, new StringBuffer("test")},
            {Boolean.FALSE, new StringBuffer(" test ")}
        };
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], isEmptyOrNull((CharSequence) p[1]));
        }
        long t1 = System.currentTimeMillis();
        infoTime("isEmptyOrNull() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testIsNotEmpty() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, charSeq]
            {Boolean.FALSE, null},
            {Boolean.FALSE, ""},
            {Boolean.TRUE, " "},
            {Boolean.TRUE, "\n"},
            {Boolean.TRUE, "test"},
            {Boolean.TRUE, " test "},
            {Boolean.FALSE, new StringBuilder("")},
            {Boolean.TRUE, new StringBuilder(" ")},
            {Boolean.TRUE, new StringBuilder("\n")},
            {Boolean.TRUE, new StringBuilder("test")},
            {Boolean.TRUE, new StringBuilder(" test ")},
            {Boolean.FALSE, new StringBuffer("")},
            {Boolean.TRUE, new StringBuffer(" ")},
            {Boolean.TRUE, new StringBuffer("\n")},
            {Boolean.TRUE, new StringBuffer("test")},
            {Boolean.TRUE, new StringBuffer(" test ")}
        };
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], isNotEmpty((CharSequence) p[1]));
        }
        long t1 = System.currentTimeMillis();
        infoTime("isNotEmpty() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testIsBlankOrNull() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, charSeq]
            {Boolean.TRUE, null},
            {Boolean.TRUE, ""},
            {Boolean.TRUE, " "},
            {Boolean.TRUE, "\n"},
            {Boolean.FALSE, "test"},
            {Boolean.FALSE, " test "},
            {Boolean.TRUE, new StringBuilder("")},
            {Boolean.TRUE, new StringBuilder(" ")},
            {Boolean.TRUE, new StringBuilder("\n")},
            {Boolean.FALSE, new StringBuilder("test")},
            {Boolean.FALSE, new StringBuilder(" test ")},
            {Boolean.TRUE, new StringBuffer("")},
            {Boolean.TRUE, new StringBuffer(" ")},
            {Boolean.TRUE, new StringBuffer("\n")},
            {Boolean.FALSE, new StringBuffer("test")},
            {Boolean.FALSE, new StringBuffer(" test ")}
        };
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], isBlankOrNull((CharSequence) p[1]));
        }
        long t1 = System.currentTimeMillis();
        infoTime("isBlank() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testIsNotBlank() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, charSeq]
            {Boolean.FALSE, null},
            {Boolean.FALSE, ""},
            {Boolean.FALSE, " "},
            {Boolean.FALSE, "\n"},
            {Boolean.TRUE, "test"},
            {Boolean.TRUE, " test "},
            {Boolean.FALSE, new StringBuilder("")},
            {Boolean.FALSE, new StringBuilder(" ")},
            {Boolean.FALSE, new StringBuilder("\n")},
            {Boolean.TRUE, new StringBuilder("test")},
            {Boolean.TRUE, new StringBuilder(" test ")},
            {Boolean.FALSE, new StringBuffer("")},
            {Boolean.FALSE, new StringBuffer(" ")},
            {Boolean.FALSE, new StringBuffer("\n")},
            {Boolean.TRUE, new StringBuffer("test")},
            {Boolean.TRUE, new StringBuffer(" test ")}
        };
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], isNotBlank((CharSequence) p[1]));
        }
        long t1 = System.currentTimeMillis();
        infoTime("isNotBlank() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testCompare() {
        long t0 = System.currentTimeMillis();

        final Object[][] params = {
            // [expectedResult, charSeq0, charSeq1, caseSensitivity]
            {0, null, null, Constants.CASE_SENSITIVE},
            {0, "", "", Constants.CASE_SENSITIVE},
            {0, "", new StringBuilder(""), Constants.CASE_SENSITIVE},
            {0, "", new StringBuffer(""), Constants.CASE_SENSITIVE},
            {0, new StringBuilder(""), new StringBuffer(""),Constants.CASE_SENSITIVE},
            {0, "test", "test",Constants.CASE_SENSITIVE},
            {0, "test", new StringBuilder("test"),Constants.CASE_SENSITIVE},
            {0, "test", new StringBuffer("test"),Constants.CASE_SENSITIVE},
            {0, new StringBuilder("test"), new StringBuffer("test"),Constants.CASE_SENSITIVE},
            {0, null, "",Constants.CASE_SENSITIVE},
            {0, null, new StringBuilder(""),Constants.CASE_SENSITIVE},
            {0, null, new StringBuffer(""),Constants.CASE_SENSITIVE},
            {-1, "test", "test ",Constants.CASE_SENSITIVE},
            {-1, "test", new StringBuilder("test "),Constants.CASE_SENSITIVE},
            {-1, "test", new StringBuffer("test "),Constants.CASE_SENSITIVE},
            {-1, new StringBuilder("test"), new StringBuffer("test "),Constants.CASE_SENSITIVE},
            {1, "test", "Test",Constants.CASE_SENSITIVE},
            {1, "test", new StringBuilder("Test"),Constants.CASE_SENSITIVE},
            {1, "test", new StringBuffer("Test"),Constants.CASE_SENSITIVE},
            {1, new StringBuilder("test"), new StringBuffer("Test"),Constants.CASE_SENSITIVE}
        };
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], compare((CharSequence) p[1],
                                                                          (CharSequence) p[2],
                                                                          (Boolean) p[3]));
        }
        long t1 = System.currentTimeMillis();
        infoTime("isEquals() tested in {0} msecs", t0, t1);
    }

    /**
     * TODO : implement test
     */
    @Test
    public void testIsContained() {
    }

    @Test
    public void testHasAccents() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, word] */
            {Boolean.FALSE, null},
            {Boolean.FALSE, ""},
            {Boolean.FALSE, " "},
            {Boolean.TRUE, "á"},
            {Boolean.TRUE, "test é"},
            {Boolean.FALSE, new StringBuilder("")},
            {Boolean.FALSE, new StringBuilder(" ")},
            {Boolean.TRUE, new StringBuilder("á")},
            {Boolean.TRUE, new StringBuilder("test é")},
            {Boolean.FALSE, new StringBuffer("")},
            {Boolean.FALSE, new StringBuffer(" ")},
            {Boolean.TRUE, new StringBuffer("á")},
            {Boolean.TRUE, new StringBuffer("test é")}
        };
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], hasAccents((CharSequence) p[1]));
        }
        long t1 = System.currentTimeMillis();
        infoTime("hasAccents() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testRemoveAccents() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, word]
            {null, null},
            {"", ""},
            {" ", " "},
            {"a", "a"},
            {"test e", "test é"},
            {"", new StringBuilder("")},
            {" ", new StringBuilder(" ")},
            {"a", new StringBuilder("á")},
            {"test e", new StringBuilder("test é")},
            {"", new StringBuffer("")},
            {" ", new StringBuffer(" ")},
            {"a", new StringBuffer("á")},
            {"test e", new StringBuffer("test é")},
            {"aceyuio", new StringBuffer("áçéýúíó")},
            {"ACEYUIO", new StringBuffer("ÁÇÉÝÚÍÓ")},
            {"nao", new StringBuffer("ñãõ")},
            {"NAO", new StringBuffer("ÑÃÕ")},
            {"aeuio", new StringBuffer("âêûîô")},
            {"AEUIO", new StringBuffer("ÂÊÛÎÔ")},
            {"aeuio", new StringBuffer("àèùìò")},
            {"AEUIO", new StringBuffer("ÀÈÙÌÒ")},
            {"aeuio", new StringBuffer("äëüïö")},
            {"AEUIO", new StringBuffer("ÄËÜÏÖ")}
        };
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], removeAccents((CharSequence) p[1]));
        }

        int iterations = 4000 * K; // 4 MegaChars. wow! this is a reeealy big text!!!
        StringBuilder input = new StringBuilder(iterations * 5);
        StringBuilder expected = new StringBuilder(iterations * 5);
        for (int i = 0; i < iterations; i++) {
            input.append("àèùìò");
            expected.append("aeuio");
        }
        assertEquals("Failed with big param", expected.toString(), removeAccents(input));
        long t1 = System.currentTimeMillis();
        infoTime("removeAccents() tested in {0} msecs on a 4MB accentued text", t0, t1);
    }

    @Test
    public void testListParams() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, word, startDelimiter, endDelimiter]
            {EMPTY_WORDS, null, Constants.DEFAULT_PARAM_START, Constants.DEFAULT_PARAM_END},
            {EMPTY_WORDS, "", Constants.DEFAULT_PARAM_START, Constants.DEFAULT_PARAM_END},
            {EMPTY_WORDS, " ", Constants.DEFAULT_PARAM_START, Constants.DEFAULT_PARAM_END},
            {EMPTY_WORDS, "test", Constants.DEFAULT_PARAM_START, Constants.DEFAULT_PARAM_END},
            {Arrays.asList("par0", "par1"), "test ${par0} ${par1}", Constants.DEFAULT_PARAM_START, Constants.DEFAULT_PARAM_END},
            {EMPTY_WORDS, null, "{", "}"},
            {EMPTY_WORDS, "", "{", "}"},
            {EMPTY_WORDS, " ", "{", "}"},
            {EMPTY_WORDS, "test", "{", "}"},
            {Arrays.asList("par0", "par1"), "test {par0} {par1}", "{", "}"},};
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertTrue("Failed with params at line " + i, CollectionUtils.isEqualCollection(
                    (Collection) p[0], listParams((CharSequence) p[1], (String) p[2], (String) p[3])));
        }
        long t1 = System.currentTimeMillis();
        infoTime("listParams() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testToString() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, word, startDelimiter, endDelimiter]
            {null, null},
            {"", ""},
            {" ", " "},
            {"test", "test"},
            {"", new StringBuilder("")},
            {" ", new StringBuilder(" ")},
            {"test", new StringBuilder("test")},
            {"", new StringBuffer("")},
            {" ", new StringBuffer(" ")},
            {"test", new StringBuffer("test")},};
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], CharSequenceUtils.toString(p[1]));
        }
        long t1 = System.currentTimeMillis();
        infoTime("listParams() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testToNullSafeString() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, word, startDelimiter, endDelimiter]
            {"", null},
            {"", ""},
            {" ", " "},
            {"test", "test"},
            {"", new StringBuilder("")},
            {" ", new StringBuilder(" ")},
            {"test", new StringBuilder("test")},
            {"", new StringBuffer("")},
            {" ", new StringBuffer(" ")},
            {"test", new StringBuffer("test")},};
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], toNullSafeString(p[1]));
        }
        long t1 = System.currentTimeMillis();
        infoTime("listParams() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testFind() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, regex, sequence]
            {CollectionUtils.EMPTY_LIST, null, null, Constants.CASE_SENSITIVE},
            {CollectionUtils.EMPTY_LIST, null, "", Constants.CASE_SENSITIVE},
            {CollectionUtils.EMPTY_LIST, "", null, Constants.CASE_SENSITIVE},
            {CollectionUtils.EMPTY_LIST, "", "", Constants.CASE_SENSITIVE},
            {Arrays.asList(new MatchEntry(4, 6)), "  ", "test  test", Constants.CASE_SENSITIVE},
            {Arrays.asList(new MatchEntry(0, 4), new MatchEntry(6, 10)), "test", "test  test", Constants.CASE_SENSITIVE},
            {Arrays.asList(new MatchEntry(0, 4)), "test", "test  TeSt", Constants.CASE_SENSITIVE},
            {CollectionUtils.EMPTY_LIST, null, null, Constants.CASE_INSENSITIVE},
            {CollectionUtils.EMPTY_LIST, null, "", Constants.CASE_INSENSITIVE},
            {CollectionUtils.EMPTY_LIST, "", null, Constants.CASE_INSENSITIVE},
            {CollectionUtils.EMPTY_LIST, "", "", Constants.CASE_INSENSITIVE},
            {Arrays.asList(new MatchEntry(4, 6)), "  ", "test  test", Constants.CASE_INSENSITIVE},
            {Arrays.asList(new MatchEntry(0, 4), new MatchEntry(6, 10)), "test", "test  test", Constants.CASE_INSENSITIVE},
            {Arrays.asList(new MatchEntry(0, 4), new MatchEntry(6, 10)), "test", "test  TeSt", Constants.CASE_INSENSITIVE},};
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];

            assertTrue("Failed with params at line " + i, CollectionUtils.isEqualCollection(
                    (Collection) p[0], find((CharSequence) p[1], (CharSequence) p[2], (Boolean) p[3])));
        }
        long t1 = System.currentTimeMillis();
        infoTime("find() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testFindPattern() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, regex, sequence]
            {CollectionUtils.EMPTY_LIST, null, null},
            {CollectionUtils.EMPTY_LIST, null, ""},
            {CollectionUtils.EMPTY_LIST, "", null},
            {CollectionUtils.EMPTY_LIST, "", ""},
            {Arrays.asList(new MatchEntry(4, 6)), "\\s+", "test  test"},
            {Arrays.asList(new MatchEntry(0, 4), new MatchEntry(6, 10)), "\\w+", "test  test"}//,
        };
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];

            assertTrue("Failed with params at line " + i, CollectionUtils.isEqualCollection(
                    (Collection) p[0], findPattern((CharSequence) p[1], (CharSequence) p[2])));
        }
        long t1 = System.currentTimeMillis();
        infoTime("findPattern() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testContainsPattern() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, charSeq]
            {Boolean.FALSE, null, null},
            {Boolean.FALSE, null, ""},
            {Boolean.FALSE, null, " "},
            {Boolean.FALSE, null, "\n"},
            {Boolean.FALSE, null, "test"},
            {Boolean.FALSE, "", ""},
            {Boolean.FALSE, "", " "},
            {Boolean.TRUE, "^\\s", " test "},
            {Boolean.TRUE, "^\\s+|\\s+$", " test "},
            {Boolean.TRUE, "test", " test "},
            {Boolean.FALSE, "[0-9]+", " test "},
            {Boolean.FALSE, "", new StringBuilder("")},
            {Boolean.FALSE, "", new StringBuilder(" ")},
            {Boolean.TRUE, "^\\s", new StringBuilder(" test ")},
            {Boolean.TRUE, "^\\s+|\\s+$", new StringBuilder(" test ")},
            {Boolean.TRUE, "test", new StringBuilder(" test ")},
            {Boolean.FALSE, "[0-9]+", new StringBuilder(" test ")},};
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            assertEquals("Failed with params at line " + i, p[0], containsPattern((CharSequence) p[1], (CharSequence) p[2]));
        }
        long t1 = System.currentTimeMillis();
        infoTime("containsPattern() tested in {0} msecs", t0, t1);
    }

    /**
     * TODO : implement test
     */
    @Test
    public void testSplit() {
    }

    @Test
    public void testIndexOf() {
        long t0 = System.currentTimeMillis();
        assertTrue(indexOf(null, 0, null) == -1);
        assertTrue(indexOf(null, 0, "") == -1);
        assertTrue(indexOf(null, 0, " ") == -1);
        assertTrue(indexOf(null, 0, "\n") == -1);
        assertTrue(indexOf(null, 0, "test") == -1);
        String sequence, sub;
        int from;

        sub = "";
        sequence = "";
        from = 0;
        assertEquals(sequence.indexOf(sub, from), indexOf(sub, from, sequence));

        sub = "";
        sequence = "test";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.indexOf(sub, from), indexOf(sub, from, sequence));
        }

        sub = "";
        sequence = " ";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.indexOf(sub, from), indexOf(sub, from, sequence));
        }

        sub = " ";
        sequence = "";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.indexOf(sub, from), indexOf(sub, from, sequence));
        }

        sub = " ";
        sequence = " ";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.indexOf(sub, from), indexOf(sub, from, sequence));
        }

        sub = "t";
        sequence = "test";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.indexOf(sub, from), indexOf(sub, from, sequence));
        }

        sub = "test";
        sequence = "st";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.indexOf(sub, from), indexOf(sub, from, sequence));
        }

        sub = "te";
        sequence = "te";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.indexOf(sub, from), indexOf(sub, from, sequence));
        }

        for (int i = 0; i < 100000; i++) {
            String s1 = randomString();
            String s2 = randomString();
            int f = NumberUtils.randomInteger(0, 30);
            assertEquals("fail >> [" + s1 + ":" + f + ":" + s2 + "]", s1.indexOf(s2, f), indexOf(s2, f, s1));
        }
        long t1 = System.currentTimeMillis();
        infoTime("indexOf() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testLastIndexOf() {
        long t0 = System.currentTimeMillis();
        assertTrue(lastIndexOf(null, 0, null) == -1);
        assertTrue(lastIndexOf(null, 0, "") == -1);
        assertTrue(lastIndexOf(null, 0, " ") == -1);
        assertTrue(lastIndexOf(null, 0, "\n") == -1);
        assertTrue(lastIndexOf(null, 0, "test") == -1);
        String sequence, sub;
        int from;

        sub = "";
        sequence = "";
        from = 0;
        assertEquals(sequence.lastIndexOf(sub, from), lastIndexOf(sub, from, sequence));

        sub = "";
        sequence = "test";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.lastIndexOf(sub, from), lastIndexOf(sub, from, sequence));
        }

        sub = "";
        sequence = " ";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.lastIndexOf(sub, from), lastIndexOf(sub, from, sequence));
        }

        sub = " ";
        sequence = "";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.lastIndexOf(sub, from), lastIndexOf(sub, from, sequence));
        }

        sub = " ";
        sequence = " ";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.lastIndexOf(sub, from), lastIndexOf(sub, from, sequence));
        }

        sub = "t";
        sequence = "test";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.lastIndexOf(sub, from), lastIndexOf(sub, from, sequence));
        }

        sub = "test";
        sequence = "st";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.lastIndexOf(sub, from), lastIndexOf(sub, from, sequence));
        }

        sub = "te";
        sequence = "te";
        for (from = 0; from < sequence.length(); from++) {
            assertEquals(sequence.lastIndexOf(sub, from), lastIndexOf(sub, from, sequence));
        }

        for (int i = 0; i < 100000; i++) {
            String s1 = randomString();
            String s2 = randomString();
            int f = NumberUtils.randomInteger(0, 30);
            assertEquals("fail >> [" + s1 + ":" + f + ":" + s2 + "]", s1.lastIndexOf(s2, f), lastIndexOf(s2, f, s1));
        }
        long t1 = System.currentTimeMillis();
        infoTime("lastIndexOf() tested in {0} msecs", t0, t1);
    }

    @Test
    public void testCountOccurrencesOf() {
        long t0 = System.currentTimeMillis();
        final Object[][] params = {
            // [expectedResult, charSeq]
            {0, null, 0, null},
            {1, "", 0, ""},
            {0, "", 0, "test"},
            {5, "r", 0, "o rato roeu a roupa do rei de roma."},
            {1, "rato", 0, "o rato roeu a roupa do rei de roma."},
            {6, "o", 0, "o rato roeu a roupa do rei de roma."},};
        for (int i = 0; i < params.length; i++) {
            Object[] p = params[i];
            int res = countOccurrencesOf((CharSequence) p[1], (Integer) p[2], (CharSequence) p[3]);
            assertEquals("Failed with params at line " + i, p[0], res);
        }
        long t1 = System.currentTimeMillis();
        infoTime("countOccurrencesOf() tested in {0} msecs", t0, t1);

    }

    /**
     * TODO : implement test
     */
    @Test
    public void testStartsWith() {
    }

    /**
     * TODO : implement test
     */
    @Test
    public void testEndsWith() {
    }

    /**
     * TODO : implement test
     */
    @Test
    public void testGetCharCount() {
    }
}
