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
package br.msf.commons.io;

import br.msf.commons.util.CharSequenceUtils;
import java.util.regex.Pattern;

/**
 * Enumeration of the default categories of mimetypes.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public enum MimeTypeCategory {

    IMAGE("image/\\w*"),
    APPLICATION("application/\\w*"),
    TEXT("text/\\w*"),
    AUDIO("audio/\\w*"),
    VIDEO("video/\\w*"),
    MULTIPART("multipart/\\w*"),
    NOT_STANDARD("^(?!.*(image/|application/|audio/|text/|video/|multipart/)).*$");
    private final Pattern pattern;

    /**
     * Creates a new MimeTypeCategory, defining its pattern.
     *
     * @param pattern The pattern to match the mimetype category.
     */
    private MimeTypeCategory(final String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    /**
     * Extracts the MimeTypeCategory from the given mimeType.
     *
     * @param mimeType The mimeType whose its category must be extracted.
     * @return The MimeTypeCategory that matches the given mimeType.
     */
    public static MimeTypeCategory parse(final String mimeType) {
        if (CharSequenceUtils.isBlankOrNull(mimeType)) {
            return null;
        }
        for (MimeTypeCategory cat : MimeTypeCategory.values()) {
            if (cat.matches(mimeType)) {
                return cat;
            }
        }
        return NOT_STANDARD;
    }

    /**
     * Indicates if the given mimeType is of Application category ("application/*").
     *
     * @param mimeType The mimeType to check.
     * @return True, if the mimetype matches the {@link #APPLICATION} pattern. False, otherwise.
     */
    public static boolean isApplication(final String mimeType) {
        return isCategory(APPLICATION, mimeType);
    }

    /**
     * Indicates if the given mimeType is of Image category ("image/*").
     *
     * @param mimeType The mimeType to check.
     * @return True, if the mimetype matches the {@link #IMAGE} pattern. False, otherwise.
     */
    public static boolean isImage(final String mimeType) {
        return isCategory(IMAGE, mimeType);
    }

    /**
     * Indicates if the given mimeType is of Text category ("text/*").
     *
     * @param mimeType The mimeType to check.
     * @return True, if the mimetype matches the {@link #TEXT} pattern. False, otherwise.
     */
    public static boolean isText(final String mimeType) {
        return isCategory(TEXT, mimeType);
    }

    /**
     * Indicates if the given mimeType is of Audio category ("audio/*").
     *
     * @param mimeType The mimeType to check.
     * @return True, if the mimetype matches the {@link #AUDIO} pattern. False, otherwise.
     */
    public static boolean isAudio(final String mimeType) {
        return isCategory(AUDIO, mimeType);
    }

    /**
     * Indicates if the given mimeType is of Video category ("video/*").
     *
     * @param mimeType The mimeType to check.
     * @return True, if the mimetype matches the {@link #VIDEO} pattern. False, otherwise.
     */
    public static boolean isVideo(final String mimeType) {
        return isCategory(VIDEO, mimeType);
    }

    /**
     * Indicates if the given mimeType is of Multipart category ("multipart/*").
     *
     * @param mimeType The mimeType to check.
     * @return True, if the mimetype matches the {@link #MULTIPART} pattern. False, otherwise.
     */
    public static boolean isMultiPart(final String mimeType) {
        return isCategory(MULTIPART, mimeType);
    }

    /**
     * Indicates if the given mimeType doesnt matches any of the standard categories.
     *
     * @param mimeType The mimeType to check.
     * @return True, if the mimetype matches the {@link #NOT_STANDARD} pattern. False, otherwise.
     */
    public static boolean isNotStandard(final String mimeType) {
        return isCategory(NOT_STANDARD, mimeType);
    }

    /**
     * Indicates if the given mimeType does matches the given category.
     *
     * @param category The category to be matched.
     * @param mimeType The mimeType to check.
     * @return True, if the given mimetype matches the given category pattern. False, otherwise.
     */
    public static boolean isCategory(final MimeTypeCategory category, final String mimeType) {
        return category.matches(mimeType);
    }

    /**
     * Indicates if the given mimeType does matches this category.
     *
     * @param mimeType The mimeType to check.
     * @return True, if the given mimetype matches this category pattern. False, otherwise.
     */
    protected boolean matches(final String mimeType) {
        return CharSequenceUtils.isNotBlank(mimeType) && this.pattern.matcher(mimeType).matches();
    }
}
