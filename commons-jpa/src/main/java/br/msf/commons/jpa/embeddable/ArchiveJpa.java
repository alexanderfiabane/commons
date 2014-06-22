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
package br.msf.commons.jpa.embeddable;

import br.ojimarcius.commons.io.Archive;
import br.ojimarcius.commons.util.ArrayUtils;
import br.ojimarcius.commons.util.IOUtils;
import java.util.Arrays;
import javax.persistence.Embeddable;

/**
 * A basic implementation of a File.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
@Embeddable
public class ArchiveJpa implements Archive {

    protected String name;
    protected String mimeType;
    protected byte[] content;

    public ArchiveJpa() {
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    public void setContent(final byte[] content) {
        this.content = content;
    }

    @Override
    public int getSize() {
        return ArrayUtils.getSize(this.content);
    }

    @Override
    public boolean isEmpty() {
        return ArrayUtils.isEmptyOrNull(this.content);
    }

    @Override
    public String getExtension() {
        return IOUtils.getExtension(this.name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArchiveJpa other = (ArchiveJpa) obj;
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
