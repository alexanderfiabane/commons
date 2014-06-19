/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.msf.commons.tags.pagetitle;

import br.msf.commons.io.exception.RuntimeIOException;
import br.msf.commons.tags.base.AbstractLabeledHiperlinkedTag;
import br.msf.commons.tags.util.TagUtils;
import java.io.IOException;
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
public class BreadcrumbTag extends AbstractLabeledHiperlinkedTag {

    private static final Properties PROPERTIES = loadProperties();
    private String cssClass;
    private String cssStyle;
    private String separator;

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    @Override
    public void doTag() throws JspException, IOException {
        evaluateBody(); // proccess children
        findParent(PageTitleTag.class).addStep(this); // register on parent. The parent will call assembleHtml().
    }

    @Override
    public CharSequence assembleHtml() throws JspException, IOException {
        final Map<String, CharSequence> params = new LinkedHashMap<String, CharSequence>();
        params.put("bc_id", getNullSafeId());
        params.put("bc_cssClass", getCssClass());
        params.put("bc_cssStyle", getCssStyle());
        params.put("bc_url", getUrl());
        params.put("bc_label", getResolvedLabel());
        params.put("bc_separator", getSeparator());

        return TagUtils.toEnhancedStringBuilder(PROPERTIES.getProperty("breadcrumb.html")).replaceParams(params).appendln();
    }

    private static Properties loadProperties() {
        try {
            final Resource usr = new ClassPathResource("/Breadcrumb.properties");
            final Resource def = new ClassPathResource("/br/msf/commons/tags/pagetitle/Breadcrumb.properties");
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
