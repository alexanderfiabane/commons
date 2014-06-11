/*
 * commons-persistence - Copyright (c) 2009-2012 MSF. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package br.msf.commons.persistence.springframework.beans.propertyeditors;

import br.msf.commons.persistence.model.Entity;
import br.msf.commons.persistence.service.EntityService;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.NumberUtils;
import java.beans.PropertyEditorSupport;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class CustomEntityEditor<T extends Entity<Long>> extends PropertyEditorSupport  {

    private final EntityService<Long, T> service;
    private final Boolean allowEmpty;

    public CustomEntityEditor(final EntityService<Long, T> service) {
        this(service, true);
    }

    public CustomEntityEditor(final EntityService<Long, T> service, final Boolean allowEmpty) {
        this.service = service;
        this.allowEmpty = true;
    }

    @Override
    public String getAsText() {
        final T value = (T) getValue();
        return (value != null && value.getId() != null)? value.getId().toString() : "";
    }
    @Override
    public void setAsText(final String text) {
        final T newValue;
        if (CharSequenceUtils.isBlank(text) && !allowEmpty) {
            throw new IllegalArgumentException("Entity is null!");
        } else if (CharSequenceUtils.isBlank(text)) {
            newValue = null;
        } else {
            newValue = service.findById(NumberUtils.parseLong(text));
        }
        setValue(newValue);
    }
}
