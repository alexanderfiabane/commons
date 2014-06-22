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
package br.ojimarcius.commons.io;

import br.ojimarcius.commons.util.ArrayUtils;
import br.ojimarcius.commons.util.IOUtils;
import java.util.Arrays;

/**
 * A basic implementation of a File.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public final class SimpleArchive implements Archive {

    private static final long serialVersionUID = -465013000799282885L;
    /**
     * The file name.
     */
    protected String name;
    /**
     * The file content's mime-type.
     */
    protected String mimeType;
    /**
     * The file content.
     */
    protected byte[] content;

    /**
     * Default constructor.
     */
    public SimpleArchive() {
        this(null, null, null);
    }

    /**
     * Creates an unnamed file with the given content.
     *
     * @param content The new file content.
     */
    public SimpleArchive(final byte[] content) {
        this(null, null, content);
    }

    /**
     * Creates a named file with the given content.
     *
     * @param name    The new file name.
     * @param content The new file content.
     */
    public SimpleArchive(final String name, final byte[] content) {
        this(name, null, content);
    }

    /**
     * Creates a named file with the given mime type and content.
     *
     * @param name     The new file name.
     * @param mimeType The new file mime type.
     * @param content  The new file content.
     */
    public SimpleArchive(final String name, final String mimeType, final byte[] content) {
        this.name = name;
        this.content = content;
        this.mimeType = mimeType;
    }

    /**
     * Returns the file name.
     *
     * @return The file name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the file name.
     *
     * @param name The file name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns the mime type.
     *
     * @return The mime type.
     */
    @Override
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the mime type.
     *
     * @param mimeType The mime type.
     */
    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Returns the content, in bytes.
     *
     * @return The content, in bytes.
     */
    @Override
    public byte[] getContent() {
        return content;
    }

    /**
     * Sets the content, in bytes.
     *
     * @param content The content, in bytes.
     */
    public void setContent(final byte[] content) {
        this.content = content;
    }

    /**
     * Returns the size of this file contents, in bytes.
     *
     * @return The size of this file contents, in bytes.
     */
    @Override
    public int getSize() {
        return ArrayUtils.getSize(this.content);
    }

    /**
     * Returns true if the content is null or empty.
     *
     * @return true if the content is null or empty. false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return ArrayUtils.isEmptyOrNull(this.content);
    }

    /**
     * Extracts the filename extension.
     *
     * @return The extracted extension, or null if none.
     */
    @Override
    public String getExtension() {
        return  IOUtils.getExtension(this.name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SimpleArchive other = (SimpleArchive) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return Arrays.equals(this.content, other.content);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + Arrays.hashCode(this.content);
        return hash;
    }

    @Override
    public String toString() {
        return getName();
    }
}
