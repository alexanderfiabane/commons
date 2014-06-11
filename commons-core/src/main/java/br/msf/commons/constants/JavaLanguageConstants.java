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
 * Interface containing various common use constants related on the java language.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public abstract class JavaLanguageConstants {

    /**
     * Constant Utils cannot be instantiated.
     */
    private JavaLanguageConstants() {
    }
    /* Java naming convention regex */
    static final String NAME_REGEX = "[a-z|A-Z|_]+[a-z|A-Z|0-9|_]*";
    static final String CLASS_NAME_REGEX = "[A-Z]+[a-z|A-Z|0-9]*";
    static final String FIELD_NAME_REGEX = "[a-z]+[a-z|A-Z|0-9]*";
}
