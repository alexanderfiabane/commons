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

import br.msf.commons.persistence.model.Entity;
import br.msf.commons.persistence.service.EntityService;
import br.msf.commons.text.EnhancedStringBuilder;
import br.msf.commons.util.ArgumentUtils;
import br.msf.commons.util.CalendarUtils;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.CollectionUtils;
import br.msf.commons.util.DateUtils;
import br.msf.commons.util.LocaleUtils;
import br.msf.commons.util.NumberUtils;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

/**
 * Classe base para paginação ajax.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 */
public abstract class AbstractAjaxService {

    protected static final Logger LOGGER = Logger.getLogger(AbstractAjaxService.class.getName());
    protected static final String[] TRUE_VALUES = {"true", "on", "yes", "checked", "sim", "y", "s", "1"};
    protected MessageSource messageSource;
    protected Locale locale;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Indica se o mapa de parâmetros possui algum item.
     *
     * @param params O mapa a ser avaliado.
     * @return true se este mapa possuir algum item. false caso contrário.
     */
    protected static boolean hasParams(final Map<String, String> params) {
        return CollectionUtils.isNotEmpty(params);
    }

    /**
     * Indica se o parâmetro de dado nome (key) existe no mapa e não é vazio.
     *
     * @param key    O nome do parâmetro.
     * @param params O mapa a ser avaliado.
     * @return true se existe um parametro não-vazio com o nome dado no mapa. false caso contrário.
     */
    protected static boolean hasParam(final String key, final Map<String, String> params) {
        return CharSequenceUtils.isNotBlank(getParam(key, params));
    }

    /**
     * Retorna o valor que está registrado para o nome de parâmetro dado.
     * <p/>
     * Pode retornar null caso este não exista.
     *
     * @param key    O nome do parâmetro.
     * @param params O mapa a ser avaliado.
     * @return O valor que está registrado para o nome de parâmetro dado.
     */
    protected static String getParam(final String key, final Map<String, String> params) {
        if (CollectionUtils.isNotEmpty(params)) {
            final String val = params.get(key);
            return val != null ? val.trim() : val;
        }
        return null;
    }

    /**
     * Retorna um {@link AbstractAjaxService.Order Order} que representa a ordenação da tabela.
     *
     * @param params mapa de parametros que conterá os parametros de ordenação.
     * @return O {@link AbstractAjaxService.Order Order} representando a ordenação da tabela,
     *         ou null se esta não estiver ordenada.
     */
    protected static Order getOrder(final Map<String, String> params) {
        if (hasParam("orderBy", params) && hasParam("orderMode", params)) {
            return new Order(getParam("orderBy", params), getParam("orderMode", params));
        }
        return null;
    }

    /**
     * Retorna um {@link org.hibernate.criterion.Order Order} que representa a ordenação da tabela.
     *
     * @param params mapa de parametros que conterá os parametros de ordenação.
     * @return O {@link org.hibernate.criterion.Order Order} representando a ordenação da tabela, ou null se esta não estiver ordenada.
     */
    protected static org.hibernate.criterion.Order getHibernateOrder(final Map<String, String> params) {
        final Order order = getOrder(params);
        return order != null ? order.toHibernateOrder() : null;
    }

    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE MÉTODO NÃO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S get(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return get(targetClass, key, params, null, null);
    }

    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE MÉTODO NÃO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S get(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return get(targetClass, key, params, pattern, null);
    }

    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE MÉTODO NÃO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S get(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        return parseValue(targetClass, getParam(key, params), pattern, locale, false);
    }

    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE MÉTODO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S getNullsafe(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return getNullsafe(targetClass, key, params, null, null);
    }

    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE MÉTODO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S getNullsafe(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return getNullsafe(targetClass, key, params, pattern, null);
    }

    /**
     * Efetua o parse de um parametro para um dado tipo.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE MÉTODO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do retorno.
     * @param targetClass Classe do tipo de Item do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado convertido para o tipo desejado.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S getNullsafe(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        return parseValue(targetClass, getParam(key, params), pattern, locale, true);
    }

    /**
     * Efetua o parse de um parametro para uma entidade.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE MÉTODO NÃO É NULL SAFE.</span>
     *
     * @param <E>         Tipo de entidade do retorno.
     * @param targetClass Classe do tipo de entidade do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param service     O service a ser usado na consulta.
     * @param joins       Joins a serem efetuados na consulta.
     * @return O parametro desejado convertido para o tipo desejado.
     */
    protected static <E extends Entity<Long>> E get(final Class<E> targetClass, final String key, final Map<String, String> params, final EntityService<Long, E> service, final String... joins) {
        ArgumentUtils.rejectIfAnyNull(targetClass, service);
        final Long id = get(Long.class, key, params);
        E entity = null;
        if (id != null) {
            entity = service.findById(id/* , joins */);
        }
        return entity;
    }

    /**
     * Efetua o parse de um parametro para uma coleção.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE MÉTODO NÃO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da coleção.
     * @param targetClass Classe do tipo de Item da coleção.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado em formato de coleção.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getCollection(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return getCollection(targetClass, key, params, null, null);
    }

    /**
     * Efetua o parse de um parametro para uma coleção.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE MÉTODO NÃO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da coleção.
     * @param targetClass Classe do tipo de Item da coleção.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de coleção.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getCollection(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return getCollection(targetClass, key, params, pattern, null);
    }

    /**
     * Efetua o parse de um parametro para uma coleção.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE MÉTODO NÃO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da coleção.
     * @param targetClass Classe do tipo de Item da coleção.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de coleção.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getCollection(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        final String sval = getParam(key, params);
        if (sval == null) {
            return null;
        }
        final Collection<String> tokens = new EnhancedStringBuilder(sval).split("\\|", true);
        Collection<S> collection = CollectionUtils.EMPTY_LIST;
        if (CollectionUtils.isNotEmpty(tokens)) {
            collection = new ArrayList<S>(tokens.size());
            for (String token : tokens) {
                S val = parseValue(targetClass, token, pattern, locale, false);
                if (val != null) {
                    collection.add(val);
                }
            }
        }
        return collection;
    }

    /**
     * Efetua o parse de um parametro para uma coleção.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE MÉTODO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da coleção.
     * @param targetClass Classe do tipo de Item da coleção.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado em formato de coleção.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getNullsafeCollection(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return getNullsafeCollection(targetClass, key, params, null, null);
    }

    /**
     * Efetua o parse de um parametro para uma coleção.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE MÉTODO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da coleção.
     * @param targetClass Classe do tipo de Item da coleção.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de coleção.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getNullsafeCollection(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return getNullsafeCollection(targetClass, key, params, pattern, null);
    }

    /**
     * Efetua o parse de um parametro para uma coleção.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE MÉTODO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item da coleção.
     * @param targetClass Classe do tipo de Item da coleção.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de coleção.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> Collection<S> getNullsafeCollection(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        final Collection<S> col = getCollection(targetClass, key, params, pattern, locale);
        return col != null ? col : CollectionUtils.EMPTY_LIST;
    }

    /**
     * Efetua o parse de um parametro para uma coleção de entidades.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE MÉTODO É NULL SAFE.</span>
     *
     * @param <E>         Tipo de entidade da coleção.
     * @param targetClass Classe do tipo de entidade do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param service     O service a ser usado na consulta.
     * @param joins       Joins a serem efetuados na consulta.
     * @return O parametro desejado em formato de coleção.
     */
    protected static <E extends Entity<Long>> Collection<E> getNullsafeCollection(final Class<E> targetClass, final String key, final Map<String, String> params, final EntityService<Long, E> service, final String... joins) {
        final Collection<Long> ids = getNullsafeCollection(Long.class, key, params);
        if (CollectionUtils.isNotEmpty(ids)) {
            final Collection<E> entities = new ArrayList<E>(ids.size());
            for (Long id : ids) {
                final E entity = service.findById(id/* , joins */);
                if (entity != null) {
                    entities.add(entity);
                }
            }
            return entities;
        }
        return CollectionUtils.EMPTY_LIST;
    }

    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE MÉTODO NÃO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getArray(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return getArray(targetClass, key, params, null, null);
    }

    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE MÉTODO NÃO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getArray(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return getArray(targetClass, key, params, pattern, null);
    }

    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #660000;">O RETORNO DESTE MÉTODO NÃO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getArray(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        final Collection<S> col = getCollection(targetClass, key, params, pattern, locale);
        if (col == null) {
            return null;
        }
        final S[] array = (S[]) Array.newInstance(targetClass, col.size());
        return col.toArray(array);
    }

    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE MÉTODO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getNullsafeArray(final Class<S> targetClass, final String key, final Map<String, String> params) {
        return getNullsafeArray(targetClass, key, params, null, null);
    }

    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE MÉTODO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getNullsafeArray(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern) {
        return getNullsafeArray(targetClass, key, params, pattern, null);
    }

    /**
     * Efetua o parse de um parametro para um array.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE MÉTODO É NULL SAFE.</span>
     * <p/>
     * Para saber quais <code>targetClass</code> este método suporta,
     * vide {@link #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean) parseValue()}.
     *
     * @param <S>         Tipo de item do array.
     * @param targetClass Classe do tipo de Item do array.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar)
     * @param locale      O locale de conversão (se targetClass == Number, Date ou Calendar)
     * @return O parametro desejado em formato de array.
     * @see #parseValue(java.lang.Class, java.lang.String, java.lang.CharSequence, java.util.Locale, boolean)parseValue()
     */
    protected static <S extends Serializable> S[] getNullsafeArray(final Class<S> targetClass, final String key, final Map<String, String> params, final CharSequence pattern, final Locale locale) {
        final Collection<S> col = getNullsafeCollection(targetClass, key, params, pattern, locale);
        final S[] array = (S[]) Array.newInstance(targetClass, col.size());
        return col.toArray(array);
    }

    /**
     * Efetua o parse de um parametro para um array de entidades.
     * <p/>
     * <span style="font-weight: bold; color: #006600;">O RETORNO DESTE MÉTODO É NULL SAFE.</span>
     *
     * @param <E>         Tipo de entidade do array.
     * @param targetClass Classe do tipo de entidade do retorno.
     * @param key         Chave para o valor do parametro.
     * @param params      Conjunto de parametros.
     * @param service     O service a ser usado na consulta.
     * @param joins       Joins a serem efetuados na consulta.
     * @return O parametro desejado em formato de array.
     */
    protected static <E extends Entity<Long>> E[] getNullsafeArray(final Class<E> targetClass, final String key, final Map<String, String> params, final EntityService<Long, E> service, final String... joins) {
        final Collection<E> col = getNullsafeCollection(targetClass, key, params, service, joins);
        final E[] array = (E[]) Array.newInstance(targetClass, col.size());
        return col.toArray(array);
    }

    /**
     * Método interno que faz o parse de um valor em formato String para um tipo desejado.
     * <p/>
     * Para adicionar suporte para um novo tipo de targetClass, apenas adicione o novo parsing no corpo desse método,
     * e todos os demais métodos que recebem targetClass passarão a reconhecê-lo.
     * <p/>
     * Este método suporta:
     * <ul>
     * <li>String</li>
     * <li>StringBuilder</li>
     * <li>StringBuffer</li>
     * <li>Boolean</li>
     * <li>Integer</li>
     * <li>Long</li>
     * <li>Double</li>
     * <li>Float</li>
     * <li>BigDecimal</li>
     * <li>Short</li>
     * <li>Byte (representado em decimal)</li>
     * <li>Date</li>
     * <li>Calendar</li>
     * </ul>
     *
     * @param <S>         Tipo de item da coleção.
     * @param targetClass Classe do tipo de Item da coleção.
     * @param sval        Valor a ser efetuado o parsing.
     * @param pattern     O pattern de conversão (se targetClass == Number, Date ou Calendar).
     * @param locale      O locale de conversão (se targetClass == Number, Date ou Calendar).
     * @param nullsafe    Indica se o retorno deverá ser nullsafe
     * @return O valor da string dada convertido para o tipo desejado.
     */
    protected static <S extends Serializable> S parseValue(final Class<S> targetClass, final String sval, final CharSequence pattern, final Locale locale, final boolean nullsafe) {
        /* throw exception se targetClass invalido */
        validateTargetClass(targetClass);

        /* usa defaults se null */
        final Locale l = LocaleUtils.getNullSafeLocale(locale);
        final CharSequence np = NumberUtils.getNullSafePattern(pattern);
        final CharSequence dp = DateUtils.getNullSafePattern(pattern);

        Object val = null;
        if (String.class.isAssignableFrom(targetClass)) {
            if (sval != null) {
                val = sval;
            } else if (nullsafe) {
                // retorna vazio se nulo
                val = "";
            }
        } else if (StringBuilder.class.isAssignableFrom(targetClass)) {
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = new StringBuilder(sval);
            } else if (nullsafe) {
                // retorna StringBuilder vazia se nulo
                val = new StringBuilder();
            }
        } else if (StringBuffer.class.isAssignableFrom(targetClass)) {
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = new StringBuffer(sval);
            } else if (nullsafe) {
                // retorna StringBuilder vazia se nulo
                val = new StringBuffer();
            }
        } else if (Number.class.isAssignableFrom(targetClass)) {
            final Class<Number> ic = (Class<Number>) targetClass;
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = NumberUtils.parseTo(ic, sval, np, l);
            } else if (nullsafe) {
                // retorna zero se nulo
                val = NumberUtils.parseTo(ic, "0", np, l);
            }
        } else if (Date.class.isAssignableFrom(targetClass)) {
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = DateUtils.parse(sval, dp, l);
            } else if (nullsafe) {
                // não sei o que assumir em caso de Date nula e nullsafe = true
                throw new IllegalArgumentException("Could not get a NullSafe Date.");
            }
        } else if (Calendar.class.isAssignableFrom(targetClass)) {
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = CalendarUtils.parse(sval, dp, l);
            } else if (nullsafe) {
                // não sei o que assumir em caso de Calendar nula e nullsafe = true
                throw new IllegalArgumentException("Could not get a NullSafe Calendar.");
            }
        } else if (Boolean.class.isAssignableFrom(targetClass)) {
            if (CharSequenceUtils.isNotBlank(sval)) {
                val = Boolean.FALSE;
                for (String v : TRUE_VALUES) {
                    if (v.equalsIgnoreCase(sval)) {
                        val = Boolean.TRUE;
                        break;
                    }
                }
            } else if (nullsafe) {
                // retorna false se nulo
                val = Boolean.FALSE;
            }
        } else {
            throw new IllegalArgumentException("Parsing for " + targetClass + " not supported.");
        }
        return (S) val;
    }

    private static void validateTargetClass(final Class<?> targetClass) {
        ArgumentUtils.rejectIfNull(targetClass);
        if (targetClass.isInterface()) {
            throw new IllegalArgumentException("targetClass cannot be an interface.");
        } else if (targetClass.isArray()) {
            throw new IllegalArgumentException("targetClass cannot be an array.");
        } else if (Collection.class.isAssignableFrom(targetClass) || Map.class.isAssignableFrom(targetClass)) {
            throw new IllegalArgumentException("targetClass cannot be a collection.");
        }
    }

    /**
     * Adiciona o field ao mapa de erros, resolvendo a message code para seu valor (de acordo com o message-source configurado).
     * <p/>
     * Se o code dado não for resolvido para uma msg, o próprio code é usado como msg de erro.
     *
     * @param field       O nome do campo a ser rejeitado.
     * @param messageCode O code para a mensagem de erro, a ser resolvido pelo message-source.
     * @param errors      O mapa de [field : msg de erro] a ser retornado para a view.
     */
    protected void reject(final String field, final String messageCode, final Map<String, String> errors) {
        reject(field, messageCode, null, errors);
    }

    /**
     * Adiciona o field ao mapa de erros, resolvendo a message code para seu valor (de acordo com o message-source configurado).
     * <p/>
     * Se o code dado não for resolvido para uma msg, o próprio code é usado como msg de erro.
     *
     * @param field       O nome do campo a ser rejeitado.
     * @param messageCode O code para a mensagem de erro, a ser resolvido pelo message-source.
     * @param params      Um array de parametros para a mensagem resolvida, caso necessário. Formato {0}, {1}, etc....
     * @param errors      O mapa de [field : msg de erro] a ser retornado para a view.
     */
    protected void reject(final String field, final String messageCode, final Object[] params, final Map<String, String> errors) {
        String message = null;
        if (CharSequenceUtils.isNotBlank(messageCode) && messageSource != null) {
            try {
                message = messageSource.getMessage(messageCode, params, LocaleUtils.getNullSafeLocale(locale));
            } catch (NoSuchMessageException ex) {
                message = messageCode;
            }
        }
        if (CharSequenceUtils.isBlankOrNull(message)) {
            message = CharSequenceUtils.isNotBlank(messageCode) ? messageCode : "Invalid Field.";
        }
        errors.put(field, message);
    }

    /**
     * Representa os parametros de ordenação de uma AjaxTable.
     */
    public static class Order {

        private final String column;
        private final String mode;

        public Order(final String column, final String mode) {
            ArgumentUtils.rejectIfAnyBlankOrNull(column, mode);
            this.column = column;
            this.mode = mode;
        }

        public String getColumn() {
            return column;
        }

        public String getMode() {
            return mode;
        }

        /**
         * Converte este objeto para o tipo Order do hibernate, que pode ser usado em consultas.
         *
         * @return O order do hibernate.
         */
        public org.hibernate.criterion.Order toHibernateOrder() {
            return "asc".equalsIgnoreCase(mode) ? org.hibernate.criterion.Order.asc(column) : org.hibernate.criterion.Order.desc(column);
        }
    }
}
