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
package br.msf.commons.persistence.dao;

import br.msf.commons.persistence.model.Entity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

/**
 * TODO
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 * @param <ID> The type of the Entity's id (Ex: Long, Integer, String, ...).
 * @param <T>  The Type of Entity handled by the DAO.
 */
public interface EntityDao<ID extends Serializable & Comparable<ID>, T extends Entity<ID>> {

    public DetachedCriteria createCriteria();

    public void setSessionFactory(final SessionFactory sessionFactory);

    public void flush();
    
    public T findById(final ID id);

    public Collection<T> findAll(final Order... orders);

    public Collection<T> findByProperty(final String propertyName, final Object propertyValue, final Order... orders);

    public Collection<T> findByProperties(final Map<String, Object> properties, final Order... orders);

    public Collection<T> findByCriteria(final DetachedCriteria criteria, final Order... orders);

    public ID save(final T entity);

    public ID saveOrUpdate(final T entity);

    public void saveOrUpdateAll(final Collection<T> entities);

    public void delete(final T entity);

    public void deleteAll();

    public void deleteAll(final Collection<T> entities);

    public T deleteById(final ID id);
}
