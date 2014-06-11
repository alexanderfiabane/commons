/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.msf.commons.tags.base;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface HtmlGeneratorTag extends JspTag {

    public String getId();

    public void setId(final String id);

    public Boolean isVisible(final Boolean defaultValue);

    public void setVisible(final Boolean visible);

    public CharSequence assembleHtml() throws JspException, IOException;

    public String getTagName();

    public void validateTagAttributes() throws JspException;
}
