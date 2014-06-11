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

import br.msf.commons.text.EnhancedStringBuilder;
import java.util.Arrays;
import java.util.Map;

/**
 * A simple implementation of the {@link Email} class.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public class SimpleEmail implements Email {

    private static final long serialVersionUID = -5068251843723597368L;
    /**
     * The e-mail sender.
     */
    private String from;
    /**
     * The message subject.
     */
    private String subject;
    /**
     * The message primary destination.
     */
    private String[] to;
    /**
     * The message primary destination.
     */
    private String[] cc;
    /**
     * The message raw content, to be processed before sending.
     */
    private String rawContent;
    /**
     * The message mimetype. Default is "text/html".
     */
    private String contentType = "text/html";

    // <editor-fold desc="Getters and Setters">
    @Override
    public String getFrom() {
        return from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    @Override
    public String[] getTo() {
        return to;
    }

    public void setTo(final String[] to) {
        this.to = to;
    }

    @Override
    public String[] getCc() {
        return cc;
    }

    public void setCc(final String[] cc) {
        this.cc = cc;
    }

    @Override
    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(final String rawContent) {
        this.rawContent = rawContent;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }
    // </editor-fold>

    @Override
    public String getContent(final Map<String, Object> params) {
        return (new EnhancedStringBuilder(getRawContent())).replaceParams(params).toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.from != null ? this.from.hashCode() : 0);
        hash = 97 * hash + (this.subject != null ? this.subject.hashCode() : 0);
        hash = 97 * hash + Arrays.deepHashCode(this.to);
        hash = 97 * hash + Arrays.deepHashCode(this.cc);
        hash = 97 * hash + (this.rawContent != null ? this.rawContent.hashCode() : 0);
        hash = 97 * hash + (this.contentType != null ? this.contentType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SimpleEmail other = (SimpleEmail) obj;
        if ((this.from == null) ? (other.from != null) : !this.from.equals(other.from)) {
            return false;
        }
        if ((this.subject == null) ? (other.subject != null) : !this.subject.equals(other.subject)) {
            return false;
        }
        if (!Arrays.deepEquals(this.to, other.to)) {
            return false;
        }
        if (!Arrays.deepEquals(this.cc, other.cc)) {
            return false;
        }
        if ((this.rawContent == null) ? (other.rawContent != null) : !this.rawContent.equals(other.rawContent)) {
            return false;
        }
        if ((this.contentType == null) ? (other.contentType != null) : !this.contentType.equals(other.contentType)) {
            return false;
        }
        return true;
    }
}
