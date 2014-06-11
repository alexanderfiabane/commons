/*
 * commons-tags - Copyright (C) 2009-2013 MSF. All rights reserved.
 */
package br.msf.commons.tags.util;

import br.msf.commons.text.EnhancedStringBuilder;
import br.msf.commons.tags.menu.MenuCustomCellTag;
import br.msf.commons.tags.core.IconTag;
import br.msf.commons.tags.menu.MenuItemTag;
import br.msf.commons.tags.core.UrlTag;
import br.msf.commons.tags.menu.MenuTag;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.DateUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.taglibs.standard.tag.common.core.UrlSupport;
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.servlet.support.JspAwareRequestContext;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
//import org.springframework.web.util.ExpressionEvaluationUtils;

/**
 * TODO : Descreva a classe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public abstract class TagUtils extends org.springframework.web.util.TagUtils {

    protected static final String REQUEST_CONTEXT_PAGE_ATTRIBUTE = RequestContextAwareTag.REQUEST_CONTEXT_PAGE_ATTRIBUTE;
    protected static final String UNDEFINED_KEY = "???";
    protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");

    public static String generateId(final JspTag tag) {
        return getTagName(tag.getClass()) + "_" + getTimeStamp();
    }

    public static String getTimeStamp() {
        return DATE_FORMAT.format(DateUtils.now());
    }

    public static String getTagName(final JspTag tag) {
        return getTagName(tag.getClass());
    }

    public static String getTagName(final Class<? extends JspTag> tagClass) {
        EnhancedStringBuilder builder = new EnhancedStringBuilder(tagClass.getSimpleName());
        builder.deletePattern("Tag$").toLowerCase(0, 1);
        return builder.toString();
    }

    public static JspTag getParent(final JspTag tag) {
        return SimpleTagSupport.findAncestorWithClass(tag, JspTag.class);
    }

    /**
     *
     * @param attributeName
     * @param attributeValue
     * @return
     */
    public static String getAttributeAssign(final String attributeName, final Object attributeValue) {
        String value = attributeValue != null ? attributeValue.toString() : null;
        if (CharSequenceUtils.isNotBlank(value)) {
            return attributeName + "=\"" + value + "\" ";
        }
        return null;
    }

    public static RequestContext getRequestContext(final PageContext pageContext) {
        RequestContext requestContext = (RequestContext) pageContext.getAttribute(REQUEST_CONTEXT_PAGE_ATTRIBUTE);
        try {
            if (requestContext == null) {
                requestContext = new JspAwareRequestContext(pageContext);
                pageContext.setAttribute(REQUEST_CONTEXT_PAGE_ATTRIBUTE, requestContext);
            }
        } catch (Exception ex) {
            requestContext = null;
        }
        return requestContext;
    }

    public static Locale getLocale(final PageContext pageContext) {
        RequestContext context = getRequestContext(pageContext);
        return (context != null) ? getRequestContext(pageContext).getLocale() : Locale.getDefault();
    }

    /**
     * Use the current RequestContext's application context as MessageSource.
     */
    public static MessageSource getMessageSource(final PageContext pageContext) {
        return getRequestContext(pageContext) != null ? getRequestContext(pageContext).getMessageSource() : null;
    }

    public static String resolveMessage(final JspTag from, final PageContext pageContext, final String key, final String defaultValue) throws JspException {
        return resolveMessage(from, pageContext, key, defaultValue, false);
    }

    public static String resolveMessage(final JspTag from, final PageContext pageContext, final String key, final String defaultValue, final boolean mandatory) throws JspException {
        if (CharSequenceUtils.isBlankOrNull(key)) {
            throw new JspTagException("Missing property key.");
        }
        String message = resolveMessageBundle(from, pageContext, key);
        if (message == null) {
            message = resolveMessageSpring(pageContext, key);
        }
        if (message == null && defaultValue != null) {
            message = defaultValue;
        }
        if (message == null && mandatory) {
            throw new JspTagException("Could not find property {" + key + "}");
        }
        return message != null ? message : UNDEFINED_KEY + key + UNDEFINED_KEY;
    }

    /**
     *
     * @param from
     * @param pageContext
     * @param key
     * @return
     */
    @SuppressWarnings("empty-statement")
    private static String resolveMessageBundle(final JspTag from, final PageContext pageContext, final String key) {
        String messageKey = key;
        // tries using the mechanism used by <fmt:message />
        String prefix = null;
        final LocalizationContext locCtxt;
        final JspTag t = SimpleTag.class.isAssignableFrom(from.getClass())
                         ? SimpleTagSupport.findAncestorWithClass(from, BundleSupport.class) : TagSupport.findAncestorWithClass((Tag) from, BundleSupport.class);
        if (t != null) {
            // use resource bundle from parent <bundle> tag
            BundleSupport parent = (BundleSupport) t;
            locCtxt = parent.getLocalizationContext();
            prefix = parent.getPrefix();
        } else {
            locCtxt = BundleSupport.getLocalizationContext(pageContext);
        }
        if (locCtxt != null) {
            ResourceBundle bundle = locCtxt.getResourceBundle();
            if (bundle != null) {
                try {
                    // prepend 'prefix' attribute from parent bundle
                    if (prefix != null) {
                        messageKey = prefix + messageKey;
                    }
                    return bundle.getString(messageKey);
                } catch (MissingResourceException mre) {
                    ; // do nothing. we will return null.
                }
            }
        }
        return null;
    }

    /**
     * Resolve the specified message into a concrete message String.
     * The returned message String should be unescaped.
     */
    @SuppressWarnings("empty-statement")
    private static String resolveMessageSpring(final PageContext pageContext, final String key) throws JspException {
        final MessageSource messageSource = getMessageSource(pageContext);
        if (messageSource != null) {
            try {
                return messageSource.getMessage(key, new Object[]{}, getRequestContext(pageContext).getLocale());
//                final String resolvedCode = ExpressionEvaluationUtils.evaluateString("code", key, pageContext);
//                if (resolvedCode != null) {
//                    return messageSource.getMessage(resolvedCode, new Object[]{}, getRequestContext(pageContext).getLocale());
//                }
            } catch (NoSuchMessageException ex) {
                ; // do nothing. we will return null.
            }
        }
        return null;
    }

    public static String resolveUrl(final String url, final String context, final PageContext pageContext) throws JspException {
        return UrlSupport.resolveUrl(url, context, pageContext);
    }

    public static CharSequence removeComments(final CharSequence content) {
        if (CharSequenceUtils.isBlankOrNull(content)) {
            return content;
        }
        final EnhancedStringBuilder builder = new EnhancedStringBuilder(content);
        builder.deletePattern(Pattern.compile("<!--(.*?)-->|<%--(.*?)--%>", Pattern.DOTALL));
        return builder;
    }
    // menu

    public static boolean isMenuTag(final JspTag tag) {
        return (tag != null) ? MenuTag.class.isAssignableFrom(tag.getClass()) : false;
    }

    public static boolean isMenuItemTag(final JspTag tag) {
        return (tag != null) ? MenuItemTag.class.isAssignableFrom(tag.getClass()) : false;
    }

    public static boolean isMenuCustomCellTag(final JspTag tag) {
        return (tag != null) ? MenuCustomCellTag.class.isAssignableFrom(tag.getClass()) : false;
    }

    public static boolean isMenuItemIconTag(final JspTag tag) {
        return (tag != null) ? IconTag.class.isAssignableFrom(tag.getClass()) : false;
    }

    public static boolean isMenuItemUrlTag(final JspTag tag) {
        return (tag != null) ? UrlTag.class.isAssignableFrom(tag.getClass()) : false;
    }

    public static EnhancedStringBuilder toEnhancedStringBuilder(final CharSequence sequence) {
        if (sequence != null && EnhancedStringBuilder.class.isAssignableFrom(sequence.getClass())) {
            return (EnhancedStringBuilder) sequence;
        } else if (sequence != null) {
            return new EnhancedStringBuilder(sequence);
        }
        return new EnhancedStringBuilder();
    }
}
