/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.ojimarcius.commons.tags.menu;

import br.ojimarcius.commons.tags.base.AbstractHtmlGeneratorTag;
import java.io.IOException;
import javax.servlet.jsp.JspException;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class MenuCustomCellTag extends AbstractHtmlGeneratorTag implements MenuCell {

    @Override
    public void doTag() throws JspException, IOException {
        findParent(MenuTag.class).addItem(this);
    }
}
