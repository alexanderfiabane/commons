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
package br.ojimarcius.commons.util;

import br.ojimarcius.commons.util.ArrayUtils;
import br.ojimarcius.commons.io.exception.RuntimeFileNotFoundException;
import br.ojimarcius.commons.io.exception.RuntimeIOException;
import br.ojimarcius.commons.util.ArgumentUtils;
import br.ojimarcius.commons.util.LocaleUtils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Various IO utilities to handle file and stream operations.
 * <p/>
 * Leverages everithing on the Apache IOUtils and add more functionality.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public abstract class IOUtils extends org.apache.commons.io.IOUtils {

    /**
     * O logger para acompanhar eventos da classe.
     */
    private static final Logger LOGGER = Logger.getLogger(IOUtils.class.getName());
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    /**
     * Path to the properties file containing the known mime-type list by file extension,
     * that will be used as the last resort to detect a file's mime-type.
     */
    protected static final String MIME_TYPES = "/" + IOUtils.class.getPackage().getName().replace('.', '/') + "/MimeTypes.properties";
    /**
     * Size of a 'kibi'.
     */
    public static final int KIBI = 1024;

    /**
     * Util files cannot be instantiated.
     */
    private IOUtils() {
    }

    public static String getExtension(final String fileName) {
        if (CharSequenceUtils.isNotBlank(fileName)) {
            int pos = fileName.lastIndexOf(".") + 1;
            if (pos > 0 && pos < fileName.length()) {
                return fileName.substring(pos);
            }
        }
        return null;
    }
    
    /**
     * Reads the contents of an URL, to a String.
     * <p/>
     * Assumes the SO default charset.
     *
     * @param url URL pointing to a text resource.
     * @return The text containing on the URL pointed resource.
     * @throws RuntimeIOException If something goes wrong opening the url' stream.
     */
    public static String readText(final URL url) {
        return readText(url, null);
    }

    /**
     * Reads the contents of an URL, to a String.
     *
     * @param url     URL pointing to a text resource.
     * @param charset The target charset, or null to use system defaults.
     * @return The text containing on the URL pointed resource.
     * @throws RuntimeIOException If something goes wrong opening the url' stream.
     */
    public static String readText(final URL url, final Charset charset) {
        if (url == null) {
            return null;
        }
        InputStream stream = null;
        try {
            stream = url.openStream();
            return readText(stream, charset);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } finally {
            closeQuietly(stream);
        }
    }

    /**
     * Reads the contents of a file, to a String.
     * <p/>
     * Assumes the SO default charset.
     *
     * @param filePath Path to the desired resource file.
     * @return The text containing on the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static String readText(final String filePath) {
        return readText(filePath, null);
    }

    /**
     * Reads the contents of a file, to a String.
     *
     * @param filePath Path to the desired resource file.
     * @param charset  The target charset, or null to use system defaults.
     * @return The text containing on the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static String readText(final String filePath, final Charset charset) {
        if (CharSequenceUtils.isBlankOrNull(filePath)) {
            return null;
        }
        return readText(getFileInputStream(filePath), charset);
    }

    /**
     * Reads the contents of a file, to a String.
     * <p/>
     * Assumes the SO default charset.
     *
     * @param file The desired resource file.
     * @return The text containing on the given resource file.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static String readText(final File file) {
        return readText(file, null);
    }

    /**
     * Reads the contents of a file, to a String.
     *
     * @param file    The desired resource file.
     * @param charset The target charset, or null to use system defaults.
     * @return The text containing on the given resource file.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static String readText(final File file, final Charset charset) {
        if (file == null) {
            return null;
        }
        return readText(getFileInputStream(file), charset);
    }

    /**
     * Reads the contents of an InputStream, to a String.
     * <p/>
     * Assumes the SO default charset.
     *
     * @param stream Stream containing the text.
     * @return The text containing on the InputStream.
     * @throws RuntimeIOException If something goes wrong reading the stream.
     */
    public static String readText(final InputStream stream) {
        return readText(stream, null);
    }

    /**
     * Reads the contents of an InputStream, to a String.
     *
     * @param stream  Stream containing the text.
     * @param charset The target charset, or null to use system defaults.
     * @return The text containing on the InputStream.
     * @throws RuntimeIOException If something goes wrong reading the stream.
     */
    public static String readText(final InputStream stream, final Charset charset) {
        if (stream == null) {
            return null;
        }
        Reader reader = null;
        try {
            reader = new InputStreamReader(stream, getNullSafeCharset(charset));
            return readText(reader);
        } finally {
            closeQuietly(reader);
            closeQuietly(stream);
        }
    }

    /**
     * Reads the String contents of a resource, using a Reader.
     *
     * @param reader Reader to be used on reading text content.
     * @return The text read by the reader.
     * @throws RuntimeIOException If something goes wrong reading the reader contents.
     */
    public static String readText(final Reader reader) {
        if (reader == null) {
            return null;
        }
        try {
            return toString(reader);
        } catch (IOException ex) {
            throw new RuntimeIOException(ex);
        } finally {
            closeQuietly(reader);
        }
    }

    public static String readText(final Package pack, final String simpleName) {
        return readText(pack, simpleName, null, null);
    }

    public static String readText(final Package pack, final String simpleName, final ClassLoader classLoader) {
        return readText(pack, simpleName, classLoader, null);
    }

    public static String readText(final Package pack, final String simpleName, final Charset charset) {
        return readText(pack, simpleName, null, charset);
    }

    public static String readText(final Package pack, final String simpleName, final ClassLoader classLoader, final Charset charset) {
        if (CharSequenceUtils.isBlankOrNull(simpleName)) {
            return null;
        }
        return readText(getClasspathInputStream(pack, simpleName, classLoader), charset);
    }

    /**
     * Reads the contents of a classpath resource file, to a String.
     *
     * @param resourcePath Path to the desired resource file.
     * @return The text containing on the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static String readClasspathText(final String resourcePath) {
        return readClasspathText(resourcePath, null, null);
    }

    /**
     * Reads the contents of a classpath resource file, to a String.
     *
     * @param classLoader  The class loader that will load the resource.
     * @param resourcePath Path to the desired resource file.
     * @return The text containing on the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static String readClasspathText(final String resourcePath, final ClassLoader classLoader) {
        return readClasspathText(resourcePath, classLoader, null);
    }

    /**
     * Reads the contents of a classpath resource file, to a String.
     *
     * @param resourcePath Path to the desired resource file.
     * @param charset      The target charset, or null to use system defaults.
     * @return The text containing on the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static String readClasspathText(final String resourcePath, final Charset charset) {
        return readClasspathText(resourcePath, null, charset);
    }

    /**
     * Reads the contents of a classpath resource file, to a String.
     *
     * @param classLoader  The class loader that will load the resource.
     * @param resourcePath Path to the desired resource file.
     * @param charset      The target charset, or null to use system defaults.
     * @return The text containing on the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static String readClasspathText(final String resourcePath, final ClassLoader classLoader, final Charset charset) {
        if (CharSequenceUtils.isBlankOrNull(resourcePath)) {
            return null;
        }
        return readText(getClasspathInputStream(resourcePath, classLoader), charset);
    }

    /**
     * Reads the contents of an URL, as a byte array.
     *
     * @param url URL pointing to a resource.
     * @return The bytes of the resource pointed by the given URL.
     * @throws RuntimeIOException If something goes wrong opening the url' stream.
     */
    public static byte[] readBytes(final URL url) {
        return readBytes(url, null);
    }

    /**
     * Reads the contents of an URL, as a byte array.
     *
     * @param url     URL pointing to a resource.
     * @param charset The target charset, or null to use system defaults.
     * @return The bytes of the resource pointed by the given URL.
     * @throws RuntimeIOException If something goes wrong opening the url' stream.
     */
    public static byte[] readBytes(final URL url, final Charset charset) {
        if (url == null) {
            return null;
        }
        InputStream stream = null;
        try {
            stream = url.openStream();
            return readBytes(stream, charset);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } finally {
            closeQuietly(stream);
        }
    }

    /**
     * Reads the contents of a file, as a byte array.
     *
     * @param filePath Path to the desired resource file.
     * @return The bytes of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static byte[] readBytes(final String filePath) {
        return readBytes(filePath, null);
    }

    /**
     * Reads the contents of a file, as a byte array.
     *
     * @param filePath Path to the desired resource file.
     * @param charset  The target charset, or null to use system defaults.
     * @return The bytes of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static byte[] readBytes(final String filePath, final Charset charset) {
        if (CharSequenceUtils.isBlankOrNull(filePath)) {
            return null;
        }
        return readBytes(getFileInputStream(new File(filePath)), charset);
    }

    /**
     * Reads the contents of a file, as a byte array.
     *
     * @param file The desired resource file.
     * @return The bytes of the given resource file.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static byte[] readBytes(final File file) {
        return readBytes(file, null);
    }

    /**
     * Reads the contents of a file, as a byte array.
     *
     * @param file    The desired resource file.
     * @param charset The target charset, or null to use system defaults.
     * @return The bytes of the given resource file.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static byte[] readBytes(final File file, final Charset charset) {
        if (file == null) {
            return null;
        }
        return readBytes(getFileInputStream(file), charset);
    }

    /**
     * Reads the contents of an InputStream, as a byte array.
     *
     * @param stream Stream containing the bytes.
     * @return The bytes of the InputStream.
     * @throws RuntimeIOException If something goes wrong reading the stream.
     */
    public static byte[] readBytes(final InputStream stream) {
        return readBytes(stream, null);
    }

    /**
     * Reads the contents of an InputStream, as a byte array.
     *
     * @param stream  Stream containing the bytes.
     * @param charset The target charset, or null to use system defaults.
     * @return The bytes of the InputStream.
     * @throws RuntimeIOException If something goes wrong reading the stream.
     */
    public static byte[] readBytes(final InputStream stream, final Charset charset) {
        if (stream == null) {
            return null;
        }
        Reader reader = null;
        try {
            if (charset == null) {
                /* assume binary data. just read the raw bytes */
                return toByteArray(stream);
            } else {
                /* assume text data. just read the bytes, using the given encoding */
                reader = new InputStreamReader(stream, charset);
                return toByteArray(reader, charset);
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } finally {
            closeQuietly(reader);
            closeQuietly(stream);
        }
    }

    /**
     * Reads the bytes of a resource, using a Reader.
     *
     * @param reader Reader to be used on reading the bytes.
     * @return The bytes read by the reader.
     * @throws RuntimeIOException If something goes wrong reading the reader contents.
     */
    public static byte[] readBytes(final Reader reader) {
        if (reader == null) {
            return null;
        }
        try {
            return toByteArray(reader);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } finally {
            closeQuietly(reader);
        }
    }

    public static byte[] readBytes(final Package pack, final String simpleName) {
        return readBytes(pack, simpleName, null, null);
    }

    public static byte[] readBytes(final Package pack, final String simpleName, final ClassLoader classLoader) {
        return readBytes(pack, simpleName, classLoader, null);
    }

    public static byte[] readBytes(final Package pack, final String simpleName, final Charset charset) {
        return readBytes(pack, simpleName, null, charset);
    }

    public static byte[] readBytes(final Package pack, final String simpleName, final ClassLoader classLoader, final Charset charset) {
        if (CharSequenceUtils.isBlankOrNull(simpleName)) {
            return null;
        }
        return readBytes(getClasspathInputStream(pack, simpleName, classLoader), charset);
    }

    /**
     * Reads the contents of a classpath resource file, as a byte array.
     *
     * @param resourcePath Path to the desired resource file.
     * @return The bytes of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static byte[] readClasspathBytes(final String resourcePath) {
        return readClasspathBytes(resourcePath, null, null);
    }

    /**
     * Reads the contents of a classpath resource file, as a byte array.
     *
     * @param classLoader  The class loader that will load the resource.
     * @param resourcePath Path to the desired resource file.
     * @return The bytes of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static byte[] readClasspathBytes(final String resourcePath, final ClassLoader classLoader) {
        return readClasspathBytes(resourcePath, classLoader, null);
    }

    /**
     * Reads the contents of a classpath resource file, as a byte array.
     *
     * @param resourcePath Path to the desired resource file.
     * @param charset      The target charset, or null to use system defaults.
     * @return The bytes of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static byte[] readClasspathBytes(final String resourcePath, final Charset charset) {
        return readClasspathBytes(resourcePath, null, charset);
    }

    /**
     * Reads the contents of a classpath resource file, as a byte array.
     *
     * @param classLoader  The class loader that will load the resource.
     * @param resourcePath Path to the desired resource file.
     * @param charset      The target charset, or null to use system defaults.
     * @return The bytes of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static byte[] readClasspathBytes(final String resourcePath, final ClassLoader classLoader, final Charset charset) {
        if (CharSequenceUtils.isBlankOrNull(resourcePath)) {
            return null;
        }
        return readBytes(getClasspathInputStream(resourcePath, classLoader), charset);
    }

    /**
     * Reads the properties of a resource url.
     * <p/>
     * Accordingly to the Standard Java Specifications, Properties files are ISO-8859-1 encoded.
     *
     * @param url URL pointing to a resource.
     * @return The properties of the resource pointed by the given URL.
     * @throws RuntimeIOException If something goes wrong opening the url' stream.
     */
    public static Properties readProperties(final URL url) {
        if (url == null) {
            return null;
        }
        InputStream stream = null;
        try {
            stream = url.openStream();
            return readProperties(stream);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        } finally {
            closeQuietly(stream);
        }
    }

    /**
     * Reads the properties of a resource file.
     * <p/>
     * Accordingly to the Standard Java Specifications, Properties files are ISO-8859-1 encoded.
     *
     * @param filepath Path to the desired resource file.
     * @return The properties of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static Properties readProperties(final String filepath) {
        if (CharSequenceUtils.isBlankOrNull(filepath)) {
            return null;
        }
        return readProperties(getFileInputStream(filepath));
    }

    /**
     * Reads the properties of a resource file.
     * <p/>
     * Accordingly to the Standard Java Specifications, Properties files are ISO-8859-1 encoded.
     *
     * @param file The desired resource file.
     * @return The properties of the given resource file.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static Properties readProperties(final File file) {
        if (file == null) {
            return null;
        }
        return readProperties(getFileInputStream(file));
    }

    /**
     * Reads the properties of a resource InputStream.
     * <p/>
     * Accordingly to the Standard Java Specifications, Properties files are ISO-8859-1 encoded.
     *
     * @param stream Stream containing the properties.
     * @return The properties containing on the InputStream.
     * @throws RuntimeIOException If something goes wrong reading the stream.
     */
    public static Properties readProperties(final InputStream stream) {
        if (stream == null) {
            return null;
        }
        return readProperties(new InputStreamReader(stream, ISO_8859_1));
    }

    /**
     * Reads the properties of a resource, using a Reader.
     * <p/>
     * Accordingly to the Standard Java Specifications, Properties files are ISO-8859-1 encoded.
     *
     * @param reader Reader to be used on reading the properties.
     * @return The properties read by the reader.
     * @throws RuntimeIOException If something goes wrong reading the reader contents.
     */
    public static Properties readProperties(final Reader reader) {
        if (reader == null) {
            return null;
        }
        try {
            final Properties p = new Properties();
            p.load(reader);
            return p;
        } catch (IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    /**
     * Reads the properties of a classpath resource file, named after a class.
     * <p/>
     * Example:<br/>
     * if the given class fully qualified name is:
     * <pre>"org.mycomp.MyClass"</pre>
     * this method will look for a properties file named:
     * <pre>"/org/mycomp/MyClass.properties"</pre>
     * on the classpath.
     * <p/>
     * Accordingly to the Standard Java Specifications, Properties files are ISO-8859-1 encoded.
     *
     * @param clazz The class witch the resource is named after.
     * @return The properties of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static Properties readProperties(final Class clazz) {
        if (clazz == null) {
            return null;
        }
        return readProperties(clazz.getPackage(), clazz.getSimpleName(), clazz.getClassLoader());
    }

    /**
     * Reads the properties of a classpath resource file, placed on the given package, under the given name.
     * <p/>
     * Accordingly to the Standard Java Specifications, Properties files are ISO-8859-1 encoded.
     *
     * @param pack       The package in witch the resource lies.
     * @param simpleName The resource simple name.
     * @return The properties of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static Properties readProperties(final Package pack, final String simpleName) {
        return readProperties(pack, simpleName, null);
    }

    /**
     * Reads the properties of a classpath resource file, placed on the given package, under the given name.
     * <p/>
     * Accordingly to the Standard Java Specifications, Properties files are ISO-8859-1 encoded.
     *
     * @param classLoader The class loader that will load the resource.
     * @param pack        The package in witch the resource lies.
     * @param simpleName  The resource simple name.
     * @return The properties of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static Properties readProperties(final Package pack, final String simpleName, final ClassLoader classLoader) {
        if (CharSequenceUtils.isBlankOrNull(simpleName)) {
            return null;
        }
        if (!simpleName.toLowerCase().endsWith(".properties")) {
            throw new IllegalArgumentException("Simple name must have a '.properties' extension.");
        }
        return readProperties(getClasspathInputStream(pack, simpleName, classLoader));
    }

    /**
     * Reads the properties of a classpath resource file.
     * <p/>
     * Accordingly to the Standard Java Specifications, Properties files are ISO-8859-1 encoded.
     *
     * @param resourcePath Path to the desired resource file.
     * @return The properties of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static Properties readClasspathProperties(final String resourcePath) {
        return readClasspathProperties(resourcePath, null);
    }

    /**
     * Reads the properties of a classpath resource file.
     * <p/>
     * Accordingly to the Standard Java Specifications, Properties files are ISO-8859-1 encoded.
     *
     * @param classLoader  The class loader that will load the resource.
     * @param resourcePath Path to the desired resource file.
     * @return The properties of the resource file pointed by the given path.
     * @throws RuntimeIOException If something goes wrong opening the file stream.
     */
    public static Properties readClasspathProperties(final String resourcePath, final ClassLoader classLoader) {
        if (CharSequenceUtils.isBlankOrNull(resourcePath)) {
            return null;
        }
        return readProperties(getClasspathInputStream(resourcePath, classLoader));
    }

    public static ResourceBundle readBundle(final Class<?> clazz) {
        return readBundle(clazz, null, null);
    }

    public static ResourceBundle readBundle(final Class<?> clazz, final Locale locale) {
        return readBundle(clazz, locale, null);
    }

    public static ResourceBundle readBundle(final Class<?> clazz, final ClassLoader classLoader) {
        return readBundle(clazz, null, classLoader);
    }

    public static ResourceBundle readBundle(final Class<?> clazz, final Locale locale, final ClassLoader classLoader) {
        if (clazz == null) {
            return null;
        }
        return readBundle(clazz.getName(), locale, classLoader);
    }

    public static ResourceBundle readBundle(final String baseName) {
        return readBundle(baseName, null, null);
    }

    public static ResourceBundle readBundle(final String baseName, final Locale locale) {
        return readBundle(baseName, locale, null);
    }

    public static ResourceBundle readBundle(final String baseName, final ClassLoader classLoader) {
        return readBundle(baseName, null, classLoader);
    }

    public static ResourceBundle readBundle(final String baseName, final Locale locale, final ClassLoader classLoader) {
        if (CharSequenceUtils.isBlankOrNull(baseName)) {
            return null;
        }
        return ResourceBundle.getBundle(baseName, LocaleUtils.getNullSafeLocale(locale), getNullSafeClassLoader(classLoader));
    }

    /**
     * Writes the given bytes to a file.
     * <p/>
     * If the given file does not exists, it will be automatically created.
     *
     * @param targetFile         File where the bytes must be written.
     * @param content            The byte contents to be written.
     * @param writeEmptyContent  Indicates if the write process can procced if the given contents is empty. Useful to avoid/permit cleaning files.
     * @param onFileExistsAction Indicates what to do in case of target file already exists.
     * @throws RuntimeIOException If the write proccess fails.
     */
    public static void writeBytes(final File targetFile, final byte[] content, final boolean writeEmptyContent, final FileExistsAction onFileExistsAction) {
        if (targetFile == null || (!writeEmptyContent && ArrayUtils.isEmptyOrNull(content))) {
            return;
        }
        File target = getUpdatedTarget(targetFile, onFileExistsAction);
        if (target == null) {
            return;
        }
        try {
            target.createNewFile();
            write(content, getFileOutputStream(target));
        } catch (IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    /**
     * Writes the given text to a file.
     * <p/>
     * If the given file does not exists, it will be automatically created.
     *
     * @param targetFile         File where the text must be written.
     * @param content            The text to be written.
     * @param charset            The charset of the target file.
     * @param writeEmptyContent  Indicates if the write process can procced if the given contents is empty. Useful to avoid/permit cleaning files.
     * @param onFileExistsAction Indicates what to do in case of target file already exists.
     * @throws RuntimeIOException If the write proccess fails.
     */
    public static void writeText(final File targetFile, final CharSequence content, final Charset charset, final boolean writeEmptyContent,
                                 final FileExistsAction onFileExistsAction) {
        if (targetFile == null || (!writeEmptyContent && CharSequenceUtils.isEmpty(content))) {
            return;
        }
        File target = getUpdatedTarget(targetFile, onFileExistsAction);
        if (target == null) {
            return;
        }
        try {
            target.createNewFile();
            write(content, getFileOutputStream(target), getNullSafeCharset(charset));
        } catch (IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    /**
     * Extracts the file name extension.
     *
     * @param fileName The file name, from where it will be extracted the extension.
     * @return The file name extension, on lowercase and without the dot.
     */
    public static String getFileNameExtension(final String fileName) {
        if (CharSequenceUtils.isBlankOrNull(fileName)) {
            return null;
        }
        int idx = fileName.lastIndexOf(".");
        return (idx > 0) ? (fileName.substring(idx + 1)).toLowerCase() : null;
    }

    /**
     * Creates a FileInputStream to the given file path.
     *
     * @param filePath The file path to open an input stream to.
     * @return The FileInputStream to the given file path.
     * @throws RuntimeFileNotFoundException if the file cannot be found.
     */
    public static InputStream getFileInputStream(final String filePath) {
        if (CharSequenceUtils.isBlankOrNull(filePath)) {
            return null;
        }
        return getFileInputStream(new File(filePath));
    }

    /**
     * Creates a FileInputStream to the given file.
     *
     * @param file The file to open an input stream to.
     * @return The FileInputStream to the given file.
     */
    public static InputStream getFileInputStream(final File file) {
        if (file == null) {
            return null;
        }
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            throw new RuntimeFileNotFoundException(ex);
        }
    }

    public static InputStream getClasspathInputStream(final Package pack, final String simpleName) {
        return getClasspathInputStream(pack, simpleName, null);
    }

    public static InputStream getClasspathInputStream(final Package pack, final String simpleName, final ClassLoader classLoader) {
        if (CharSequenceUtils.isBlankOrNull(simpleName)) {
            return null;
        }
        return getClasspathInputStream(getClasspathResourcePath(pack, simpleName), classLoader);
    }

    public static InputStream getClasspathInputStream(final String resourcePath) {
        return getClasspathInputStream(resourcePath, null);
    }

    public static InputStream getClasspathInputStream(final String resourcePath, final ClassLoader classLoader) {
        if (CharSequenceUtils.isBlankOrNull(resourcePath)) {
            return null;
        }
        final String pathToUse = resourcePath.replaceAll("^[/]+", "");
        final ClassLoader cl = (classLoader != null ? classLoader : ReflectionUtils.getDefaultClassLoader());
        return cl.getResourceAsStream(pathToUse);
    }

    /**
     * Creates a FileOutputStream to the given file path.
     *
     * @param filePath The file path to open an output stream to.
     * @return The FileOutputStream to the given file path.
     * @throws RuntimeFileNotFoundException if the file cannot be found.
     */
    public static OutputStream getFileOutputStream(final String filePath) {
        if (CharSequenceUtils.isBlankOrNull(filePath)) {
            return null;
        }
        return getFileOutputStream(new File(filePath));
    }

    /**
     * Creates a FileOutputStream to the given file.
     *
     * @param file The file to open an output stream to.
     * @return The FileOutputStream to the given file.
     */
    public static OutputStream getFileOutputStream(final File file) {
        if (file == null) {
            return null;
        }
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            throw new RuntimeFileNotFoundException(ex);
        }
    }

    /**
     * Creates a BufferedReader for the given byte array.
     *
     * @param content The bytes that will be handled by the Reader.
     * @param charset The charset to be used while reading contents. <tt>null</tt> means <tt>Charset.defaultCharset()</tt>.
     * @return The new BufferedReader.
     */
    public static BufferedReader getBufferedReader(final byte[] content, final Charset charset) {
        if (content == null) {
            return null;
        }
        return toBufferedReader(new InputStreamReader(new ByteArrayInputStream(content), getNullSafeCharset(charset)));
    }

    public static boolean isFile(final File file) {
        return file != null && file.isFile();
    }

    public static boolean isNotFile(final File file) {
        return file == null || !file.isFile();
    }

    public static boolean exists(final File file) {
        return file != null && file.exists();
    }

    public static boolean notExists(final File file) {
        return file == null || !file.exists();
    }

    public static boolean isDirectory(final File file) {
        return file != null && file.isDirectory();
    }

    public static boolean isNotDirectory(final File file) {
        return file == null || !file.isDirectory();
    }

    public static boolean isHidden(final File file) {
        return file != null && file.isHidden();
    }

    public static boolean isNotHidden(final File file) {
        return file == null || !file.isHidden();
    }

    public static boolean isAbsolute(final File file) {
        return file != null && file.isAbsolute();
    }

    public static boolean isNotAbsolute(final File file) {
        return file == null || !file.isAbsolute();
    }

    public static boolean makeDirs(final File file) {
        if (notExists(file)) {
            return file.mkdirs();
        }
        return false;
    }

    public static Charset getCharset(final String charsetName) {
        if (CharSequenceUtils.isBlankOrNull(charsetName)) {
            LOGGER.log(Level.WARNING, "Charset name is blank. Assuming default charset!");
            return Charset.defaultCharset();
        }
        return Charset.forName(charsetName);
    }

    public static String getRelativePath(final File base, final File child) {
        ArgumentUtils.rejectIfAnyNull(base, child);
        return base.toURI().relativize(child.toURI()).getPath();
    }

    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
    
    /**
     * Appends a sufix between the file name and its extension.
     * <p/>
     * Used when writing a file with {@link FileExistsAction#RENAME_EXISTING} or {@link FileExistsAction#RENAME_NEW}.
     *
     * @param oldName The original file path/name;
     * @param suffix  A suffix to diferentiate the files.
     * @return The renamed file.
     */
    private static File getRenamedFile(final String oldName, final String suffix) {
        if (CharSequenceUtils.isAnyBlankOrNull(oldName, suffix)) {
            throw new IllegalArgumentException("Blank argument!");
        }
        final int idx = oldName.lastIndexOf(".");
        final String newNameBase;
        if (idx > 0) {
            newNameBase = oldName.substring(0, idx) + "_" + suffix + "{i}" + oldName.substring(idx);
        } else {
            newNameBase = oldName + "_" + suffix + "{i}";
        }
        int i = 0;
        File targetFileRename;
        do {
            targetFileRename = new File(newNameBase.replace("{i}", Integer.toString(i)));
            i++;
        } while (targetFileRename.exists());
        return targetFileRename;
    }

    private static File getUpdatedTarget(final File targetFile, final FileExistsAction onFileExistsAction) {
        if (targetFile == null) {
            return null;
        }
        File target = targetFile;
        final FileExistsAction action = (onFileExistsAction != null) ? onFileExistsAction : FileExistsAction.RENAME_NEW;
        if (target.isDirectory()) {
            throw new IllegalArgumentException("The given file already exists and is a directory.");
        } else if (target.exists()) {
            switch (action) {
                case RENAME_EXISTING:
                    String tmp = target.getAbsolutePath();
                    if (target.renameTo(getRenamedFile(target.getAbsolutePath(), "old"))) {
                        target = new File(tmp);
                    }
                    break;
                case RENAME_NEW:
                    target = getRenamedFile(target.getAbsolutePath(), "new");
                    break;
                case THROW_EXCEPTION:
                    throw new IllegalArgumentException(target.getAbsolutePath() + " already exists.");
                case OVERRIDE:
                    // do nothing to the filepath
                    break;
                default:
                    target = null;
                    break;
            }
        }
        return target;
    }

    /**
     * If the given charset is not null, it will be returned, else the system default charset will be returned.
     *
     * @param declaredCharset The charset to be tested.
     * @return The given charset or the system default if the given one is null.
     */
    protected static Charset getNullSafeCharset(final Charset declaredCharset) {
        return declaredCharset != null ? declaredCharset : Charset.defaultCharset();
    }

    protected static String getClasspathResourcePath(final Package pack, final String simpleName) {
        if (CharSequenceUtils.isBlankOrNull(simpleName)) {
            return null;
        }
        final StringBuilder builder = new StringBuilder("/");
        if (pack != null) {
            builder.append(pack.getName().replaceAll("\\.", "/")).append("/");
        }
        return builder.append(simpleName).toString();
    }

    /**
     * Returns the given ClassLoader, or the IOUtils default class-loader if the given one is null.
     *
     * @param preferredLoader
     * @return The given ClassLoader, or the IOUtils default class-loader if the given one is null.
     */
    protected static ClassLoader getNullSafeClassLoader(final ClassLoader preferredLoader) {
        if (preferredLoader != null) {
            return preferredLoader;
        }
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = IOUtils.class.getClassLoader();
        }
        return cl;
    }

    public enum FileExistsAction {

        DO_NOTHING,
        RENAME_EXISTING,
        RENAME_NEW,
        OVERRIDE,
        THROW_EXCEPTION;
    }
}
