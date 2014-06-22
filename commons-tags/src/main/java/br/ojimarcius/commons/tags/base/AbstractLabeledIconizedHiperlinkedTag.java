/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.ojimarcius.commons.tags.base;

import javax.servlet.jsp.JspException;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public abstract class AbstractLabeledIconizedHiperlinkedTag extends AbstractHtmlGeneratorTag implements LabeledTag, IconizedTag, HiperlinkedTag {

    private String label;
    private Boolean isLabelKey;
    private String url;
    private String icon;
    private Integer iconSize;

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

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public void setIcon(final String icon) {
        this.icon = icon;
    }

    @Override
    public Integer getIconSize(final Integer defaultValue) {
        return iconSize != null ? iconSize : defaultValue;
    }

    @Override
    public void setIconSize(final Integer iconSize) {
        this.iconSize = iconSize;
    }
}
