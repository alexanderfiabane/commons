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

import br.ojimarcius.commons.temporal.Period;
import br.ojimarcius.commons.util.CalendarUtils;
import java.util.Calendar;
import javax.persistence.Embeddable;

@Embeddable
public class PeriodJpa implements Period {

    protected Calendar start;
    protected Calendar end;

    public PeriodJpa() {
    }

    @Override
    public Calendar getStart() {
        return this.start;
    }

    @Override
    public void setStart(final Calendar start) {
        this.start = start;
    }

    @Override
    public Calendar getEnd() {
        return this.end;
    }

    @Override
    public void setEnd(final Calendar end) {
        this.end = end;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.start != null ? this.start.hashCode() : 0);
        hash = 97 * hash + (this.end != null ? this.end.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PeriodJpa other = (PeriodJpa) obj;
        if (this.start != other.getStart() && (this.start == null || !this.start.equals(other.getStart()))) {
            return false;
        }
        return this.end == other.getEnd() || (this.end != null && this.end.equals(other.getEnd()));
    }

    @Override
    public int compareTo(final Period another) {
        int compare = CalendarUtils.compare(this.start, another.getStart());
        if (compare == 0) {
            compare = CalendarUtils.compare(this.end, another.getEnd());
        }
        return compare;
    }
}
