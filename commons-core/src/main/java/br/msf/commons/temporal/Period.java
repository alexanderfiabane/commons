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
package br.msf.commons.temporal;

import java.io.Serializable;
import java.util.Calendar;

/**
 * A class that represents a time lapse, having a start date and an end date.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public interface Period extends Serializable, Comparable<Period> {

    /**
     * Returns the start of the period.
     *
     * @return The start of the period.
     */
    public Calendar getStart();

    /**
     * Sets the start of the period, in Calendar format.
     *
     * @param start The start of the period.
     */
    public void setStart(final Calendar start);

    /**
     * Returns the end of the period.
     *
     * @return The end of the period.
     */
    public Calendar getEnd();

    /**
     * Sets the end of the period, in Calendar format.
     *
     * @param end The end of the period.
     */
    public void setEnd(final Calendar end);
}
