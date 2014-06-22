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
package br.ojimarcius.commons.util;

import br.ojimarcius.commons.reflect.MethodHook;
import br.ojimarcius.commons.util.CollectionUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilidades para anotações.
 *
 * @author Marcius da Silva da Fonseca (mfonseca@ufsm.br)
 */
public class AnnotationUtils {

    private static final Logger LOGGER = Logger.getLogger(AnnotationUtils.class.getName());

    public static <A extends Annotation> A getAnnotation(final Class<?> annotatedClass, final Class<A> annotationClass) {
        return annotatedClass.getAnnotation(annotationClass);
    }

    public static <A extends Annotation> A getAnnotation(final Method annotatedMethod, final Class<A> annotationClass) {
        return annotatedMethod.getAnnotation(annotationClass);
    }

    public static <A extends Annotation> A getAnnotation(final Field annotatedField, final Class<A> annotationClass) {
        return annotatedField.getAnnotation(annotationClass);
    }

    public static <A extends Annotation> boolean hasAnnotation(final Class<?> annotatedClass, final Class<A> annotationClass) {
        return annotatedClass.isAnnotationPresent(annotationClass);
    }

    public static <A extends Annotation> boolean hasAnnotation(final Method annotatedMethod, final Class<A> annotationClass) {
        return annotatedMethod.isAnnotationPresent(annotationClass);
    }

    public static <A extends Annotation> boolean hasAnnotation(final Field annotatedField, final Class<A> annotationClass) {
        return annotatedField.isAnnotationPresent(annotationClass);
    }

    /**
     * Retorna o método anotado com a anotação dada.
     * <p/>
     * Assume que deverá ter no máx um método anotado.
     *
     * @param <A>             Tipo de Anotação.
     * @param <T>             Tipo de instância-alvo.
     * @param targetInstance  Objeto-alvo, contendo o método anotado a ser buscado.
     * @param annotationClass A classe da anotação desejada.
     * @return O método anotado.
     */
    public static <A extends Annotation, T extends Object> Method getAnnotatedMethod(final T targetInstance, final Class<A> annotationClass) {
        return CollectionUtils.getSingleton(getAnnotatedMethods(targetInstance, annotationClass));
    }

    /**
     * Retorna o(s) método(s) anotado(s) com a anotação dada.
     *
     * @param <A>             Tipo de Anotação.
     * @param <T>             Tipo de instância-alvo.
     * @param targetInstance  Objeto-alvo, contendo o(s) método(s) anotado(s) a ser(em) buscado(s).
     * @param annotationClass A classe da anotação desejada.
     * @return A coleção de métodos anotados.
     */
    public static <A extends Annotation, T extends Object> Collection<Method> getAnnotatedMethods(final T targetInstance, final Class<A> annotationClass) {
        if (annotationClass == null) {
            return CollectionUtils.EMPTY_LIST;
        }
        Collection<Method> allMethods = ReflectionUtils.getMethods(targetInstance, false);
        Collection<Method> annotatedMethods = new ArrayList<Method>(20);
        for (final Method method : allMethods) {
            if (AnnotationUtils.hasAnnotation(method, annotationClass)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    /**
     * Executa o método anotado com a anotação dada, se houver.
     * <p/>
     * Assume que não tem hooks de execução e o o método invocado não possui parâmetros.
     * <p/>
     * Assume que deverá ter no máx um método anotado.
     *
     * @param <A>             Tipo de Anotação.
     * @param <T>             Tipo de instância-alvo.
     * @param targetInstance  Objeto-alvo, contendo o método anotado a ser invocado.
     * @param annotationClass A classe da anotação desejada.
     * @return O retorno da invocação do método.
     */
    public static <A extends Annotation, T extends Object> Object invokeAnnotatedMethod(final T targetInstance, final Class<A> annotationClass) {
        return invokeAnnotatedMethod(targetInstance, annotationClass, null, null);
    }

    /**
     * Executa o método anotado com a anotação dada, se houver.
     * <p/>
     * Assume que não tem hooks de execução e o o método invocado não possui parâmetros.
     * <p/>
     * Assume que deverá ter no máx um método anotado.
     *
     * @param <A>             Tipo de Anotação.
     * @param <T>             Tipo de instância-alvo.
     * @param targetInstance  Objeto-alvo, contendo o método anotado a ser invocado.
     * @param annotationClass A classe da anotação desejada.
     * @param args            Argumentos a serem passados para o método invocado.
     * @return O retorno da invocação do método.
     */
    public static <A extends Annotation, T extends Object> Object invokeAnnotatedMethod(final T targetInstance, final Class<A> annotationClass, final Object[] args) {
        return invokeAnnotatedMethod(targetInstance, annotationClass, args, null);
    }

    /**
     * Executa o método anotado com a anotação dada, se houver.
     * <p/>
     * Assume que não tem hooks de execução e o o método invocado não possui parâmetros.
     * <p/>
     * Assume que deverá ter no máx um método anotado.
     *
     * @param <A>             Tipo de Anotação.
     * @param <T>             Tipo de instância-alvo.
     * @param targetInstance  Objeto-alvo, contendo o método anotado a ser invocado.
     * @param annotationClass A classe da anotação desejada.
     * @param hook            Objeto que implementa os métodos preHook e/ou postHook, a serem executados ao redor do método anotado.
     * @return O retorno da invocação do método.
     */
    public static <A extends Annotation, T extends Object> Object invokeAnnotatedMethod(final T targetInstance, final Class<A> annotationClass, final MethodHook<T> hook) {
        return invokeAnnotatedMethod(targetInstance, annotationClass, null, hook);
    }

    /**
     * Executa o método anotado com a anotação dada, se houver.
     * <p/>
     * Assume que deverá ter no máx um método anotado.
     *
     * @param <A>             Tipo de Anotação.
     * @param <T>             Tipo de instância-alvo.
     * @param targetInstance  Objeto-alvo, contendo o método anotado a ser invocado.
     * @param annotationClass A classe da anotação desejada.
     * @param args            Argumentos a serem passados para o método invocado.
     * @param hook            Objeto que implementa os métodos preHook e/ou postHook, a serem executados ao redor do método anotado.
     * @return O retorno da invocação do método.
     */
    public static <A extends Annotation, T extends Object> Object invokeAnnotatedMethod(final T targetInstance, final Class<A> annotationClass, final Object[] args, final MethodHook<T> hook) {
        Method method = getAnnotatedMethod(targetInstance, annotationClass);
        if (method != null) {
            return ReflectionUtils.invokeMethod(targetInstance, method, args, hook);
        } else {
            LOGGER.log(Level.FINE, "No methods annotated with <{0}>", annotationClass.getName());
        }
        return null;
    }

    /**
     * Executa um ou mais métodos anotados com a anotação dada.
     * <p/>
     * Assume que não tem hooks de execução e o o método invocado não possui parâmetros.
     *
     * @param <A>             Tipo de Anotação.
     * @param <T>             Tipo de instância-alvo.
     * @param targetInstance  Objeto-alvo, contendo o método anotado a ser invocado.
     * @param annotationClass A classe da anotação desejada.
     */
    public static <A extends Annotation, T extends Object> void invokeAnnotatedMethods(final T targetInstance, final Class<A> annotationClass) {
        invokeAnnotatedMethods(targetInstance, annotationClass, null, null);
    }

    /**
     * Executa um ou mais métodos anotados com a anotação dada.
     * <p/>
     * Assume que não tem hooks de execução e o o método invocado não possui parâmetros.
     *
     * @param <A>             Tipo de Anotação.
     * @param <T>             Tipo de instância-alvo.
     * @param targetInstance  Objeto-alvo, contendo o método anotado a ser invocado.
     * @param annotationClass A classe da anotação desejada.
     * @param args            Argumentos a serem passados para o método invocado.
     */
    public static <A extends Annotation, T extends Object> void invokeAnnotatedMethods(final T targetInstance, final Class<A> annotationClass, final Object[] args) {
        invokeAnnotatedMethods(targetInstance, annotationClass, args, null);
    }

    /**
     * Executa um ou mais métodos anotados com a anotação dada.
     * <p/>
     * Assume que não tem hooks de execução e o o método invocado não possui parâmetros.
     *
     * @param <A>             Tipo de Anotação.
     * @param <T>             Tipo de instância-alvo.
     * @param targetInstance  Objeto-alvo, contendo o método anotado a ser invocado.
     * @param annotationClass A classe da anotação desejada.
     * @param hook            Objeto que implementa os métodos preHook e/ou postHook, a serem executados ao redor do método anotado.
     */
    public static <A extends Annotation, T extends Object> void invokeAnnotatedMethods(final T targetInstance, final Class<A> annotationClass, final MethodHook<T> hook) {
        invokeAnnotatedMethods(targetInstance, annotationClass, null, hook);
    }

    /**
     * Executa um ou mais métodos anotados com a anotação dada.
     *
     * @param <A>             Tipo de Anotação.
     * @param <T>             Tipo de instância-alvo.
     * @param targetInstance  Objeto-alvo, contendo o método anotado a ser invocado.
     * @param annotationClass A classe da anotação desejada.
     * @param args            Argumentos a serem passados para o método invocado.
     * @param hook            Objeto que implementa os métodos preHook e/ou postHook, a serem executados ao redor do método anotado.
     */
    public static <A extends Annotation, T extends Object> void invokeAnnotatedMethods(final T targetInstance, final Class<A> annotationClass, final Object[] args, final MethodHook<T> hook) {
        for (Method method : getAnnotatedMethods(targetInstance, annotationClass)) {
            ReflectionUtils.invokeMethod(targetInstance, method, args, hook);
        }
    }
}
