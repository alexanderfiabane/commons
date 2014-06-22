/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.ojimarcius.commons.tags.label;

import br.ojimarcius.commons.text.EnhancedStringBuilder;
import br.ojimarcius.commons.tags.base.AbstractLabeledTag;
import br.ojimarcius.commons.tags.util.TagUtils;
import br.ojimarcius.commons.util.CharSequenceUtils;
import java.io.IOException;
import javax.servlet.jsp.JspException;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class LabelTag extends AbstractLabeledTag {

    private static final String MANDATORY_STAR_STYLE = "color: #880000;";
    private String forField;
    private String cssClass;
    private Boolean isMandatory = Boolean.FALSE;
    private Boolean colonAfter = Boolean.TRUE;
    private Boolean breakAfter = Boolean.TRUE;

    public String getFor() {
        return forField;
    }

    public void setFor(String forField) {
        this.forField = forField;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
    
    public Boolean isMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public Boolean isColonAfter() {
        return colonAfter;
    }

    public void setColonAfter(Boolean colonAfter) {
        this.colonAfter = colonAfter;
    }

    public Boolean isBreakAfter() {
        return breakAfter;
    }

    public void setBreakAfter(Boolean breakAfter) {
        this.breakAfter = breakAfter;
    }

    @Override
    public CharSequence assembleHtml() throws JspException, IOException {
        final EnhancedStringBuilder builder = new EnhancedStringBuilder();
        String body = evaluateBody();
        if (body != null) {body = body.trim();}
        builder.append("<label")
                .appendIfTrue(CharSequenceUtils.isNotBlank(getFor()), " ", TagUtils.getAttributeAssign("for", getFor()))
                .appendIfTrue(CharSequenceUtils.isNotBlank(getCssClass()), " ", TagUtils.getAttributeAssign("class", getCssClass()))
                .append(">")
                .append(isLabelKey(Boolean.FALSE), resolveMessage(getLabel()), getLabel())
                .appendIfTrue(isColonAfter(), ":")
                .appendIfTrue(isMandatory(), "<span ", TagUtils.getAttributeAssign("style", MANDATORY_STAR_STYLE), ">*</span>")
                .appendIfNotBlank(body)
                .append("</label>")
                .appendIfTrue(isBreakAfter(), "<br/>");
        return builder;
    }

    @Override
    public void doTag() throws JspException, IOException {
        evaluateBody();
        writeAssembledHtml(); // write generated html
    }
}
