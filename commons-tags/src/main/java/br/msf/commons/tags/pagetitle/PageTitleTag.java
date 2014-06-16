/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.msf.commons.tags.pagetitle;

import br.msf.commons.io.exception.RuntimeIOException;
import br.msf.commons.text.EnhancedStringBuilder;
import br.msf.commons.tags.base.AbstractLabeledIconizedTag;
import br.msf.commons.tags.util.TagUtils;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.CollectionUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PageTitleTag extends AbstractLabeledIconizedTag {

    private static final Properties PROPERTIES = loadProperties();
    private final Collection<BreadcrumbTag> steps = new ArrayList<BreadcrumbTag>();
    private String breadcrumbSeparator;
    private Boolean defaultIsLabelKey;

    public Boolean isDefaultIsLabelKey(final Boolean defaultValue) {
        return defaultIsLabelKey != null ? defaultIsLabelKey : defaultValue;
    }

    public void setDefaultIsLabelKey(final Boolean isBreadcrumbLabelKey) {
        this.defaultIsLabelKey = isBreadcrumbLabelKey;
    }

    public String getBreadcrumbSeparator(final String defaultValue) {
        return CharSequenceUtils.isNotBlank(breadcrumbSeparator) ? breadcrumbSeparator : defaultValue;
    }

    public void setBreadcrumbSeparator(final String separator) {
        this.breadcrumbSeparator = separator;
    }

    public void addStep(final BreadcrumbTag step) {
        steps.add(step);
    }

    @Override
    public void doTag() throws JspException, IOException {
        evaluateBody(); // proccess children
        writeAssembledHtml(); // write generated html
        getJspContext().setAttribute(PROPERTIES.getProperty("pageTitle.labelAttrName"), getResolvedLabel(), PageContext.REQUEST_SCOPE);
        if (CollectionUtils.isNotEmpty(steps)) {
            final String url = CollectionUtils.getLast(steps).getUrl();
            getJspContext().setAttribute(PROPERTIES.getProperty("pageTitle.previousPageUrlAttrName"), url, PageContext.REQUEST_SCOPE);
        }
    }

    @Override
    public CharSequence assembleHtml() throws JspException, IOException {
        final Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("id", getNullSafeId());
        params.put("label", getResolvedLabel());
        params.put("icon", getIcon());
        params.put("iconSize", getIconSize(48));
        params.put("breadcrumbs", assembleBreadCrumbs(steps));
        return TagUtils.toEnhancedStringBuilder(PROPERTIES.getProperty("pageTitle.html")).replaceParams(params).appendln();
    }

    public EnhancedStringBuilder assembleBreadCrumbs(final Collection<BreadcrumbTag> crumbs) throws JspException, IOException {
        final EnhancedStringBuilder builder = new EnhancedStringBuilder();
        int crumbIndex = 0;
        for (BreadcrumbTag crumb : crumbs) {
            if (crumb.isVisible(Boolean.TRUE)) {
                if (crumb.getId() == null) {
                    crumb.setId("Breadcrumb" + crumbIndex);
                }
                if (crumb.isLabelKey(null) == null) {
                    crumb.setIsLabelKey(isDefaultIsLabelKey(Boolean.FALSE));
                }
                if (crumb.getSeparator() == null) {
                    crumb.setSeparator(getBreadcrumbSeparator("&raquo;"));
                }
                builder.append(crumb.assembleHtml());
                crumbIndex++;
            }
        }
        return builder;
    }

    private static Properties loadProperties() {
        try {
            final Resource usr = new ClassPathResource("/PageTitle.properties");
            final Resource def = new ClassPathResource("/br/msf/commons/tags/pagetitle/PageTitle.properties");
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
