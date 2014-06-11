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
package br.msf.commons.persistence.service.report;

import br.msf.commons.persistence.model.Entity;
import br.msf.commons.util.ArrayUtils;
import br.msf.commons.util.CollectionUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * TODO
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 * @param <T> The Type of Entity handled by the Service.
 */
public abstract class AbstractReportBean<E extends Entity> implements EntityReport<E> {

    private Map<String, Object> params;
    private Collection<E> data;

    public AbstractReportBean() {
        this.params = new HashMap<String, Object>();
    }

    protected abstract InputStream getJasperStream();

    @Override
    public void putParam(final String key, final Object value) {
        this.params.put(key, value);
    }

    @Override
    public void putParams(final Map<String, Object> params) {
        this.params.putAll(params);
    }

    @Override
    public void setReportData(final Collection<E> data) {
        this.data = data;
    }

    @Override
    public byte[] generateReport() throws Exception {
        if (CollectionUtils.isNotEmpty(data)) {
            return JasperRunManager.runReportToPdf(getJasperStream(), params, new JRBeanCollectionDataSource(data));
        }
        return null;
    }

    @Override
    public boolean generateReport(final File file) throws Exception {
        byte[] bytes = generateReport();
        if (ArrayUtils.isEmptyOrNull(bytes)) {
            return false;
        }
        OutputStream outputStream = null;
        try {
            if (!file.isFile()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            return true;
        } catch (Exception ex2) {
            return false;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
