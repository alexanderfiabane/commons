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
package br.msf.commons.reflect;

/**
 * Classe que representa um par de "hooks" a ser EXECUTADO ao redor de uma invocação de método via reflection.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 * @param <T> Tipo de instância-alvo.
 */
public class MethodHook<T> {

    /**
     * Executa antes da execução do método-alvo.
     *
     * @param targetInstance Objeto-alvo.
     * @param args           Argumentos.
     */
    public void preHook(T targetInstance, Object[] args) {
    }

    /**
     * Executa após a execução do método-alvo.
     *
     * @param targetInstance Objeto-alvo.
     * @param args           Argumentos.
     */
    public void postHook(T targetInstance, Object[] args) {
    }
}
