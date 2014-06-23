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
package br.ojimarcius.commons.persistence.model;

import br.ojimarcius.commons.util.ArgumentUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * TODO
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 * @param <ID> The type of the Entity's id (Ex: Long, Integer, String, ...).
 */
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable & Comparable<ID>> implements Entity<ID> {

    private static final long serialVersionUID = -225409368874436056L;
    /**
     * @serial The entity object ID, i.e., the primary key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private ID id;

    public AbstractEntity() {
    }

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(final ID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return hashCodeById(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return equalsById(this, obj);
    }

    @Override
    public int compareTo(Entity<ID> o) {
        ArgumentUtils.rejectIfNull(o);
        ArgumentUtils.rejectIfNotEquals(this.getClass(), o.getClass());
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + getId();
    }

    /**
     *
     * @param e1
     * @param e2
     * @return
     */
    public static boolean equalsById(final Entity e1, final Object obj2) {
        if (e1 == null || obj2 == null || e1.getClass() != obj2.getClass()) {
            return false;
        }
        final Entity e2 = (Entity) obj2;
        if (e1 == e2) {
            return true;
        }
        if (e1.getId() != e2.getId() && (e1.getId() == null || !e1.getId().equals(e2.getId()))) {
            return false;
        }
        return true;
    }

    public static int hashCodeById(final Entity e1) {
        int hash = 3;
        hash = 83 * hash + (e1.getId() != null ? e1.getId().hashCode() : 0);
        return hash;
    }
}
