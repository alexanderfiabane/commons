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
package br.msf.commons.mail;

import java.io.Serializable;
import java.util.Map;

/**
 * A class that represents a e-mail to be sent to someone.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public interface Email extends Serializable {

    /**
     * Returns the message sender.
     *
     * @return The message sender.
     */
    public String getFrom();

    /**
     * Returns the message subject.
     *
     * @return The message subject.
     */
    public String getSubject();

    /**
     * Returns the message primary destination.
     *
     * @return The message primary destination.
     */
    public String[] getTo();

    /**
     * Returns the message secondary destinations.
     *
     * @return The message secondary destinations.
     */
    public String[] getCc();

    /**
     * Returns the mimetype of the message.
     *
     * @return The mimetype of the message.
     */
    public String getContentType();

    /**
     * Returns the raw content, exactly as it was created (no param replacements).
     *
     * @return The message contents with no param replacements.
     */
    public String getRawContent();

    /**
     * Returns the message content processed, exactly as it will be received by the receiver.
     *
     * @param params A map containing all the params necessary to process the message.
     * @return The message contents already processed.
     */
    public String getContent(final Map<String, Object> params);
}
