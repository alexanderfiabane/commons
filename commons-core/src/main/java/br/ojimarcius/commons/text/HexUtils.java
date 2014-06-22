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
package br.ojimarcius.commons.text;

import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.ArgumentUtils;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * Class that manipulates Strings representing hexadecimal numbers.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public abstract class HexUtils {

    /**
     * Pattern used to validate hex Strings.
     */
    protected static final Pattern HEX_PATTERN = Pattern.compile("[0-9|a-f|A-F]+");
    /**
     * Symbol used to separate groups of nibbles.
     */
    protected static final String GROUP_SEPARATOR = ".";
    /**
     * Symbol used to separate groups of nibbles, as a RegExp.
     */
    protected static final String GROUP_SEPARATOR_REGEX = "\\.";

    /**
     * Util files cannot be instantiated.
     */
    private HexUtils() {
    }

    /**
     * Converts an hex number to its byte notation.
     *
     * @param hexString The String representing the hex number.
     * @return The set of bytes that represents the given hex number.
     */
    public static byte[] toBytes(final String hexString) {
        final String unformatted = unformat(hexString);
        ArgumentUtils.rejectIfDontMatches(unformatted, HEX_PATTERN);
        int length = unformatted.length() / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(unformatted.charAt(i * 2), 16);
            int low = Character.digit(unformatted.charAt(i * 2 + 1), 16);
            int value = (high << 4) | low;
            if (value > 127) {
                value -= 256;
            }
            raw[i] = (byte) value;
        }
        return raw;
    }

    /**
     * Converts a byte set to its hex representing number.
     *
     * @param bytes The bytes to be converted.
     * @return The hex number representing the given bytes.
     */
    public static String toHexString(final byte[] bytes) {
        ArgumentUtils.rejectIfNull(bytes);
        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            int high = ((aByte >> 4) & 0xf) << 4;
            int low = aByte & 0xf;
            if (high == 0) {
                builder.append('0');
            }
            builder.append(Integer.toHexString(high | low));
        }
        return builder.toString();
    }

    /**
     * Puts a separator between groups of <tt>groupLen</tt> nibbles.
     * <p/>
     * Also, puts leading zeroes when necessary.
     *
     * @param hexString The hex string to be formatted.
     * @param groupLen  The length of the groups of nibbles.
     * @return The formatted hex string.
     */
    public static String format(final String hexString, final int groupLen) {
        final String unformatted = unformat(hexString);
        ArgumentUtils.rejectIfDontMatches(unformatted, HEX_PATTERN);
        final StringBuilder buffer = new StringBuilder();
        final StringBuilder formatted = new StringBuilder();
        for (int i = CharSequenceUtils.indexOfLastChar(unformatted); i >= 0; i--) {
            buffer.insert(0, unformatted.charAt(i));
            if (buffer.length() == groupLen) {
                /* When the buffer reaches 'groupLen' size, its contents is passed to 'formatted'. */
                if (i > 0) {
                    /*
                     * If unprocessed chars remains on the original string, them we put the separator on the buffer
                     * start.
                     */
                    buffer.insert(0, GROUP_SEPARATOR);
                }
                /* we pass the buffer value to the 'formatted' accumulator */
                formatted.insert(0, buffer);
                /* empty the buffer */
                buffer.replace(0, buffer.length(), "");
            }
        }
        /* If unprocessed chars remains on the buffer, it means that we need to fill it up with trailing zeroes. */
        if (buffer.length() > 0) {
            buffer.insert(0, StringUtils.repeat("0", groupLen - buffer.length()));
            /* we pass the buffer value to the 'formatted' accumulator */
            formatted.insert(0, buffer);
        }
        return formatted.toString();
    }

    /**
     * Removes the "0x" prefix, the "H" or "h" suffix, the separators and the leading zeros when applicable.
     *
     * @param hexString The hex string to be cleared up.
     * @return The cleaned up hex string.
     */
    public static String unformat(final String hexString) {
        ArgumentUtils.rejectIfNull(hexString);
        String unformatted = hexString.replaceAll(GROUP_SEPARATOR_REGEX, "").
                replaceAll("^0x", "").
                replaceAll("H$", "").
                replaceAll("h$", "").
                replaceAll("^0+", "");
        if (!HEX_PATTERN.matcher(unformatted).matches()) {
            throw new IllegalArgumentException("Invalid hex.");
        }
        return unformatted;
    }
}
