/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.msf.commons.tags.base;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class AbstractLabeledTag extends AbstractHtmlGeneratorTag implements LabeledTag {

    private String label;
    private Boolean isLabelKey;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getResolvedLabel() throws JspException {
        return isLabelKey(Boolean.FALSE) ? resolveMessage(getLabel()) : getLabel();
    }

    @Override
    public void setLabel(final String label) {
        this.label = label;
    }

    @Override
    public Boolean isLabelKey(final Boolean defaultValue) {
        return isLabelKey != null ? isLabelKey : defaultValue;
    }

    @Override
    public void setIsLabelKey(final Boolean isLabelKey) {
        this.isLabelKey = isLabelKey;
    }
}
