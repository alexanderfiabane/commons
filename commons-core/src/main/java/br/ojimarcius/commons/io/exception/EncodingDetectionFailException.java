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
package br.ojimarcius.commons.io.exception;

/**
 * Exception thrown when the attempt to detect the encoding of a file fails.
 * <p/>
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public class EncodingDetectionFailException extends RuntimeException {

    private static final long serialVersionUID = 4333058733732101526L;
    /**
     * Default message used when none is defined.
     */
    private static final String DEFAULT_MESSAGE = "Could not determine the content's encoding.";

    /**
     * Default constructor.
     */
    public EncodingDetectionFailException() {
        this(DEFAULT_MESSAGE, null);
    }

    /**
     * Constructor with message definition.
     * <p/>
     * @param message The message for the new exception.
     */
    public EncodingDetectionFailException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with cause definition.
     * <p/>
     * @param cause The cause for the new exception.
     */
    public EncodingDetectionFailException(final Throwable cause) {
        this(DEFAULT_MESSAGE, cause);
    }

    /**
     * Constructor with message and cause definition.
     * <p/>
     * @param message The message for the new exception.
     * @param cause   The cause for the new exception.
     */
    public EncodingDetectionFailException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
