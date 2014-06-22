/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.ojimarcius.commons.tags.menu;

import br.ojimarcius.commons.io.exception.RuntimeIOException;
import br.ojimarcius.commons.text.EnhancedStringBuilder;
import br.ojimarcius.commons.tags.base.AbstractHtmlGeneratorTag;
import br.ojimarcius.commons.tags.util.TagUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.jsp.JspException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class MenuTag extends AbstractHtmlGeneratorTag {

    private static final Properties PROPERTIES = loadProperties();
    private final Collection<MenuCell> itens = new ArrayList<MenuCell>();
    private Integer itensPerRow = 6;
    private Boolean disabled = Boolean.FALSE;
    private Boolean defaultIsLabelKey;
    private Integer defaultIconSize;
    private String defaultMiClass;
    private String defaultMiDisabledClass;
    private String defaultLabelClass;
    private String defaultLabelDisabledClass;
    private String defaultIconClass;
    private String defaultIconDisabledClass;
    // <editor-fold defaultstate="collapsed" desc="Gettes and setters">

    public Integer getItensPerRow() {
        return itensPerRow;
    }

    public void setItensPerRow(final Integer itensPerRow) {
        this.itensPerRow = itensPerRow;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(final Boolean disabled) {
        this.disabled = disabled;
    }
    // children defaults

    public Boolean isDefaultIsLabelKey() {
        return defaultIsLabelKey;
    }

    public void setDefaultIsLabelKey(final Boolean isLabelKey) {
        this.defaultIsLabelKey = isLabelKey;
    }

    public Integer getDefaultIconSize() {
        return defaultIconSize;
    }

    public void setDefaultIconSize(final Integer iconSize) {
        this.defaultIconSize = iconSize;
    }

    public String getDefaultMiClass() {
        return defaultMiClass;
    }

    public void setDefaultMiClass(final String miClass) {
        this.defaultMiClass = miClass;
    }

    public String getDefaultMiDisabledClass() {
        return defaultMiDisabledClass;
    }

    public void setDefaultMiDisabledClass(final String miDisabledClass) {
        this.defaultMiDisabledClass = miDisabledClass;
    }

    public String getDefaultLabelClass() {
        return defaultLabelClass;
    }

    public void setDefaultLabelClass(final String labelClass) {
        this.defaultLabelClass = labelClass;
    }

    public String getDefaultLabelDisabledClass() {
        return defaultLabelDisabledClass;
    }

    public void setDefaultLabelDisabledClass(final String labelDisabledClass) {
        this.defaultLabelDisabledClass = labelDisabledClass;
    }

    public String getDefaultIconClass() {
        return defaultIconClass;
    }

    public void setDefaultIconClass(final String iconClass) {
        this.defaultIconClass = iconClass;
    }

    public String getDefaultIconDisabledClass() {
        return defaultIconDisabledClass;
    }

    public void setDefaultIconDisabledClass(final String iconDisabledClass) {
        this.defaultIconDisabledClass = iconDisabledClass;
    }

    public void addItem(final MenuCell item) {
        itens.add(item);
    }
    // </editor-fold>

    @Override
    public void doTag() throws JspException, IOException {
        evaluateBody(); // proccess children
        writeAssembledHtml(); // write generated html
    }

    @Override
    public CharSequence assembleHtml() throws JspException, IOException {
        if (!isVisible(Boolean.TRUE)) {
            return null;
        }
        final Collection<EnhancedStringBuilder> assembledRows = assembleRows(assembleColumns(itens));
        final EnhancedStringBuilder builder = new EnhancedStringBuilder();
        for (CharSequence row : assembledRows) {
            builder.appendln(row);
        }
        final Map<String, CharSequence> params = new LinkedHashMap<String, CharSequence>();
        params.put("m_id", getNullSafeId());
        params.put("m_menuRows", builder);
        return TagUtils.toEnhancedStringBuilder(PROPERTIES.getProperty("menu.html")).replaceParams(params).appendln();
    }

    private void adjustDefaults(final MenuItemTag t) {
        // if the menu itself is disabled, all menuitems must be disabled.
        if (isDisabled()) {
            t.setDisabled(isDisabled());
        }
        // if menuitem doesn't defines isLabelKey, we use the menu's isLabelKey definition.
        if (t.isLabelKey(null) == null) {
            t.setIsLabelKey(isDefaultIsLabelKey());
        }
        // if menuitem doesn't defines xxx, we use the menu's xxx definition.
        if (t.getIconSize(null) == null) {
            t.setIconSize(getDefaultIconSize());
        }
        // if menuitem doesn't defines miClass, we use the menu's miClass definition.
        if (t.getMiClass(null) == null) {
            t.setMiClass(getDefaultMiClass());
        }
        // if menuitem doesn't defines miDisabledClass, we use the menu's miDisabledClass definition.
        if (t.getMiDisabledClass(null) == null) {
            t.setMiDisabledClass(getDefaultMiDisabledClass());
        }
        // if menuitem doesn't defines labelClass, we use the menu's labelClass definition.
        if (t.getLabelClass(null) == null) {
            t.setLabelClass(getDefaultLabelClass());
        }
        // if menuitem doesn't defines labelDisabledClass, we use the menu's labelDisabledClass definition.
        if (t.getLabelDisabledClass(null) == null) {
            t.setLabelDisabledClass(getDefaultLabelDisabledClass());
        }
        // if menuitem doesn't defines iconClass, we use the menu's iconClass definition.
        if (t.getIconClass(null) == null) {
            t.setIconClass(getDefaultIconClass());
        }
        // if menuitem doesn't defines iconDisabledClass, we use the menu's iconDisabledClass definition.
        if (t.getIconDisabledClass(null) == null) {
            t.setIconDisabledClass(getDefaultIconDisabledClass());
        }
    }

    private Collection<EnhancedStringBuilder> assembleColumns(final Collection<MenuCell> cells) throws JspException, IOException {
        final Collection<EnhancedStringBuilder> assembledColumns = new ArrayList<EnhancedStringBuilder>(cells.size());
        int itemIndex = 0;
        for (MenuCell cell : cells) {
            if (cell.isVisible(Boolean.TRUE)) {
                if (cell.getId() == null) {
                    cell.setId("MenuItem" + itemIndex);
                }
                if (isMenuItem(cell)) {
                    adjustDefaults((MenuItemTag) cell);
                }
                final Map<String, CharSequence> params = new LinkedHashMap<String, CharSequence>();
                params.put("m_idColumn", "MenuColumn" + itemIndex);
                params.put("m_menuItem", cell.assembleHtml());
                assembledColumns.add(TagUtils.toEnhancedStringBuilder(PROPERTIES.getProperty("menuColumn.html")).replaceParams(params).appendln());
                itemIndex++;
            }
        }
        return assembledColumns;
    }

    private Collection<EnhancedStringBuilder> assembleRows(final Collection<EnhancedStringBuilder> assembledColumns) throws JspException, IOException {
        final Collection<EnhancedStringBuilder> assembledRows = new ArrayList<EnhancedStringBuilder>(assembledColumns.size() / getItensPerRow() + 1);
        final EnhancedStringBuilder builder = new EnhancedStringBuilder();
        int columnCount = 0;
        int rowIndex = 0;
        for (CharSequence column : assembledColumns) {
            builder.appendln(column);
            columnCount++;
            if (columnCount >= getItensPerRow()) {
                final Map<String, CharSequence> params = new LinkedHashMap<String, CharSequence>();
                params.put("m_idRow", "MenuRow" + rowIndex);
                params.put("m_menuColumns", builder);
                assembledRows.add(TagUtils.toEnhancedStringBuilder(PROPERTIES.getProperty("menuRow.html")).replaceParams(params).appendln());
                builder.clear();
                columnCount = 0;
                rowIndex++;
            }
        }
        if (builder.isNotBlank()) {
            final Map<String, CharSequence> params = new LinkedHashMap<String, CharSequence>();
            params.put("m_idRow", "MenuRow" + rowIndex);
            params.put("m_menuColumns", builder);
            assembledRows.add(TagUtils.toEnhancedStringBuilder(PROPERTIES.getProperty("menuRow.html")).replaceParams(params).appendln());
        }
        return assembledRows;
    }

    private boolean isMenuItem(final MenuCell cell) {
        return (cell != null && MenuItemTag.class.isAssignableFrom(cell.getClass()));
    }

    private static Properties loadProperties() {
        try {
            final Resource usr = new ClassPathResource("/Menu.properties");
            final Resource def = new ClassPathResource("/br/msf/commons/tags/menu/Menu.properties");
            final Properties defProps = new Properties();
            final Properties props;
            defProps.load(def.getInputStream());
            if (usr.exists()) {
                props = new Properties(defProps);
                props.load(usr.getInputStream());
            } else {
                props = defProps;
            }
            return props;
        } catch (IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }
}
