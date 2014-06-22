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
package br.ojimarcius.commons.persistence.service.report;

import br.ojimarcius.commons.persistence.model.Entity;
import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * TODO
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 * @param <T> The Type of Entity handled by the Service.
 */
public interface EntityReport<E extends Entity> {

    public void putParam(final String key, final Object value);

    public void putParams(final Map<String, Object> params);

    public void setReportData(final Collection<E> data);

    public byte[] generateReport() throws Exception;

    public boolean generateReport(final File file) throws Exception;
}
