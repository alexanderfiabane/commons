/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.ojimarcius.commons.tags.menu;

import br.ojimarcius.commons.io.exception.RuntimeIOException;
import br.ojimarcius.commons.tags.base.AbstractLabeledIconizedHiperlinkedTag;
import br.ojimarcius.commons.tags.core.UrlTag;
import br.ojimarcius.commons.tags.util.TagUtils;
import br.ojimarcius.commons.text.EnhancedStringBuilder;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.NumberUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.JspTag;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class MenuItemTag extends AbstractLabeledIconizedHiperlinkedTag implements MenuCell {

    private static final Properties PROPERTIES = loadProperties();
    private Boolean disabled;
    private String miClass;
    private String miDisabledClass;
    private String labelClass;
    private String labelDisabledClass;
    private String iconClass;
    private String iconDisabledClass;

    public Boolean isDisabled(final Boolean defaultValue) {
        return disabled != null ? disabled : defaultValue;
    }

    public void setDisabled(final Boolean disabled) {
        this.disabled = disabled;
    }

    public String getMiClass(final String defaultValue) {
        return miClass != null ? miClass : defaultValue;
    }

    public void setMiClass(final String miClass) {
        this.miClass = miClass;
    }

    public String getMiDisabledClass(final String defaultValue) {
        return miDisabledClass != null ? miDisabledClass : defaultValue;
    }

    public void setMiDisabledClass(final String miDisabledClass) {
        this.miDisabledClass = miDisabledClass;
    }

    public String getLabelClass(final String defaultValue) {
        return labelClass != null ? labelClass : defaultValue;
    }

    public void setLabelClass(final String labelClass) {
        this.labelClass = labelClass;
    }

    public String getLabelDisabledClass(final String defaultValue) {
        return labelDisabledClass != null ? labelDisabledClass : defaultValue;
    }

    public void setLabelDisabledClass(final String labelDisabledClass) {
        this.labelDisabledClass = labelDisabledClass;
    }

    public String getIconClass(final String defaultValue) {
        return iconClass != null ? iconClass : defaultValue;
    }

    public void setIconClass(final String iconClass) {
        this.iconClass = iconClass;
    }

    public String getIconDisabledClass(final String defaultValue) {
        return iconDisabledClass != null ? iconDisabledClass : defaultValue;
    }

    public void setIconDisabledClass(final String iconDisabledClass) {
        this.iconDisabledClass = iconDisabledClass;
    }

    @Override
    public void doTag() throws JspException, IOException {
        if (!isVisible(Boolean.TRUE)) {
            return;
        }
        evaluateBody(); // proccess children
        validateTagAttributes();
        JspTag parent = findNearestParent(MenuTag.class, MenuCustomCellTag.class);
        if (parent != null && TagUtils.isMenuTag(parent)) {
            ((MenuTag) parent).addItem(this); // register on parent. The parent will call assembleHtml().
        } else {
            // no MenuTag parent. Just render the menuitem.
            writeAssembledHtml();
        }
    }

    @Override
    public CharSequence assembleHtml() throws JspException {
        if (!isVisible(Boolean.TRUE)) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mi_id", getNullSafeId());
        params.put("mi_label", getResolvedLabel());
        params.put("mi_iconSize", getIconSize(32));
        params.put("mi_iconContainerSize", NumberUtils.toInteger(getIconSize(32) * 1.5d));
        params.put("mi_icon", getIcon());
        params.put("mi_onclick", isDisabled(Boolean.FALSE) ? "" : "location.href = '" + getUrl() + "';");
        params.put("mi_miClass", isDisabled(Boolean.FALSE) ? getMiDisabledClass("miDisabled") : getMiClass("mi"));
        params.put("mi_iconClass", isDisabled(Boolean.FALSE) ? getIconDisabledClass("miIconDisabled") : getIconClass("miIcon"));
        params.put("mi_labelClass", isDisabled(Boolean.FALSE) ? getLabelDisabledClass("miLabelDisabled") : getLabelClass("miLabel"));

        final EnhancedStringBuilder builder = new EnhancedStringBuilder(PROPERTIES.getProperty("menuItem.html"));
        return builder.replaceParams(params);
    }

    @Override
    public void validateTagAttributes() throws JspException {
        if (CharSequenceUtils.isBlankOrNull(getUrl())) {
            throw new JspTagException(getTagName() + "'s <" + getTagName(UrlTag.class) + " /> child element is missing.");
        }
    }

    private static Properties loadProperties() {
        try {
            final Resource usr = new ClassPathResource("/MenuItem.properties");
            final Resource def = new ClassPathResource("/br/ojimarcius/commons/tags/menu/MenuItem.properties");
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
