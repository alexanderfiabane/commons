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
import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * TODO
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 * @param <ID> The type of the Entity's id (Ex: Long, Integer, String, ...).
 * @param <T>  The Type of Entity handled by the DAO.
 */
public abstract class ContainerManagedDao<ID extends Serializable & Comparable<ID>, T extends Entity<ID>> implements Dao<ID, T> {

    protected final Class<T> persistentClass;

    public ContainerManagedDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    
    protected abstract EntityManager getEntityManager();

    @Override
    public T find(final ID id) {
        return getEntityManager().find(persistentClass, id);
    }

    @Override
    public Long count() {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> query = cb.createQuery(Long.class);
        final Root<T> entity = query.from(persistentClass);
        query.select(cb.count(entity));

        final TypedQuery<Long> tq = getEntityManager().createQuery(query);
        return tq.getSingleResult();
    }

    @Override
    public T save(final T entity) {
        T managed;
        try {
            getEntityManager().persist(entity);
            managed = entity;
        } catch (EntityExistsException ex) {
            managed = getEntityManager().merge(entity);
        }
        return managed;
    }

    @Override
    public void save(final T... entities) {
        final int len = entities == null ? 0 : entities.length;
        if (len < 1) {
            return;
        }
        for (int i = 0; i < len; i++) {
            final T entity = entities[i];
            if (entity != null) {
                try {
                    getEntityManager().persist(entity);
                } catch (EntityExistsException ex) {
                    getEntityManager().merge(entity);
                }
            }
            if ((i % 1000) == 0) {
                flushAndClean();
            }
        }
        flushAndClean();
    }

    @Override
    public void delete(final T entity) {
        try {
            getEntityManager().remove(entity);
        } catch (IllegalArgumentException ex) {
            // if detached
            delete(entity.getId());
        }
    }

    @Override
    public T delete(final ID id) {
        final T tmp = find(id);
        if (tmp != null) {
            getEntityManager().remove(tmp);
        }
        return tmp;
    }

    @Override
    public Class<T> getPersistentClass() {
        return this.persistentClass;
    }

    protected void flushAndClean() {
        getEntityManager().flush();
        getEntityManager().clear();
    }
}
