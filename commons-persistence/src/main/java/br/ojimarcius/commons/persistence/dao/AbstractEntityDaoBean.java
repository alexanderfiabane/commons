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
package br.ojimarcius.commons.persistence.dao;

import br.ojimarcius.commons.persistence.model.Entity;
import br.ojimarcius.commons.util.ArgumentUtils;
import br.ojimarcius.commons.util.ArrayUtils;
import br.ojimarcius.commons.util.CollectionUtils;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * TODO
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 * @param <ID> The type of the Entity's id (Ex: Long, Integer, String, ...).
 * @param <T>  The Type of Entity handled by the DAO.
 */
public abstract class AbstractEntityDaoBean<ID extends Serializable & Comparable<ID>, T extends Entity<ID>> implements EntityDao<ID, T> {

    protected final Class<T> persistentClass;
    protected SessionFactory sessionFactory;

    public AbstractEntityDaoBean() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected final Class<T> getPersistentClass() {
        return this.persistentClass;
    }

    protected Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }
    
    @Override
    public void flush() {
        final Session session = getCurrentSession();
        session.flush();
        session.clear();
    }

    @Override
    public final DetachedCriteria createCriteria() {
        return DetachedCriteria.forClass(persistentClass);
    }

    @Override
    public final Collection<T> findByCriteria(final DetachedCriteria criteria, final Order... orders) {
        if (ArrayUtils.isNotEmpty(orders)) {
            for (Order order : orders) {
                criteria.addOrder(order);
            }
        }
        return criteria.getExecutableCriteria(getCurrentSession()).list();
    }

    @Override
    public T findById(final ID id) {
        ArgumentUtils.rejectIfNull(id);
        return (T) getCurrentSession().get(persistentClass, id);
    }

    @Override
    public Collection<T> findAll(final Order... orders) {
        return findByCriteria(createCriteria(), orders);
    }

    @Override
    public Collection<T> findByProperty(final String propertyName, final Object propertyValue, final Order... orders) {
        ArgumentUtils.rejectIfNull(propertyName);
        final DetachedCriteria dc = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.add((propertyValue == null) ? Restrictions.isNull(propertyName) : Restrictions.eq(propertyName, propertyValue));
        return findByCriteria(dc, orders);
    }

    @Override
    public Collection<T> findByProperties(final Map<String, Object> properties, final Order... orders) {
        ArgumentUtils.rejectIfEmptyOrNull(properties);
        final DetachedCriteria dc = createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        final Set<Map.Entry<String, Object>> props = properties.entrySet();
        for (Map.Entry<String, Object> entry : props) {
            final String propName = entry.getKey();
            final Object propValue = entry.getValue();
            dc.add((propValue == null) ? Restrictions.isNull(propName) : Restrictions.eq(propName, propValue));
        }
        return findByCriteria(dc, orders);
    }

    @Override
    public ID save(final T entity) {
        ArgumentUtils.rejectIfNull(entity);
        final Session session = getCurrentSession();
        final ID id = (ID) session.save(entity);
        return id;
    }

    @Override
    public ID saveOrUpdate(final T entity) {
        ArgumentUtils.rejectIfNull(entity);
        final Session session = getCurrentSession();
        session.saveOrUpdate(entity);
        return entity.getId();
    }

    @Override
    public void saveOrUpdateAll(final Collection<T> entities) {
        if (!CollectionUtils.isEmptyOrNull(entities)) {
            final Session session = getCurrentSession();
            int i = 0;
            for (T entity : entities) {
                session.saveOrUpdate(entity);
                // flush a batch of inserts and release memory, every 100 rows:
                if (i % 100 == 0) {
                    flush();
                }
            }
        }
    }

    @Override
    public void delete(final T entity) {
        ArgumentUtils.rejectIfNull(entity);
        final Session session = getCurrentSession();
        if (entity.getId() != null) {
            session.delete(entity);
            entity.setId(null);
        }
    }

    @Override
    public void deleteAll() {
        final StringBuilder builder = new StringBuilder();
        builder.append("delete from ").append(getPersistentClass().getName());
        Query q = getCurrentSession().createQuery(builder.toString());
        q.executeUpdate();
    }

    @Override
    public void deleteAll(final Collection<T> entities) {
        if (!CollectionUtils.isEmptyOrNull(entities)) {
            final Session session = getCurrentSession();
            int i = 0;
            for (T entity : entities) {
                session.delete(entity);
                // flush a batch of inserts and release memory, every 100 rows:
                if (i % 100 == 0) {
                    flush();
                }
            }
        }
    }

    @Override
    public T deleteById(final ID id) {
        ArgumentUtils.rejectIfNull(id);
        final Session session = getCurrentSession();
        final T entity = findById(id);
        session.evict(entity);
        if (entity != null) {
            session.delete(entity);
        }
        return entity;
    }
}
