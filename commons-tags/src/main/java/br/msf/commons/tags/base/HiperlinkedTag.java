/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.msf.commons.tags.base;

import javax.servlet.jsp.tagext.JspTag;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface HiperlinkedTag extends JspTag {

    public String getUrl();

    public void setUrl(final String url);
}
