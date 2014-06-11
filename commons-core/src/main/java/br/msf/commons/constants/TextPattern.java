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
package br.msf.commons.constants;

/**
 * A collection of String Constants used generally on many types of functionality.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public abstract class TextPattern {

    /**
     * Regex that matches one or more letters in sequence. Works with accented chars.
     */
    public static final String LETTERS = "\\p{L}+";
    /**
     * Regex that matches one or more lowercase letters in sequence. Works with accented chars.
     */
    public static final String LOWERCASES = "\\p{Ll}+";
    /**
     * Regex that matches one or more uppercase letters in sequence. Works with accented chars.
     */
    public static final String UPPERCASES = "\\p{Lu}+";
    /**
     * Regex that matches one or more digits in sequence.
     */
    public static final String DIGITS = "\\d+";
    /**
     * Regex that matches one or more spaces in sequence.
     */
    public static final String SPACES = "\\s+";
    /**
     * Regex that matches one or more symbols in sequence.
     */
    public static final String SIMBOLS = "[\\p{P}\\p{S}]+";
    /**
     * Regex that matches one or more punctuation chars in sequence (!,.:;?).
     */
    public static final String PUNCTUATIONS = "[!,.:;?]+";
    /**
     * Regex that matches one or more NON-ACCENTED vowels in sequence.
     */
    public static final String VOWELS = "[aeiouAEIOU]+";
    /**
     * Regex that matches one or more NON-ACCENTED consonants in sequence.
     */
    public static final String CONSONANTS = "[b-df-hj-np-tv-zB-DF-HJ-NP-TV-Z]+";
}
