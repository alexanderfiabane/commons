/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.msf.commons.tags.base;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface LabeledTag extends JspTag {

    public String getLabel();

    public String getResolvedLabel() throws JspException;

    public void setLabel(final String label);

    public Boolean isLabelKey(final Boolean defaultValue);

    public void setIsLabelKey(final Boolean isLabelKey);
}
