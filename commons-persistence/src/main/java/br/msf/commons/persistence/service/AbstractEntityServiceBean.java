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

import br.msf.commons.persistence.dao.EntityDao;
import br.msf.commons.persistence.model.Entity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 * @param <ID> The type of the Entity's id (Ex: Long, Integer, String, ...).
 * @param <T>  The Type of Entity handled by the Service.
 * @param <D>  The Type of DAO that do the database tasks for the Service.
 */
@Transactional
public abstract class AbstractEntityServiceBean<ID extends Serializable & Comparable<ID>, T extends Entity<ID>> implements EntityService<ID, T> {

    private EntityDao<ID, T> dao;

    protected <D extends EntityDao<ID, T>> D getDao() {
        return (D) dao;
    }

    protected <D extends EntityDao<ID, T>> void setDao(final D dao) {
        this.dao = dao;
    }

    protected void flush() {
        getDao().flush();
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(final ID id) {
        return getDao().findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<T> findAll() {
        return getDao().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<T> findByProperty(final String propertyName, final Object propertyValue) {
        return getDao().findByProperty(propertyName, propertyValue);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<T> findByProperties(final Map<String, Object> properties) {
        return getDao().findByProperties(properties);
    }

    @Override
    @Transactional(readOnly = false)
    public ID save(final T entity) {
        return getDao().save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public ID saveOrUpdate(final T entity) {
        return getDao().saveOrUpdate(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveOrUpdateAll(final Collection<T> entities) {
        getDao().saveOrUpdateAll(entities);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(final T entity) {
        getDao().delete(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAll() {
        getDao().deleteAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAll(final Collection<T> entities) {
        getDao().deleteAll(entities);
    }

    @Override
    @Transactional(readOnly = false)
    public T deleteById(final ID id) {
        return getDao().deleteById(id);
    }
}
