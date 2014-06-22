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
package br.ojimarcius.commons.persistence.springframework.validation;

import org.springframework.validation.Errors;

/**
 * TODO : Describe.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 */
public interface Validator<T> extends org.springframework.validation.Validator {

    public static final String ERROR_INVALID = "error.invalid";
    public static final String ERROR_REQUIRED = "error.required";
    public static final String ERROR_EXISTS = "error.exists";
    public static final String ERROR_MIN_LENGTH = "error.minlength";
    public static final String ERROR_MAX_LENGTH = "error.maxlength";
    public static final String ERROR_MIN_VALUE = "error.minvalue";
    public static final String ERROR_MAX_VALUE = "error.maxvalue";

    public void validateCommand(final T target, final Errors errors);
}
