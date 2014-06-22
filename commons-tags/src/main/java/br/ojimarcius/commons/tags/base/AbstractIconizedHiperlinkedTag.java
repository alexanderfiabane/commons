/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.ojimarcius.commons.tags.base;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public abstract class AbstractIconizedHiperlinkedTag extends AbstractHtmlGeneratorTag implements IconizedTag, HiperlinkedTag {

    private String url;
    private String icon;
    private Integer iconSize;

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
