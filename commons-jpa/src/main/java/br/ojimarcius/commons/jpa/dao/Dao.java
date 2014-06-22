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
package br.ojimarcius.commons.jpa.dao;

import br.ojimarcius.commons.jpa.Entity;
import java.io.Serializable;

/**
 * TODO
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 * @param <ID> The type of the Entity's id (Ex: Long, Integer, String, ...).
 * @param <T>  The Type of Entity handled by the DAO.
 */
public interface Dao<ID extends Serializable & Comparable<ID>, T extends Entity<ID>> {

    public Class<T> getPersistentClass();
    
    public T find(final ID id);
    
    public Long count();

    public T save(final T entity);

    public void save(final T... entities);
    
    public void delete(final T entity);

    public T delete(final ID id);
}
