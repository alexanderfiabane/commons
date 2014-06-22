/*
 * Copyright (C) 2013 Marcius da Silva da Fonseca.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA
 */
package br.ojimarcius.commons.text;

import br.ojimarcius.commons.util.CharSequenceUtils;
import br.ojimarcius.commons.math.exception.RuntimeParseException;
import br.ojimarcius.commons.util.CalendarUtils;
import br.ojimarcius.commons.util.CollectionUtils;
import br.ojimarcius.commons.util.LocaleUtils;
import br.ojimarcius.commons.util.NumberUtils;
import com.ibm.icu.text.RuleBasedNumberFormat;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CustomDateFormat implements Serializable {

    private static final long serialVersionUID = -2226609673818448572L;
    public static final String DEFAULT_DATE_TIME_SEPARATOR = " ";
    private static final String FIELD_DELIMITER_START = "{";
    private static final String FIELD_DELIMITER_END = "}";
    private static final String SPELLED_PREFIX = "@";
    private static final String REFERENCE_PREFIX = "#";
    private String pattern;
    private boolean ordinalOnFirstDayOfMonth;
    private Locale locale;

    public CustomDateFormat(final String pattern) {
        this(pattern, null, false);
    }

    public CustomDateFormat(final String pattern, final Locale locale) {
        this(pattern, locale, false);
    }

    public CustomDateFormat(final String pattern, final boolean ordinalOnFirstDayOfMonth) {
        this(pattern, null, false);
    }

    public CustomDateFormat(final String pattern, final Locale locale, final boolean ordinalOnFirstDayOfMonth) {
        this.pattern = pattern;
        this.locale = LocaleUtils.getNullSafeLocale(locale);
        this.ordinalOnFirstDayOfMonth = ordinalOnFirstDayOfMonth;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    public boolean isOrdinalOnFirstDayOfMonth() {
        return ordinalOnFirstDayOfMonth;
    }

    public void setOrdinalOnFirstDayOfMonth(final boolean ordinalOnFirstDayOfMonth) {
        this.ordinalOnFirstDayOfMonth = ordinalOnFirstDayOfMonth;
    }

    public String format(final Date date) {
        return format((Object) date);
    }

    public String format(final Calendar date) {
        return format((Object) date);
    }

    public String format(final Object date) {
        if (date == null) {
            return null;
        }
        final EnhancedStringBuilder builder = new EnhancedStringBuilder(getPattern());
        final Collection<String> fieldNames = CharSequenceUtils.listParams(builder, FIELD_DELIMITER_START, FIELD_DELIMITER_END);
        final String ret;
        if (CollectionUtils.isEmptyOrNull(fieldNames)) {
            // works as a SimpleDateFormat
            ret = (new SimpleDateFormat(pattern, LocaleUtils.getNullSafeLocale(locale))).format(CalendarUtils.castToDate(date));
        } else {
            Map<String, String> fieldMap = fetchFieldValues(date, fieldNames);
            ret = builder.replace(FIELD_DELIMITER_START, FIELD_DELIMITER_END, fieldMap).toString();
        }
        return ret.toLowerCase();
    }

    public Date parse(final CharSequence date) {
        final DateFormat df = new SimpleDateFormat(convertPattern(pattern), LocaleUtils.getNullSafeLocale(locale));
        try {
            return df.parse(date.toString());
        } catch (ParseException ex) {
            throw new RuntimeParseException(ex);
        }
    }

    public Calendar parseCalendar(final CharSequence date) {
        return CalendarUtils.castToCalendar(parse(date));
    }

    private Map<String, String> fetchFieldValues(final Object date, final Collection<String> fieldNames) {
        final Map<String, String> map = new HashMap<String, String>(fieldNames.size());
        for (final String field : fieldNames) {
            if (field.startsWith(SPELLED_PREFIX)) {
                map.put(field, getFieldValue(date, field.substring(SPELLED_PREFIX.length()), true, ordinalOnFirstDayOfMonth));
            } else if (field.startsWith(REFERENCE_PREFIX)) {
                map.put(field, getPalavraReferente(date, field.charAt(REFERENCE_PREFIX.length())));
            } else {
                map.put(field, getFieldValue(date, field, false, ordinalOnFirstDayOfMonth));
            }
        }
        return map;
    }

    private String getFieldValue(final Object date, final String fieldName, final boolean porExtenso, final boolean ordinal) {
        final String ret = (new SimpleDateFormat(fieldName, LocaleUtils.getNullSafeLocale(locale))).format(CalendarUtils.castToDate(date));
        if (ordinal && (fieldName.equals("d") || fieldName.equals("dd")) && CalendarUtils.isFirstDayOfMonth(date)) {
            if (porExtenso) {
                return NumberSpeller.getInstance(locale).spellOrdinal(1);
            } else {
                RuleBasedNumberFormat fmt = new RuleBasedNumberFormat(locale, RuleBasedNumberFormat.ORDINAL);
                return fmt.format(1);
            }
        }

        if (porExtenso && NumberUtils.isNumber(ret)) {
            return NumberSpeller.getInstance(locale).spellCardinal(Integer.parseInt(ret));
        }
        return ret;
    }

    private String getPalavraReferente(final Object date, final char fieldName) {
        final boolean plural;
        if (fieldName == 'y') {
            plural = (Integer.parseInt(getFieldValue(date, "yyyy", false, false)) > 1);
        } else {
            plural = (Integer.parseInt(getFieldValue(date, Character.toString(fieldName), false, false)) > 1);
        }
        return getPalavraReferente(fieldName, plural);
    }

    private String getPalavraReferente(final char fieldName, final boolean plural) {
        switch (fieldName) {
            case 'd':
            case 'D':
            case 'F':
            case 'E':
                return plural ? "dias" : "dia";
            case 'M':
                return plural ? "meses" : "mês";
            case 'y':
                return plural ? "anos" : "ano";
            case 'h':
            case 'H':
            case 'k':
            case 'K':
                return plural ? "horas" : "hora";
            case 'm':
                return plural ? "minutos" : "minuto";
            case 's':
                return plural ? "segundos" : "segundo";
            case 'S':
                return plural ? "milissegundos" : "milissegundo";
            case 'w':
            case 'W':
                return plural ? "semanas" : "semana";
            default:
                throw new IllegalArgumentException("pattern {" + REFERENCE_PREFIX + fieldName + "} not supported.");
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CustomDateFormat other = (CustomDateFormat) obj;

        if ((this.pattern == null) ? (other.pattern != null) : !this.pattern.equals(other.pattern)) {
            return false;
        }
        return this.ordinalOnFirstDayOfMonth == other.ordinalOnFirstDayOfMonth;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.pattern != null ? this.pattern.hashCode() : 0);
        hash = 53 * hash + (this.ordinalOnFirstDayOfMonth ? 1 : 0);
        return hash;
    }

    private String convertPattern(final String customPattern) {
        final Collection<String> fields = CharSequenceUtils.listParams(customPattern, FIELD_DELIMITER_START, FIELD_DELIMITER_END);
        if (CollectionUtils.isEmptyOrNull(fields)) {
            return customPattern;
        }
        final EnhancedStringBuilder builder = new EnhancedStringBuilder();
        builder.append("'").append(customPattern.replace("'", "''")).append("'");
        builder.replacePlain("{", "'{").
                replacePlain("}", "}'");

        if (builder.startsWith("''{")) {
            builder.replace(0, 2, "");
        }
        if (builder.endsWith("}''")) {
            int len = builder.length();
            builder.replace(len - 2, len, "");
        }

        /* simple chars that dont need a ' around them */
        builder.replacePlain("' '", " ").
                replacePlain("'-'", "-").
                replacePlain("'/'", "/");
        for (String field : fields) {
            if (field.startsWith(SPELLED_PREFIX)) {
                throw new RuntimeParseException("Parser doesn't supports formats with spelled fields.");
            } else if (field.startsWith(REFERENCE_PREFIX)) {
                throw new RuntimeParseException("Parser doesn't supports formats with referring fields.");
            } else {
                builder.replacePlain(FIELD_DELIMITER_START + field + FIELD_DELIMITER_END, field);
            }
        }
        return builder.toString();
    }
}
