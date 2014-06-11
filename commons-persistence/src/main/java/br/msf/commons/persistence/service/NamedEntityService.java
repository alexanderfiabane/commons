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
package br.msf.commons.persistence.service;

import br.msf.commons.persistence.model.NamedEntity;
import java.io.Serializable;
import java.util.Collection;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

/**
 * TODO
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 * @param <ID> The type of the Entity's id (Ex: Long, Integer, String, ...).
 * @param <T>  The Type of Entity handled by the Service.
 * @param <D>  The Type of DAO that do the database tasks for the Service.
 */
public interface NamedEntityService<ID extends Serializable & Comparable<ID>, T extends NamedEntity<ID>> extends EntityService<ID, T> {

    public Collection<T> findByName(final String name);

    public Collection<T> findByName(final String name, final MatchMode matchMode);

    public Collection<T> findByName(final String name, final MatchMode matchMode, final boolean caseSensitive);

    public Collection<T> findByName(final String name, final MatchMode matchMode, final boolean caseSensitive, final Order order);
}
