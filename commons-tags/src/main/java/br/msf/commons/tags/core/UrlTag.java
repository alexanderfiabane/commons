/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.msf.commons.tags.core;

import br.msf.commons.tags.base.AbstractHtmlGeneratorTag;
import br.msf.commons.tags.base.HiperlinkedTag;
import java.io.IOException;
import javax.servlet.jsp.JspException;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class UrlTag extends AbstractHtmlGeneratorTag {

    @Override
    public void doTag() throws JspException, IOException {
        final String url = evaluateBody(); // evaluates the menuitemurl body
        findParent(HiperlinkedTag.class).setUrl(url); // register the url on the parent
    }
}
