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
package br.msf.commons.util;

import br.msf.commons.reflect.MethodHook;
import br.msf.commons.reflect.exception.NoSuchGetterException;
import br.msf.commons.text.EnhancedStringBuilder;
import br.msf.commons.util.ArgumentUtils;
import br.msf.commons.util.ObjectUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that contains various utilities to handle reflection calls.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public abstract class ReflectionUtils {

    private static final Logger LOGGER = Logger.getLogger(ReflectionUtils.class.getName());
    /**
     * Usado no {@link #invokeCascadeGetterFor(java.lang.Object, java.lang.String) invokeCascadeGetterFor}.
     * <p/>
     * O "this" serve apenas para dar mais clareza na leitura do codigo.
     */
    private static final String THIS_NO_INICIO_REGEX = "^(\\.)*this(\\.)*";
    /**
     * Usado no {@link #invokeCascadeGetterFor(java.lang.Object, java.lang.String) invokeCascadeGetterFor}.
     * <p/>
     * Usado para quebrar o caminho de propriedades, para processamento uma a uma.
     */
    private static final String ATTRIB_PATH_SPLIT_REGEX = "[\\.\\[\\]]+";

    public static boolean isClass(final Object value) {
        return ObjectUtils.isClass(value);
    }

    public static boolean isClass(final Object value, final boolean acceptNull) {
        return ObjectUtils.isClass(value, acceptNull);
    }

    public static boolean isInterfaceClass(final Object value) {
        return isInterfaceClass(value, false);
    }

    public static boolean isInterfaceClass(final Object value, final boolean acceptNull) {
        if (isClass(value, false)) {
            return ((Class) value).isInterface();
        } else if (value == null && acceptNull) {
            return true;
        }
        return false;
    }

    public static boolean isAnnotationClass(final Object value) {
        return isAnnotationClass(value, false);
    }

    public static boolean isAnnotationClass(final Object value, final boolean acceptNull) {
        if (isClass(value, false)) {
            return ((Class) value).isAnnotation();
        } else if (value == null && acceptNull) {
            return true;
        }
        return false;
    }

    public static boolean isEnumClass(final Object value) {
        return isEnumClass(value, false);
    }

    public static boolean isEnumClass(final Object value, final boolean acceptNull) {
        if (isClass(value, false)) {
            return ((Class) value).isEnum();
        } else if (value == null && acceptNull) {
            return true;
        }
        return false;
    }

    /**
     * Invoke a chain of getters.
     * <p/>
     * Ex: if attribPath is "funcionario.pessoa.nome", "target.getFuncionario().getPessoa().getNome()" will be invoked.
     * <p/>
     * The invoking is null-safe.
     *
     * @param target     Object were the invokes will be placed.
     * @param attribPath The invoking path.
     * @return The returned value.
     */
    public static Object invokeCascadeGetterFor(final Object target, final String attribPath) {
        String[] attribs = attribPath.split("\\.");
        Object current = target;
        for (String attr : attribs) {
            if (current == null) {
                return null;
            }
            if (CharSequenceUtils.isNotBlank(attr)) {
                current = invokeGetterFor(current, attr);
            }
        }
        return current;
    }

    /**
     * Invoke a a getter.
     *
     * @param target     Object were the invoke will be placed.
     * @param attribName The attribute name.
     * @return The returned value.
     */
    public static Object invokeGetterFor(final Object target, final String attribName) {
        Class<? extends Object> c = target.getClass();
        Method getter = null;
        try {
            getter = c.getMethod(genGetterName("get", attribName));
        } catch (NoSuchMethodException ex1) {
            try {
                getter = c.getMethod(genGetterName("is", attribName));
            } catch (NoSuchMethodException ex2) {
                try {
                    getter = c.getMethod(attribName);
                } catch (NoSuchMethodException ex3) {
                    throw new NoSuchGetterException("Could not find a getter for " + attribName, ex3);
                }
            }
        }
        if (getter == null) {
            throw new NoSuchGetterException("Could not find a getter for " + attribName);
        }
        try {
            if (!getter.isAccessible()) {
                getter.setAccessible(true);
            }
            return getter.invoke(target);
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException ex) {
            throw new RuntimeException("Could not invoke " + getter.getName() + "()", ex);
        }
    }

    public static void invokeSetterFor(final Object object, final String attribName, final Object value) {
        Class<? extends Object> c = object.getClass();
        Method setter = null;
        try {
            setter = getMethod(c, genGetterName("set", attribName), value.getClass());
        } catch (NoSuchMethodException ex) {
            throw new NoSuchGetterException("Não foi encontrado um setter para " + attribName, ex);
        }
        if (setter == null) {
            throw new NoSuchGetterException("Não foi encontrado um setter para " + attribName);
        }
        try {
            if (!setter.isAccessible()) {
                setter.setAccessible(true);
            }
            setter.invoke(object, value);
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException ex) {
            throw new RuntimeException("Não foi possível executar " + setter.getName() + "( " + value.getClass().getName() + " )", ex);
        }
    }

    /**
     * Returns the getter name, accordingly to the java naming standards.
     *
     * @param prefix     Must be "get" or "is".
     * @param attribName The attribute name.
     * @return The getter name.
     */
    protected static String genGetterName(final String prefix, final String attribName) {
        StringBuilder builder = new StringBuilder(prefix).append(Character.toUpperCase(attribName.charAt(0)));
        if (attribName.length() > 1) {
            builder.append(attribName.substring(1));
        }
        return builder.toString();
    }

    /**
     * Returns a list of the fields owned by the given class, including superclasses fields, recursively.
     *
     * @param mainClass The main class.
     * @return The list of the fields owned by the given class, including superclasses fields, recursively.
     */
    public static List<Field> getFields(final Class mainClass) {
        final List<Field> fields = new ArrayList<>();
        for (Class clazz = mainClass; clazz != null && !Object.class.equals(clazz); clazz = clazz.getSuperclass()) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        return fields;
    }

    public static Field getField(final Class clazz, final String fieldName) {
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            return getField(clazz.getSuperclass(), fieldName);
        }
    }

    public static <T extends Object> T getFieldValue(final Object object, final String fieldName) {
        ArgumentUtils.rejectIfBlankOrNull(fieldName);
        if (object == null) {
            return null;
        }
        try {
            final Field f = getField(object.getClass(), fieldName);
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            return (T) f.get(object);
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void setField(final Object object, final String fieldName, final Object value) {
        final Field field = getField(object.getClass(), fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Classe '" + object.getClass().getName() + "' não possui o campo '" + fieldName + "'");
        }
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            field.set(object, value);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Não foi possível setar o campo '" + fieldName + "' da classe '" + object.getClass().getName() + "'", ex);
        }
    }

    /**
     * Looks for the default POJO constructor of the class, and return it if found.
     * <p/>
     * If there is a default constructor and it is not accessible, it is turned accessible before returning it.
     * <p/>
     * Throws a RuntimeException if there is no such constructor.
     *
     * @param <T> The class type.
     * @param c   The class to look up for its default constructor.
     * @return The default constructor found, set as accessible.
     * @throws RuntimeException if there is no such constructor.
     */
    public static <T> Constructor<T> getDefaultConstructor(final Class<T> c) {
        ArgumentUtils.rejectIfNull(c);
        try {
            Constructor<T> constructor = c.getConstructor();
            if (constructor == null) {
                throw new IllegalStateException("The given class doesn't have a standard constructor.");
            }
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor;
        } catch (IllegalStateException | NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Creates a new instance of the given class, invoking the default constructor.
     *
     * @param <T> The return type.
     * @param c   The class.
     * @return The new object created.
     */
    public static <T> T newInstanceOf(final Class<T> c) {
        ArgumentUtils.rejectIfNull(c);
        try {
            return getDefaultConstructor(c).newInstance();
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the collection of fields defined by the object class and its superClasses.
     *
     * @param object           The object instance to be scanned for fields.
     * @param includeStatic    Indicates if the scanning must include static fields.
     * @param includeTransient Indicates if the scanning must include transient fields.
     * @param includeVolatile  Indicates if the scanning must include volatile fields.
     * @return The Collection of fields found.
     */
    public static Collection<Field> getFields(final Object object, final boolean includeStatic, final boolean includeTransient, final boolean includeVolatile) {
        ArgumentUtils.rejectIfNull(object);
        Class<?> clazz = object.getClass();
        Collection<Field> fields = new ArrayList<>();
        while (clazz != null && !Object.class.equals(clazz)) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                int modifiers = field.getModifiers();
                if ((includeStatic || !Modifier.isStatic(modifiers))
                    && (includeTransient || !Modifier.isTransient(modifiers))
                    && (includeVolatile || !Modifier.isVolatile(modifiers))) {
                    fields.add(field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public static Collection<Method> getMethods(final Object object) {
        return getMethods(object, true);
    }

    public static Collection<Method> getMethods(final Object object, final boolean includeStatic) {
        ArgumentUtils.rejectIfNull(object);
        final Collection<Method> methods = new TreeSet<>(new Comparator<Method>() {
            @Override
            public int compare(Method f1, Method f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });
        Class<?> clazz = object.getClass();
        while (clazz != null && !Object.class.equals(clazz)) {
            final Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                final int modifiers = method.getModifiers();
                if (includeStatic || !Modifier.isStatic(modifiers)) {
                    methods.add(method);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return methods;
    }

    public static <T extends Object> Object invokeMethod(final T targetInstance, final Method method) {
        return invokeMethod(targetInstance, method, null, null);
    }

    public static <T extends Object> Object invokeMethod(final T targetInstance, final Method method, final Object[] args) {
        return invokeMethod(targetInstance, method, args, null);
    }

    public static <T extends Object> Object invokeMethod(final T targetInstance, final Method method, final MethodHook<T> hook) {
        return invokeMethod(targetInstance, method, null, hook);
    }

    public static <T extends Object> Object invokeMethod(final T targetInstance, final Method method, final Object[] args, final MethodHook<T> hook) {
        ArgumentUtils.rejectIfNull(method);
        if (!Modifier.isStatic(method.getModifiers())) {
            // se nao for estatico, tem que ter instance
            ArgumentUtils.rejectIfNull(targetInstance);
        }
        Object ret = null;
        method.setAccessible(true);
        try {
            if (hook != null) {
                hook.preHook(targetInstance, args);
            }
            ret = method.invoke(targetInstance, args);
            if (hook != null) {
                hook.postHook(targetInstance, args);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        return ret;
    }

    /**
     * Clones an object, by scanning the original for fields and copying these field values to a new instance of
     * the same class.
     * <p/>
     * The given object <strong>must</strong> have a default, no argument, constructor.
     * <p/>
     * <strong>Static, transient and volatile fields are ignored on the process</strong>.
     *
     * @param <T>      The type of the cloned object.
     * @param original The object to be cloned.
     * @return The copy the the given object.
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneObject(final T original) {
        if (original == null) {
            return null;
        }
        final T clone = (T) newInstanceOf(original.getClass());
        final Collection<Field> fields = getFields(original, false, false, false);
        for (Field field : fields) {
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Object fieldValue = field.get(original);
                field.set(clone, fieldValue);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
        return clone;
    }

    public static String getPackage(final Class clazz, final String separator) {
        ArgumentUtils.rejectIfNull(clazz);
        if (CharSequenceUtils.isBlankOrNull(separator) || ".".equals(separator)) {
            return clazz.getPackage().getName();
        }
        return (new EnhancedStringBuilder(clazz.getPackage().getName())).
                replacePlain(".", separator).toString();
    }

    public static Method getMethod(final Class clazz, final String name, final Class<?>... parameterTypes) throws NoSuchMethodException {
        if (clazz == null) {
            throw new NoSuchMethodException(name + "() method not found.");
        }
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException | SecurityException ex) {
            return getMethod(clazz.getSuperclass(), name, parameterTypes);
        }
    }

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ReflectionUtils.class.getClassLoader();
        }
        return cl;
    }

    public static boolean hasDeclaredMethod(final Class clazz, final String methodName, final Class<?>... parameterTypes) {
        try {
            if (clazz == null || CharSequenceUtils.isAllBlankOrNull(methodName)) {
                return false;
            }
            return clazz.getDeclaredMethod(methodName, Object.class) != null;
        } catch (NoSuchMethodException | SecurityException ex) {
            return false;
        }
    }
}
