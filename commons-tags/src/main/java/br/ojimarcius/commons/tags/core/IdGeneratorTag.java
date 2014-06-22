/*
 * commons-tags - Copyright (c) 2009-2012 MSF. All rights reserved.
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
package br.ojimarcius.commons.tags.core;

import br.ojimarcius.commons.tags.base.AbstractHtmlGeneratorTag;
import br.ojimarcius.commons.tags.util.TagUtils;
import br.ojimarcius.commons.text.EnhancedStringBuilder;
import br.ojimarcius.commons.util.CharSequenceUtils;
import java.io.IOException;
import javax.servlet.jsp.JspException;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class IdGeneratorTag extends AbstractHtmlGeneratorTag {
    private String defaultValue;
    private String prefix;
    private String sufix;
    public String getDefaultValue() {
        return defaultValue;
    }
    public void setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
    }
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
    public String getSufix() {
        return sufix;
    }
    public void setSufix(final String sufix) {
        this.sufix = sufix;
    }
    @Override
    public CharSequence assembleHtml() throws JspException, IOException {
        if (CharSequenceUtils.isNotBlank(getDefaultValue())) {
            return getDefaultValue();
        }
        final EnhancedStringBuilder builder = new EnhancedStringBuilder();
        builder.appendIfNotBlank(getPrefix()).
                append(TagUtils.getTimeStamp()).
                appendIfNotBlank(getSufix());
        return builder.toString();
    }
    @Override
    public void doTag() throws JspException, IOException {
        evaluateBody();
        writeAssembledHtml(); // write generated html
    }
}
