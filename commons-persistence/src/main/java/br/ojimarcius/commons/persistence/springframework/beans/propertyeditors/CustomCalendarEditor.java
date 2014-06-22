/*
 * commons-persistence - Copyright (c) 2009-2012 MSF. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package br.ojimarcius.commons.persistence.springframework.beans.propertyeditors;

import br.ojimarcius.commons.util.CharSequenceUtils;
import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Property editor for
 * <code>java.util.Date</code>,
 * supporting a custom
 * <code>java.text.DateFormat</code>.
 *
 * <p>This is not meant to be used as system PropertyEditor but rather
 * as locale-specific date editor within custom controller code,
 * parsing user-entered number strings into Date properties of beans
 * and rendering them in the UI form.
 *
 * <p>In web MVC code, this editor will typically be registered with
 * <code>binder.registerCustomEditor</code> calls in a custom
 * <code>initBinder</code> method.
 *
 * @author Juergen Hoeller
 * @since 28.04.2003
 * @see java.util.Date
 * @see java.text.DateFormat
 * @see org.springframework.validation.DataBinder#registerCustomEditor
 * @see org.springframework.web.servlet.mvc.BaseCommandController#initBinder
 */
public class CustomCalendarEditor extends PropertyEditorSupport {

    private final DateFormat dateFormat;
    private final boolean allowEmpty;

    /**
     * Create a new CustomDateEditor instance, using the given DateFormat
     * for parsing and rendering.
     * <p>The "allowEmpty" parameter states if an empty String should
     * be allowed for parsing, i.e. get interpreted as null value.
     * Otherwise, an IllegalArgumentException gets thrown in that case.
     * <p/>
     * @param dateFormat DateFormat to use for parsing and rendering
     * @param allowEmpty if empty strings should be allowed
     */
    public CustomCalendarEditor(DateFormat dateFormat, boolean allowEmpty) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && CharSequenceUtils.isBlankOrNull(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else {
            try {
                Calendar c = new GregorianCalendar();
                c.setTime(this.dateFormat.parse(text));
                setValue(c);
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        Calendar value = (Calendar) getValue();
        return (value != null ? this.dateFormat.format(value.getTime()) : "");
    }
}
