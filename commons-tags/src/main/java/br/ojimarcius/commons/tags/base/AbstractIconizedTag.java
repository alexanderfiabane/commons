/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.ojimarcius.commons.tags.base;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public abstract class AbstractIconizedTag extends AbstractHtmlGeneratorTag implements IconizedTag {

    private String icon;
    private Integer iconSize;

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
