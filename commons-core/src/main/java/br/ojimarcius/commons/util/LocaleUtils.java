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

import java.util.Locale;

/**
 * Class that contains utilities to handle {@link Locale Locales}.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public abstract class LocaleUtils extends org.apache.commons.lang3.LocaleUtils {

    /**
     * Constant that holds the locale used in Brazil.
     */
    public static final Locale PT_BR_LOCALE = new Locale("pt", "BR");

    /**
     * Returns the given locale, or the system defaults if the given one is null.
     *
     * @param preferredLocale
     * @return The given locale, or the system defaults if the given one is null.
     */
    public static Locale getNullSafeLocale(final Locale preferredLocale) {
        //return preferredLocale == null ? PT_BR_LOCALE : preferredLocale;
        return preferredLocale == null ? Locale.getDefault() : preferredLocale;
    }
}
