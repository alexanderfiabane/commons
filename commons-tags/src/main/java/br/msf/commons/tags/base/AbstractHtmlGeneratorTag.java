/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.msf.commons.tags.base;

import br.msf.commons.tags.util.TagUtils;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.ReflectionUtils;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public abstract class AbstractHtmlGeneratorTag extends SimpleTagSupport implements HtmlGeneratorTag {

    private String id;
    private Boolean visible;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public Boolean isVisible(final Boolean defaultValue) {
        return (visible != null) ? visible : defaultValue;
    }

    @Override
    public void setVisible(final Boolean visible) {
        this.visible = visible;
    }

    @Override
    public CharSequence assembleHtml() throws JspException, IOException {
        return evaluateBody();
    }

    @Override
    public String getTagName() {
        return TagUtils.getTagName(this);
    }

    @Override
    public void validateTagAttributes() throws JspException {
    }

    protected final PageContext getPageContext() {
        return (PageContext) getJspContext();
    }

    protected final Locale getLocale() {
        return TagUtils.getLocale(getPageContext());
    }

    protected final String getNullSafeId() {
        return hasId() ? id : TagUtils.generateId(this);
    }

    protected final Boolean hasId() {
        return CharSequenceUtils.isNotBlank(id);
    }

    protected final String generateId() {
        return TagUtils.generateId(this);
    }

    protected final String evaluateBody() throws JspException, IOException {
        JspFragment body = getJspBody();
        if (body != null) {
            final StringWriter writer = new StringWriter();
            body.invoke(writer);
            return TagUtils.removeComments(writer.toString()).toString();
        }
        return null;
    }

    protected final void writeAssembledHtml() throws JspException, IOException {
        final CharSequence assembled = TagUtils.removeComments(assembleHtml());
        if (isVisible(Boolean.TRUE) && CharSequenceUtils.isNotEmpty(assembled)) {
            JspWriter writer = getJspContext().getOut();
            writer.print(assembled);
        }
    }

    protected final String resolveMessage(final String key) throws JspException {
        return resolveMessage(key, null, false);
    }

    protected final String resolveMessage(final String key, final String defaultValue) throws JspException {
        return resolveMessage(key, defaultValue, false);
    }

    protected final String resolveMessage(final String key, final boolean mandatory) throws JspException {
        return resolveMessage(key, null, mandatory);
    }

    protected final String resolveMessage(final String key, final String defaultValue, final boolean mandatory) throws JspException {
        return TagUtils.resolveMessage(this, getPageContext(), key, defaultValue, mandatory);
    }

    protected final String resolveUrl(final String url, final String context) throws JspException {
        return TagUtils.resolveUrl(url, context, getPageContext());
    }

    protected final <T extends JspTag> T findParent(final Class<T> expectedParentClass) throws JspTagException {
        return findParent(true, expectedParentClass);
    }

    protected final <T extends JspTag> T findParent(final boolean mandatory, final Class<T> expectedParentClass) throws JspTagException {
        final JspTag parent = findAncestorWithClass(this, expectedParentClass);
        if (parent == null && mandatory) {
            throw new JspTagException("<" + getTagName() + "/> must be a child of <" + getTagName(expectedParentClass) + "/>");
        }
        return (T) parent;
    }

    protected final JspTag findNearestParent(final Class<? extends JspTag>... expectedParentClass) throws JspTagException {
        return findNearestParent(true, expectedParentClass);
    }

    protected final JspTag findNearestParent(final boolean mandatory, final Class<? extends JspTag>... expectedParentClass) throws JspTagException {
        JspTag parent = TagUtils.getParent(this);
        while (parent != null) {
            for (Class<? extends JspTag> clazz : expectedParentClass) {
                if (clazz.isAssignableFrom(parent.getClass())) {
                    return parent;
                }
            }
            parent = TagUtils.getParent(parent);
        }

        if (mandatory) {
            throw new JspTagException("<" + getTagName() + "/> parent not found.");
        }
        return null;
    }

    protected static String getTagName(final Class<? extends JspTag> tagClass) {
        try {
            return ((AbstractHtmlGeneratorTag) ReflectionUtils.newInstanceOf(tagClass)).getTagName();
        } catch (Exception ex) {
            return TagUtils.getTagName(tagClass);
        }
    }
}
