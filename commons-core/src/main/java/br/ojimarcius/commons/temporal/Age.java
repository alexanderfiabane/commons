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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package br.ojimarcius.commons.temporal;

import br.ojimarcius.commons.text.EnhancedStringBuilder;
import br.ojimarcius.commons.util.CollectionUtils;
import br.ojimarcius.commons.util.IOUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 *
 * @author vbox
 */
public class Age implements Comparable<Age> {

    protected static final int YEARS = 0;
    protected static final int MONTHS = 1;
    protected static final int DAYS = 2;
    protected static final int HOURS = 3;
    protected static final int MINS = 4;
    protected static final int SECS = 5;
    protected static final int MSECS = 6;

    private final int[] fields;

    public Age() {
        this.fields = new int[]{0, 0, 0, 0, 0, 0, 0};
    }

    public int getYears() {
        return fields[YEARS];
    }

    public void setYears(int years) {
        this.fields[YEARS] = years;
    }

    public int getMonths() {
        return fields[MONTHS];
    }

    public void setMonths(int months) {
        this.fields[MONTHS] = months;
    }

    public int getDays() {
        return fields[DAYS];
    }

    public void setDays(int days) {
        this.fields[DAYS] = days;
    }

    public int getHours() {
        return fields[HOURS];
    }

    public void setHours(int hours) {
        this.fields[HOURS] = hours;
    }

    public int getMinutes() {
        return fields[MINS];
    }

    public void setMinutes(int minutes) {
        this.fields[MINS] = minutes;
    }

    public int getSeconds() {
        return fields[SECS];
    }

    public void setSeconds(int seconds) {
        this.fields[SECS] = seconds;
    }

    public int getMilliseconds() {
        return fields[MSECS];
    }

    public void setMilliseconds(int milliseconds) {
        this.fields[MSECS] = milliseconds;
    }

    @Override
    public int compareTo(final Age anotherAge) {
        for (int i = 0; i < this.fields.length; i++) {
            int comp = this.fields[i] - anotherAge.fields[i];
            if (comp != 0) {
                return comp;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        final Collection<CharSequence> texts = new ArrayList<CharSequence>(7);
        for (int i = 0; i <= MSECS; i++) {
            final int value = this.fields[i];
            if (value > 0) {
                final EnhancedStringBuilder builder = new EnhancedStringBuilder();
                builder.append(value, " ").append(value == 1, getTextSingular(i), getTextPlural(i));
                texts.add(builder);
            }
        }
        return CollectionUtils.isNotEmpty(texts) ? CollectionUtils.toString(texts, getSeparator(), getLastSeparator()) : "0";
    }

    protected static ResourceBundle getBundle() {
        return IOUtils.readBundle(Age.class);
    }

    protected static String getTextSingular(final int i) {
        return getBundle().getString("qualifier.singular." + i);
    }

    protected static String getTextPlural(final int i) {
        return getBundle().getString("qualifier.plural." + i);
    }

    protected static String getSeparator() {
        return getBundle().getString("separator");
    }

    protected static String getLastSeparator() {
        return getBundle().getString("lastSeparator");
    }

    public static void main(String[] args) {
        Age age = new Age();
        age.setYears(1);
        age.setMonths(2);
        age.setDays(3);
        age.setHours(4);
        age.setMinutes(5);
        age.setSeconds(6);
        age.setMilliseconds(7);
        System.out.println(age.toString());
    }
}
