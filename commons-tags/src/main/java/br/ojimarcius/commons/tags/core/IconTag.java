/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.ojimarcius.commons.tags.core;

import br.ojimarcius.commons.tags.base.AbstractIconizedTag;
import br.ojimarcius.commons.tags.base.IconizedTag;
import java.io.IOException;
import javax.servlet.jsp.JspException;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class IconTag extends AbstractIconizedTag {

    @Override
    public void doTag() throws JspException, IOException {
        final String url = evaluateBody(); // evaluates the menuitemicon body
        IconizedTag parent = findParent(IconizedTag.class);
        parent.setIcon(url); // register the icon url on the parent
        if (getIconSize(null) != null) {
            parent.setIconSize(getIconSize(null)); // register the iconSize on the parent
        }
    }
}
