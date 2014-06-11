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
public abstract class Constants {

    /**
     * Default parameter start delimiter.
     * <p/>
     * value = "${".
     */
    public static final String DEFAULT_PARAM_START = "${";
    /**
     * Default parameter end delimiter.
     * <p/>
     * value = "}".
     */
    public static final String DEFAULT_PARAM_END = "}";
    /**
     * A constant just to keep source files more readable.
     * <p/>
     * Used on methods that have a boolean case sensitivity parameter.
     * <p/>
     * It is just more easy to understand
     * <ul>
     * <li><tt>MyObj.contains("word", <b>CASE_SENSITIVE</b>);</tt></li>
     * </ul>
     * than
     * <ul>
     * <li><tt>MyObj.contains("word", <b>true</b>);</tt></li>
     * </ul>
     */
    public static final Boolean CASE_SENSITIVE = Boolean.TRUE;
    /**
     * A constant just to keep source files more readable.
     * <p/>
     * Used on methods that have a boolean case sensitivity parameter.
     * <p/>
     * It is just more easy to understand
     * <ul>
     * <li><tt>MyObj.contains("word", <b>CASE_INSENSITIVE</b>);</tt></li>
     * </ul>
     * than
     * <ul>
     * <li><tt>MyObj.contains("word", <b>false</b>);</tt></li>
     * </ul>
     */
    public static final Boolean CASE_INSENSITIVE = Boolean.FALSE;

}
