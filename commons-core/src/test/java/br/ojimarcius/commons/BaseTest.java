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
package br.ojimarcius.commons;

import br.ojimarcius.commons.text.EnhancedStringBuilder;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mfonseca
 */
public class BaseTest {

    /**
     * Returns the logger to this class.
     *
     * @return The logger to this class.
     */
    public Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }

    /**
     * Log the time consumed, in msecs, by some test (end - start), as INFO level.
     *
     * @param start The start time.
     * @param end   The end time.
     */
    public void infoTime(final long start, final long end) {
        infoTime("Time consumed: {0} msecs.", start, end);
    }

    /**
     * Log the time consumed, in msecs, by some function (end - start), as INFO level.
     *
     * @param message The logging message.
     * @param start   The start time.
     * @param end     The end time.
     */
    public void infoTime(final CharSequence message, final long start, final long end) {
        log(Level.INFO, message, (end - start));
    }

    public void log(final Level level, final CharSequence message, final Object... messageParams) {
        getLogger().log(level, new EnhancedStringBuilder(message).replace("{", "}", messageParams).toString());
    }

    protected String randomString() {
        return randomString(15);
    }

    protected String randomString(int maxLen) {
        int len = NumberUtils.randomInteger(0, maxLen);
        StringBuilder builder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            builder.append(randomChar());
        }
        return builder.toString();
    }

    protected Character randomChar() {
        return chars.charAt(NumberUtils.randomInteger(0, CharSequenceUtils.indexOfLastChar(chars)));
    }
    static final String chars = "abcdefghijklmnopqrstuvxywz ABCDEFGHIJKLMNOPQRSTUVXYWZ 0123456789";
}
