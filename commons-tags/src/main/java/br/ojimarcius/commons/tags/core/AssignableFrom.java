/*
 * commons-tags - Copyright (c) 2009-2013 Marcius da Silva da Fonseca. All rights reserved.
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

import br.ojimarcius.commons.tags.util.TagUtils;
import br.ojimarcius.commons.util.CharSequenceUtils;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class AssignableFrom extends SimpleTagSupport {
    private String leftClass;
    private String rightClass;
    private String var;
    private String scope;
    public String getLeftClass() {
        return leftClass;
    }
    public void setLeftClass(final String leftClass) {
        this.leftClass = leftClass;
    }
    public String getRightClass() {
        return rightClass;
    }
    public void setRightClass(final String rightClass) {
        this.rightClass = rightClass;
    }
    public String getVar() {
        return var;
    }
    public void setVar(final String var) {
        this.var = var;
    }
    public String getScope(final String defaultValue) {
        return CharSequenceUtils.isNotBlank(scope) ? scope : defaultValue;
    }
    public void setScope(final String scope) {
        this.scope = scope;
    }
    @Override
    public void doTag() throws JspException, IOException {
        Boolean result = false;
        if (CharSequenceUtils.isNotBlank(leftClass) && CharSequenceUtils.isNotBlank(rightClass)) {
            final Class lc = getClassForName(leftClass);
            final Class rc = getClassForName(rightClass);
            result = lc.isAssignableFrom(rc);
        }
        getJspContext().setAttribute(getVar(), result, TagUtils.getScope(getScope(TagUtils.SCOPE_PAGE)));
    }
    private Class getClassForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(className + "  not found!");
        }
    }
}
