/*
 * commons-core - Copyright (c) 2009-2012 MSF. All rights reserved.
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
package br.msf.commons.velocity;

import br.msf.commons.io.exception.RuntimeIOException;
import br.msf.commons.util.CharSequenceUtils;
import br.msf.commons.util.CollectionUtils;
import br.msf.commons.util.IOUtils;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

/**
 * Basic implementation of a {@link VelocityProcessor}.
 *
 * @param <P> Type of expected params, if applicable.
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public class ConfigurableVelocityProcessor<P> implements VelocityProcessor<P> {

    /**
     * Storage of utility objects and classes that can be invoked on the source-code to provide some functionality.
     */
    protected Map<String, Object> tools;
    /**
     * Velocity engine that will do the processing job.
     */
    protected final VelocityEngine engine;

    /**
     * Default constructor.
     * <p/>
     * Uses the default velocity configuration.
     */
    public ConfigurableVelocityProcessor() {
        this(null);
    }

    /**
     * Constructor that provides fine tuning of the velocity configurations.
     *
     * @param velocityConfig The properties file that provides additional configs that extends or overrides the default
     *                       ones.
     */
    public ConfigurableVelocityProcessor(final Properties velocityConfig) {
        try {
            this.engine = (CollectionUtils.isNotEmpty(velocityConfig)) ? new VelocityEngine(velocityConfig) : new VelocityEngine();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String processFile(final String filePath, final Map<String, P> params) {
        if (CharSequenceUtils.isBlankOrNull(filePath)) {
            return null;
        }
        return processFile(new File(filePath), params);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String processFile(final File file, final Map<String, P> params) {
        try {
            return (file != null) ? process(file.toURI().toURL(), params) : null;
        } catch (MalformedURLException e) {
            // cant happen. file.toURI() returns the absolute URI.
            throw new IllegalStateException(e);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String process(final URL url, final Map<String, P> params) {
        try {
            return (url != null) ? process(url.openStream(), params) : null;
        } catch (IOException ex) {
            throw new RuntimeIOException(ex);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String process(final InputStream stream, final Map<String, P> params) {
        return (stream != null) ? process(IOUtils.readText(stream, getInputCharset()), params) : null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String process(final Reader reader, final Map<String, P> params) {
        return (reader != null) ? process(IOUtils.readText(reader), params) : null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String process(final CharSequence templateSource, final Map<String, P> params) {
        if (templateSource == null) {
            return null;
        } else if (CharSequenceUtils.isBlankOrNull(templateSource)) {
            return "";
        }
        final Writer out = new StringWriter();
        engine.evaluate(getContext(params), out, "logTag", templateSource.toString());
        return out.toString();
    }

    /**
     * Sets the map of utility objects, to be used across the templates.
     * <p/>
     * Currently set tools are discarded.
     *
     * @param tools The map of utility objects, to be used across the templates.
     */
    public final void setTools(final Map<String, Object> tools) {
        this.tools = tools;
    }

    /**
     * Adds a utility object instance to the tools map.
     *
     * @param toolName  The name of the tool, as it will be called on the templates.
     * @param toolValue The instance of the utility object.
     */
    public final void addTool(final String toolName, final Object toolValue) {
        if (CollectionUtils.isEmptyOrNull(tools)) {
            tools = new HashMap<String, Object>();
        }
        tools.put(toolName, toolValue);
    }

    /**
     * Creates and returns a new velocity context.
     *
     * @param params Params to be attached on the context.
     * @return The new velocity context.
     */
    protected Context getContext(final Map<String, P> params) {
        VelocityContext context = new VelocityContext();
        putOnContext(context, tools);
        putOnContext(context, params);
        return context;
    }

    /**
     * Attach the map of objects to the given context.
     *
     * @param context The context, where the objects will be attached.
     * @param map     The objects to be attached.
     */
    protected final void putOnContext(final Context context, final Map<String, ?> map) {
        if (CollectionUtils.isNotEmpty(map)) {
            for (Entry<String, ?> entry : map.entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Returns the engine's config entry for the input encoding.
     * <p/>
     * @return The configured input encoding.
     */
    protected String getInputEncoding() {
        return engine.getProperty("input.encoding").toString();
    }

    /**
     * Returns the engine's config entry for the output encoding.
     * <p/>
     * @return The configured output encoding.
     */
    protected String getOutputEncoding() {
        return engine.getProperty("output.encoding").toString();
    }

    /**
     * Returns the engine's config entry for the input encoding, as a Charset object.
     * <p/>
     * @return The configured input charset.
     */
    protected Charset getInputCharset() {
        return Charset.forName(getInputEncoding());
    }

    /**
     * Returns the engine's config entry for the output encoding, as a Charset object.
     * <p/>
     * @return The configured output charset.
     */
    protected Charset getOutputCharset() {
        return Charset.forName(getOutputEncoding());
    }
}
