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

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

/**
 * Class that defines the behavior of a velocity template processor.
 *
 * @param <P> Type of expected params, if applicable.
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public interface VelocityProcessor<P> {

    /**
     * Process the contents of the template stored on the file of given path.
     *
     * @param filePath The full path to the file containing the template.
     * @param params   The params to be used on the evaluation of the template.
     * @return The contents of the processed velocity template.
     */
    public String processFile(final String filePath, final Map<String, P> params);

    /**
     * Process the contents of the template stored on the given file.
     *
     * @param file   The file containing the template.
     * @param params The params to be used on the evaluation of the template.
     * @return The contents of the processed velocity template.
     */
    public String processFile(final File file, final Map<String, P> params);

    /**
     * Process the contents of the template stored on the given URL.
     *
     * @param url    The URL pointing to the resource containing the template.
     * @param params The params to be used on the evaluation of the template.
     * @return The contents of the processed velocity template.
     */
    public String process(final URL url, final Map<String, P> params);

    /**
     * Process the contents of the template pointed by the given stream.
     *
     * @param stream The stream containing the template.
     * @param params The params to be used on the evaluation of the template.
     * @return The contents of the processed velocity template.
     */
    public String process(final InputStream stream, final Map<String, P> params);

    /**
     * Process the contents of the template pointed by the given reader.
     *
     * @param reader The reader pointing to the resource containing the template.
     * @param params The params to be used on the evaluation of the template.
     * @return The contents of the processed velocity template.
     */
    public String process(final Reader reader, final Map<String, P> params);

    /**
     * Process the contents of the template defined on the CharSequence.
     *
     * @param templateSource The source-code of the template.
     * @param params         The params to be used on the evaluation of the template.
     * @return The contents of the processed velocity template.
     */
    public String process(final CharSequence templateSource, final Map<String, P> params);
}
