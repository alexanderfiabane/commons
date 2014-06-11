/*
 * Copyright (C) 2014 Marcius da Silva da Fonseca.
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
package br.msf.commons.mocca.ajax;

import java.io.Serializable;

/**
 * Classe base para paginação ajax.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 * @param <T> Tipo de retorno do search().
 */
public abstract class AbstractAjaxFinder<T extends Serializable> extends AbstractAjaxService implements AjaxFinder<T> {
}
