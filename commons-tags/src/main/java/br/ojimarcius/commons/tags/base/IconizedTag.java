/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.ojimarcius.commons.tags.base;

import javax.servlet.jsp.tagext.JspTag;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface IconizedTag extends JspTag {

    public String getIcon();

    public void setIcon(final String icon);

    public Integer getIconSize(final Integer defaultValue);

    public void setIconSize(final Integer iconSize);
}
