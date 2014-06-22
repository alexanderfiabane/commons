/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.ojimarcius.commons.tags.message;

import br.ojimarcius.commons.tags.base.AbstractHtmlGeneratorTag;
import br.ojimarcius.commons.tags.util.TagUtils;
import br.ojimarcius.commons.util.CharSequenceUtils;
import java.io.IOException;
import javax.servlet.jsp.JspException;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public class MessageTag extends AbstractHtmlGeneratorTag {

    private String var;
    private String scope;
    private String key;
    private String defaultValue;

    public String getVar() {
        return var;
    }

    public void setVar(final String var) {
        this.var = var;
    }

    public String getScope(final String defaultValue) {
        return CharSequenceUtils.isNotBlank(scope) ? scope : defaultValue;
    }

    public void setScope(final String scope) {
        this.scope = scope;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public void doTag() throws JspException, IOException {
        evaluateBody();
        if (CharSequenceUtils.isNotBlank(getVar())) {
            getJspContext().setAttribute(getVar().trim(), assembleHtml(), TagUtils.getScope(getScope("page")));
        } else {
            writeAssembledHtml(); // write generated html
        }
    }

    @Override
    public CharSequence assembleHtml() throws JspException {
        return resolveMessage(getKey(), getDefaultValue());
    }
}
