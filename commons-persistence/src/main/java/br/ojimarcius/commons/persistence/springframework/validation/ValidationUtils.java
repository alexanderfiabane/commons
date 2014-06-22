/*
 * commons-persistence - Copyright (c) 2009-2013 MSF. All rights reserved.
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
package br.ojimarcius.commons.persistence.springframework.validation;

import br.ojimarcius.commons.persistence.model.Entity;
import br.ojimarcius.commons.util.CalendarUtils;
import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.util.CollectionUtils;
import br.ojimarcius.commons.util.DateUtils;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.TemporalType;

/**
 * TODO : Describe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public abstract class ValidationUtils {

    public static boolean isNotNullCharSequence(final Object value) {
        return value != null && CharSequence.class.isAssignableFrom(value.getClass());
    }

    public static boolean isNotNullCollection(final Object value) {
        return value != null && Collection.class.isAssignableFrom(value.getClass());
    }

    public static boolean isNotNullEntity(final Object value) {
        return value != null && Entity.class.isAssignableFrom(value.getClass());
    }

    public static boolean isNotNullNumber(final Object value) {
        return value != null && Number.class.isAssignableFrom(value.getClass());
    }

    public static boolean isNotNullDate(final Object value) {
        return value != null && Date.class.isAssignableFrom(value.getClass());
    }

    public static boolean isNotNullCalendar(final Object value) {
        return value != null && Calendar.class.isAssignableFrom(value.getClass());
    }

    public static boolean isNullOrEmpty(final Object value) {
        if (value == null) {
            return true;
        } else if (isNotNullCharSequence(value)) {
            return CharSequenceUtils.isBlankOrNull((CharSequence) value);
        } else if (isNotNullCollection(value)) {
            return CollectionUtils.isEmptyOrNull((Collection) value);
        }
        return false;
    }

    public static String getFullPath(final String pathPrefix, final String fieldName) {
        if (CharSequenceUtils.isBlankOrNull(pathPrefix)) {
            return fieldName;
        } else {
            return pathPrefix + "." + fieldName;
        }
    }

    public static Calendar toCalendar(String value, TemporalType type) {
        if (type == null || TemporalType.DATE.equals(type)) {
            return CalendarUtils.parse(value, "yyyy-mm-dd");
        } else if (TemporalType.TIME.equals(type)) {
            return CalendarUtils.parse(value, "HH-mm-ss");
        } else {
            return CalendarUtils.parse(value, "yyyy-mm-dd HH-mm-ss");
        }
    }

    public static Date toDate(String value, TemporalType type) {
        if (type == null || TemporalType.DATE.equals(type)) {
            return DateUtils.parse(value, "yyyy-mm-dd");
        } else if (TemporalType.TIME.equals(type)) {
            return DateUtils.parse(value, "HH-mm-ss");
        } else {
            return DateUtils.parse(value, "yyyy-mm-dd HH-mm-ss");
        }
    }

    public static Number toNumber(String value) {
        return new BigDecimal(value);
    }
}
