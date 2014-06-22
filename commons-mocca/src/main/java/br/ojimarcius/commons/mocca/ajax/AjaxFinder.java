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
package br.ojimarcius.commons.mocca.ajax;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Interface para Localizador ajax.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 * @param <T> Tipo de retorno do search().
 */
public interface AjaxFinder<T extends Serializable> {

    /**
     * Efetua a pesquisa, trazendo os itens.
     *
     * @param firstResult O índice do primeiro resultado a ser buscado.
     * @param maxResults  O máximo de resultados por página.
     * @param params      A coleção de parametros da pesquisa.
     * @return O resultado da pesquisa.
     * @throws java.lang.Exception Caso ocorra algo inesperado.
     */
    public abstract Collection<T> search(final int firstResult, final int maxResults, final Map<String, String> params) throws Exception;
}
