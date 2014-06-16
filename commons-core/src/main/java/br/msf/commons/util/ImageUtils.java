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

import br.msf.commons.util.ArrayUtils;
import br.msf.commons.util.CollectionUtils;
import br.msf.commons.io.exception.ImageIOException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.common.byteSources.ByteSourceArray;
import org.apache.sanselan.formats.jpeg.JpegImageParser;
import org.apache.sanselan.formats.jpeg.segments.UnknownSegment;

/**
 * Class containing utility methods to handle images.
 *
 * @author Marcius da Silva da Fonseca (sf.marcius@gmail.com)
 * @version 1.0
 */
public abstract class ImageUtils {

    /**
     * General class logger.
     */
    protected static final Logger LOGGER = Logger.getLogger(ImageUtils.class.getName());
    /**
     * Color profile used when there is no embedded one.
     */
    private static final String DEFAULT_COLOR_PROFILE = "/br/msf/commons/io/ISOcoated_v2_300_eci.icc";

    /**
     * Util files cannot be instantiated.
     */
    private ImageUtils() {
    }

    /**
     * Closes an ImageInputStream.
     *
     * @param input InputStream to be closed.
     */
    public static void closeQuietly(final ImageInputStream input) {
        if (input != null) {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(ImageUtils.class.getName()).log(Level.WARNING, "Could not close inputstream.", ex);
            }
        }
    }

    public static ImageInputStream getImageInputStream(final byte[] bytes) {
        if (ArrayUtils.isEmptyOrNull(bytes)) {
            return null;
        }
        try {
            return ImageIO.createImageInputStream(new BufferedInputStream(new ByteArrayInputStream(bytes)));
        } catch (IOException ex) {
            throw new ImageIOException(ex);
        }
    }

    public static ImageOutputStream getImageOutputStream(final File outputFile) {
        if (outputFile == null) {
            return null;
        }
        if (outputFile.isDirectory()) {
            throw new IllegalArgumentException("The given file is a directory.");
        }
        final File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try {
            return ImageIO.createImageOutputStream(outputFile);
        } catch (IOException ex) {
            throw new ImageIOException(ex);
        }
    }

    public static ImageOutputStream getImageOutputStream(final OutputStream output) {
        if (output == null) {
            return null;
        }
        try {
            return ImageIO.createImageOutputStream(output);
        } catch (IOException ex) {
            throw new ImageIOException(ex);
        }
    }

    public static ImageReader getImageReader(final byte[] bytes) {
        if (ArrayUtils.isEmptyOrNull(bytes)) {
            return null;
        }
        ImageInputStream is = null;
        try {
            is = getImageInputStream(bytes);
            return getImageReader(is);
        } finally {
            closeQuietly(is);
        }
    }

    public static ImageReader getImageReader(final ImageInputStream input) {
        if (input == null) {
            return null;
        }
        final Iterator<ImageReader> it = ImageIO.getImageReaders(input);
        final ImageReader reader = (CollectionUtils.hasNext(it)) ? it.next() : null;
        if (reader != null && CollectionUtils.hasNext(it)) {
            LOGGER.log(Level.WARNING, "Multiple ImageReaders found. Returning the first one.");
        }
        return reader;
    }

    public static ImageWriter getImageWriter(final ImageFormat format) {
        if (format == null) {
            return null;
        }
        ImageWriter writer = null;
        /* 1st we try to find a reader by the format name */
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName(format.getName());
        if (CollectionUtils.hasNext(it)) {
            writer = it.next();
        } else {
            /* If not found, we try by format mimetype */
            it = ImageIO.getImageWritersByMIMEType(format.getMimetype());
            if (CollectionUtils.hasNext(it)) {
                writer = it.next();
            } else {
                /* If no luck, we use the format extension (sufix)*/
                it = ImageIO.getImageWritersBySuffix(format.getExtension());
                if (CollectionUtils.hasNext(it)) {
                    writer = it.next();
                }
            }
        }
        if (writer != null && CollectionUtils.hasNext(it)) {
            LOGGER.log(Level.WARNING, "Multiple ImageWriters found. Returning the first one.");
        }
        return writer;
    }

    public static BufferedImage readImage(final URL url) {
        if (url == null) {
            return null;
        }
        InputStream is = null;
        try {
            is = url.openStream();
            return readImage(IOUtils.readBytes(is), null);
        } catch (IOException ex) {
            throw new ImageIOException(ex);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static BufferedImage readImage(final URL url, final ImageReader reader) {
        if (url == null) {
            return null;
        }
        InputStream is = null;
        try {
            is = url.openStream();
            return readImage(IOUtils.readBytes(is), reader);
        } catch (IOException ex) {
            throw new ImageIOException(ex);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static BufferedImage readImage(final File file) {
        if (file == null) {
            return null;
        }
        return readImage(IOUtils.readBytes(file), null);
    }

    public static BufferedImage readImage(final File file, final ImageReader reader) {
        if (file == null) {
            return null;
        }
        return readImage(IOUtils.readBytes(file), reader);
    }

    public static BufferedImage readImage(final byte[] bytes) {
        if (ArrayUtils.isEmptyOrNull(bytes)) {
            return null;
        }
        return readImage(bytes, null);
    }

    public static BufferedImage readImage(final byte[] bytes, final ImageReader reader) {
        if (ArrayUtils.isEmptyOrNull(bytes)) {
            return null;
        }
        final ImageReader internalReader = (reader != null) ? reader : getImageReader(bytes);
        ImageInputStream is = null;
        try {
            is = getImageInputStream(bytes);
            internalReader.setInput(is, false, false);
            return internalReader.read(0);
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "Could not read image as RGB color space. Trying to decode as YCCK.");
            /* probabbly the image dont uses RGB colorSpace, lets try to convert to RGB */
            return readConvert(bytes, internalReader);
        } finally {
            closeQuietly(is);
        }
    }

    public static Icon getIcon(final byte[] bytes) {
        if (ArrayUtils.isEmptyOrNull(bytes)) {
            return null;
        }
        return getIcon(readImage(bytes));
    }

    public static Icon getIcon(final BufferedImage image) {
        if (image == null) {
            return null;
        }
        return new ImageIcon(image);
    }

    public static boolean isPng(final URL url) {
        final ImageFormat format = getImageFormat(url);
        return format != null && format.isPng();
    }

    public static boolean isPng(final File file) {
        final ImageFormat format = getImageFormat(file);
        return format != null && format.isPng();
    }

    public static boolean isPng(final byte[] bytes) {
        final ImageFormat format = getImageFormat(bytes);
        return format != null && format.isPng();
    }

    public static boolean isJpeg(final URL url) {
        final ImageFormat format = getImageFormat(url);
        return format != null && format.isJpeg();
    }

    public static boolean isJpeg(final File file) {
        final ImageFormat format = getImageFormat(file);
        return format != null && format.isJpeg();
    }

    public static boolean isJpeg(final byte[] bytes) {
        final ImageFormat format = getImageFormat(bytes);
        return format != null && format.isJpeg();
    }

    public static boolean isBmp(final URL url) {
        final ImageFormat format = getImageFormat(url);
        return format != null && format.isBmp();
    }

    public static boolean isBmp(final File file) {
        final ImageFormat format = getImageFormat(file);
        return format != null && format.isBmp();
    }

    public static boolean isBmp(final byte[] bytes) {
        final ImageFormat format = getImageFormat(bytes);
        return format != null && format.isBmp();
    }

    public static boolean isGif(final URL url) {
        final ImageFormat format = getImageFormat(url);
        return format != null && format.isGif();
    }

    public static boolean isGif(final File file) {
        final ImageFormat format = getImageFormat(file);
        return format != null && format.isGif();
    }

    public static boolean isGif(final byte[] bytes) {
        final ImageFormat format = getImageFormat(bytes);
        return format != null && format.isGif();
    }

    public static ImageFormat getImageFormat(final URL url) {
        if (url == null) {
            return null;
        }
        try {
            return getImageFormat(IOUtils.readBytes(url.openStream()));
        } catch (IOException ex) {
            throw new ImageIOException("Could not open image url stream.");
        }
    }

    public static ImageFormat getImageFormat(final File file) {
        if (file == null) {
            return null;
        }
        return getImageFormat(IOUtils.readBytes(file));
    }

    public static ImageFormat getImageFormat(final byte[] bytes) {
        if (ArrayUtils.isEmptyOrNull(bytes)) {
            return null;
        }
        try {
            /* try with ImageIO */
            final String formatName = getImageReader(bytes).getFormatName();
            if (ImageFormat.BMP.getName().equalsIgnoreCase(formatName)) {
                return ImageFormat.BMP;
            } else if (ImageFormat.GIF.getName().equalsIgnoreCase(formatName)) {
                return ImageFormat.GIF;
            } else if (ImageFormat.JPEG.getName().equalsIgnoreCase(formatName)) {
                return ImageFormat.JPEG;
            } else if (ImageFormat.PNG.getName().equalsIgnoreCase(formatName)) {
                return ImageFormat.PNG;
            } else {
                throw new ImageIOException("Unsupported image format");
            }
        } catch (Exception ex) {
            throw new ImageIOException("Given bytes do not seem to be an image.", ex);
        }
    }

    public static byte[] getPngBytes(final BufferedImage image) {
        return getImageBytes(image, ImageFormat.PNG);
    }

    public static byte[] getJpegBytes(final BufferedImage image) {
        return getJpegBytes(image, null);
    }

    public static byte[] getJpegBytes(final BufferedImage image, final Float imageQuality) {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            writeJpeg(image, getImageOutputStream(baos), imageQuality);
            return baos.toByteArray();
        } finally {
            IOUtils.closeQuietly(baos);
        }
    }

    public static byte[] getBmpBytes(final BufferedImage image) {
        return getImageBytes(image, ImageFormat.BMP);
    }

    public static byte[] getGifBytes(final BufferedImage image) {
        return getImageBytes(image, ImageFormat.GIF);
    }

    public static byte[] getImageBytes(final BufferedImage image, final ImageFormat format) {
        if (image == null) {
            return null;
        }
        if (format == null) {
            throw new IllegalArgumentException(
                    "Man... dont get me wrong, but how the hell am I suppose to get bytes without knowing the target ImageFormat???");
        }
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            writeImage(image, getImageOutputStream(baos), getImageWriter(format));
            return baos.toByteArray();
        } finally {
            IOUtils.closeQuietly(baos);
        }
    }

    public static byte[] toJpeg(final byte[] bytes) {
        return toJpeg(bytes, false);
    }

    public static byte[] toJpeg(final byte[] bytes, final boolean force) {
        if (isJpeg(bytes) && !force) {
            return bytes;
        }
        return getJpegBytes(readImage(bytes));
    }

    public static byte[] toPng(final byte[] bytes) {
        return toPng(bytes, false);
    }

    public static byte[] toPng(final byte[] bytes, final boolean force) {
        if (isPng(bytes) && !force) {
            return bytes;
        }
        return getPngBytes(readImage(bytes));
    }

    public static byte[] toBmp(final byte[] bytes) {
        return toBmp(bytes, false);
    }

    public static byte[] toBmp(final byte[] bytes, final boolean force) {
        if (isBmp(bytes) && !force) {
            return bytes;
        }
        return getBmpBytes(readImage(bytes));
    }

    public static byte[] toGif(final byte[] bytes) {
        return toGif(bytes, false);
    }

    public static byte[] toGif(final byte[] bytes, final boolean force) {
        if (isGif(bytes) && !force) {
            return bytes;
        }
        return getGifBytes(readImage(bytes));
    }

    public static void writePng(final byte[] bytes, final File outputFile) {
        writeImage(readImage(bytes), getImageOutputStream(outputFile), getImageWriter(ImageFormat.PNG));
    }

    public static void writePng(final byte[] bytes, final ImageOutputStream output) {
        writeImage(readImage(bytes), output, getImageWriter(ImageFormat.PNG));
    }

    public static void writePng(final BufferedImage image, final File outputFile) {
        writeImage(image, getImageOutputStream(outputFile), getImageWriter(ImageFormat.PNG));
    }

    public static void writePng(final BufferedImage image, final ImageOutputStream output) {
        writeImage(image, output, getImageWriter(ImageFormat.PNG));
    }

    public static void writeJpeg(final byte[] bytes, final File outputFile) {
        writeJpeg(readImage(bytes), getImageOutputStream(outputFile), null);
    }

    public static void writeJpeg(final byte[] bytes, final ImageOutputStream output) {
        writeJpeg(readImage(bytes), output, null);
    }

    public static void writeJpeg(final byte[] bytes, final File outputFile, final Float imageQuality) {
        writeJpeg(readImage(bytes), getImageOutputStream(outputFile), imageQuality);
    }

    public static void writeJpeg(final byte[] bytes, final ImageOutputStream output, final Float imageQuality) {
        writeJpeg(readImage(bytes), output, imageQuality);
    }

    public static void writeJpeg(final BufferedImage image, final File outputFile) {
        writeJpeg(image, getImageOutputStream(outputFile), null);
    }

    public static void writeJpeg(final BufferedImage image, final ImageOutputStream output) {
        writeJpeg(image, output, null);
    }

    public static void writeJpeg(final BufferedImage image, final File outputFile, final Float imageQuality) {
        writeJpeg(image, getImageOutputStream(outputFile), imageQuality);
    }

    public static void writeJpeg(final BufferedImage image, final ImageOutputStream output, final Float imageQuality) {
        if (image == null || output == null) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.INFO, "Nothing to write.");
            return;
        }
        if (imageQuality != null && (imageQuality < 0 || imageQuality > 1)) {
            throw new IllegalArgumentException("Invalid image quality. Must be a float between 0 (zero) and 1 (one).");
        }
        final BufferedImage targetImage = removeTransparency(image, Color.WHITE);
        if (imageQuality == null) {
            writeImage(targetImage, output, getImageWriter(ImageFormat.JPEG));
        } else {
            final ImageWriter writer = getImageWriter(ImageFormat.JPEG);
            try {
                writer.setOutput(output);
                final IIOImage ioimage = new IIOImage(targetImage, null, null);
                final ImageWriteParam writerParam = writer.getDefaultWriteParam();
                writerParam.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
                writerParam.setCompressionQuality(imageQuality);
                writer.write(null, ioimage, writerParam);
            } catch (IOException ex) {
                throw new ImageIOException(ex);
            } finally {
                writer.dispose();
                closeQuietly(output);
            }
        }
    }

    public static void writeBmp(final byte[] bytes, final File outputFile) {
        writeImage(removeTransparency(bytes), getImageOutputStream(outputFile), getImageWriter(ImageFormat.PNG));
    }

    public static void writeBmp(final byte[] bytes, final ImageOutputStream output) {
        writeImage(removeTransparency(bytes), output, getImageWriter(ImageFormat.PNG));
    }

    public static void writeBmp(final BufferedImage image, final File outputFile) {
        writeImage(removeTransparency(image), getImageOutputStream(outputFile), getImageWriter(ImageFormat.PNG));
    }

    public static void writeBmp(final BufferedImage image, final ImageOutputStream output) {
        writeImage(removeTransparency(image), output, getImageWriter(ImageFormat.PNG));
    }

    public static void writeGif(final byte[] bytes, final File outputFile) {
        writeImage(removeTransparency(bytes), getImageOutputStream(outputFile), getImageWriter(ImageFormat.GIF));
    }

    public static void writeGif(final byte[] bytes, final ImageOutputStream output) {
        writeImage(removeTransparency(bytes), output, getImageWriter(ImageFormat.GIF));
    }

    public static void writeGif(final BufferedImage image, final File outputFile) {
        writeImage(removeTransparency(image), getImageOutputStream(outputFile), getImageWriter(ImageFormat.GIF));
    }

    public static void writeGif(final BufferedImage image, final ImageOutputStream output) {
        writeImage(removeTransparency(image), output, getImageWriter(ImageFormat.GIF));
    }

    public static void writeImage(final byte[] bytes, final File outputFile) {
        final ImageFormat format = getImageFormat(bytes);
        if (format != null) {
            if (outputFile != null && !outputFile.getName().toLowerCase().endsWith("." + format.getExtension())) {
                throw new IllegalArgumentException("File name must be a '." + format.getExtension() + "' one.");
            }
            writeImage(readImage(bytes), getImageOutputStream(outputFile), getImageWriter(format));
        }
    }

    public static void writeImage(final byte[] bytes, final ImageOutputStream output) {
        final ImageFormat format = getImageFormat(bytes);
        if (format != null) {
            writeImage(readImage(bytes), output, getImageWriter(format));
        }
    }

    public static void writeImage(final byte[] bytes, final File outputFile, final ImageWriter writer) {
        writeImage(readImage(bytes), getImageOutputStream(outputFile), writer);
    }

    public static void writeImage(final byte[] bytes, final ImageOutputStream output, final ImageWriter writer) {
        writeImage(readImage(bytes), output, writer);
    }

    public static void writeImage(final BufferedImage image, final File outputFile, final ImageWriter writer) {
        writeImage(image, getImageOutputStream(outputFile), writer);
    }

    public static void writeImage(final BufferedImage image, final ImageOutputStream output, final ImageWriter writer) {
        if (image == null || output == null || writer == null) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.INFO, "Nothing to write.");
            return;
        }
        try {
            writer.setOutput(output);
            writer.write(image);
        } catch (IOException ex) {
            throw new ImageIOException(ex);
        } finally {
            writer.dispose();
            closeQuietly(output);
        }
    }

    public static BufferedImage removeTransparency(final byte[] bytes) {
        return removeTransparency(readImage(bytes), Color.WHITE);
    }

    public static BufferedImage removeTransparency(final byte[] bytes, final Color replacementColor) {
        return removeTransparency(readImage(bytes), replacementColor);
    }

    public static BufferedImage removeTransparency(final BufferedImage image) {
        return removeTransparency(image, Color.WHITE);
    }

    public static BufferedImage removeTransparency(final BufferedImage image, final Color replacementColor) {
        if (image == null) {
            return null;
        }
        final BufferedImage opaqueImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = getGraphics(opaqueImage);
        g.drawImage(image, 0, 0, opaqueImage.getWidth(), opaqueImage.getHeight(), replacementColor, null);
        g.dispose();
        return opaqueImage;
    }

    public static BufferedImage scaleImage(final byte[] bytes, final int scaledWidth, final int scaledHeight, final boolean keepAspectRatio) {
        return scaleImage(readImage(bytes), scaledWidth, scaledHeight, keepAspectRatio);
    }

    public static BufferedImage scaleImage(final BufferedImage image, final int scaledWidth, final int scaledHeight, final boolean keepAspectRatio) {
        if (image == null) {
            return null;
        }
        if (scaledWidth < 0 || scaledHeight < 0) {
            throw new IllegalArgumentException("Invalid width and/or heigth.");
        }
        final BufferedImage scaledImage;
        if (keepAspectRatio) {
            final int proportionalHeight = scaledWidth * image.getHeight() / image.getWidth();
            if (proportionalHeight < scaledHeight) {
                scaledImage = createCompatibleImage(image, scaledWidth, proportionalHeight);
            } else {
                scaledImage = createCompatibleImage(image, scaledWidth, scaledHeight);
            }
            int diff = proportionalHeight - scaledHeight;
            if (diff < 0) {
                diff = 0;
            }
            final Graphics2D g = getGraphics(scaledImage);
            g.drawImage(image, 0, -(diff / 2), scaledWidth, proportionalHeight, null);
            g.dispose();
        } else {
            scaledImage = createCompatibleImage(image, scaledWidth, scaledHeight);
            final Graphics2D g = getGraphics(scaledImage);
            g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
            g.dispose();
        }
        return scaledImage;
    }

    public static BufferedImage createCompatibleImage(final BufferedImage image) {
        if (image == null) {
            return null;
        }
        return createCompatibleImage(image, image.getWidth(), image.getHeight());
    }

    public static BufferedImage createCompatibleImage(final BufferedImage image, final int width, final int height) {
        if (image == null) {
            return null;
        }
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("width and height must be pozitive.");
        }
        return new BufferedImage(width, height, image.getType());
    }

    public static BufferedImage createTransparentImage(final int width, final int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("width and height must be pozitive.");
        }
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = getGraphics(image);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
        g.fill(new Rectangle2D.Double(0, 0, width, height));
        g.dispose();
        return image;
    }

    public static BufferedImage copy(final BufferedImage image) {
        if (image == null) {
            return null;
        }
        final BufferedImage copyImage = createCompatibleImage(image);
        Graphics2D g = getGraphics(copyImage);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();
        return copyImage;
    }

    public static BufferedImage contrastImage(final URL url, final float contrastRatio) {
        if (url == null) {
            return null;
        }
        return contrastImage(readImage(url), contrastRatio);
    }

    public static BufferedImage contrastImage(final File file, final float contrastRatio) {
        if (file == null) {
            return null;
        }
        return contrastImage(readImage(file), contrastRatio);
    }

    public static BufferedImage contrastImage(final byte[] bytes, final float contrastRatio) {
        if (ArrayUtils.isEmptyOrNull(bytes)) {
            return null;
        }
        return contrastImage(readImage(bytes), contrastRatio);
    }

    public static BufferedImage contrastImage(final BufferedImage image, final float contrastRatio) {
        if (image == null) {
            return null;
        }
        if (contrastRatio == 1F) {
            return copy(image);
        }
        final BufferedImage contrastImage = copy(image);
        final RescaleOp rescaleOp = new RescaleOp(contrastRatio, 15, null);
        rescaleOp.filter(image, contrastImage);
        return contrastImage;
    }

    public static ImagePoint[] getTrimmedSelection(final BufferedImage image) {
        if (image == null) {
            return null;
        }
        int left = 0;
        int top = 0;
        int right = image.getWidth();
        int bottom = image.getHeight();

        /* apply a contrast filter to remove noise */
        final BufferedImage scanImage = contrastImage(image, 1.2F);

        /* look for the first line that has any non-blank pixel */
        for (int y = top; y < bottom; y++) {
            boolean found = false;
            for (int x = left; x < right; x++) {
                if (scanImage.getRGB(x, y) != Color.WHITE.getRGB()) {
                    found = true;
                }
            }
            if (found) {
                top = y;
                break;
            }
        }
        if (top >= bottom) {
            /* scanned all lines and doesnt found any non-blank pixel */
            return null;
        }

        /* look for the last line that has any non-blank pixel */
        for (int y = (bottom - 1); y > top; y--) {
            boolean found = false;
            for (int x = left; x < right; x++) {
                if (scanImage.getRGB(x, y) != Color.WHITE.getRGB()) {
                    found = true;
                }
            }
            if (found) {
                bottom = y;
                break;
            }
        }

        /* look for the first column that has any non-blank pixel */
        for (int x = left; x < right; x++) {
            boolean found = false;
            for (int y = top; y < bottom; y++) {
                if (scanImage.getRGB(x, y) != Color.WHITE.getRGB()) {
                    found = true;
                }
            }
            if (found) {
                left = x;
                break;
            }
        }

        /* look for the last column that has any non-blank pixel */
        for (int x = (right - 1); x > left; x--) {
            boolean found = false;
            for (int y = top; y < bottom; y++) {
                if (scanImage.getRGB(x, y) != Color.WHITE.getRGB()) {
                    found = true;
                }
            }
            if (found) {
                right = x;
                break;
            }
        }
        return new ImagePoint[]{new ImagePoint(left, top), new ImagePoint(right, bottom)};
    }

    public static BufferedImage trimImage(final URL url) {
        if (url == null) {
            return null;
        }
        return trimImage(readImage(url));
    }

    public static BufferedImage trimImage(final File file) {
        if (file == null) {
            return null;
        }
        return trimImage(readImage(file));
    }

    public static BufferedImage trimImage(final byte[] bytes) {
        if (ArrayUtils.isEmptyOrNull(bytes)) {
            return null;
        }
        return trimImage(readImage(bytes));
    }

    public static BufferedImage trimImage(final BufferedImage image) {
        if (image == null) {
            return null;
        }
        final ImagePoint[] trimmedSelection = getTrimmedSelection(image);
        if (trimmedSelection == null) {
            return null;
        }
        return cropImage(image, trimmedSelection[0], trimmedSelection[1]);
    }

    public static BufferedImage cropImage(final BufferedImage image, final ImagePoint leftTop, final ImagePoint rightBottom) {
        if (image == null) {
            return null;
        }
        final ImagePoint max = new ImagePoint(image.getWidth(), image.getHeight());
        final ImagePoint[] selection = getValidSelection(leftTop, rightBottom, max);
        final ImagePoint lt = selection[0];
        final ImagePoint rb = selection[1];
        final int selectionWidth = rb.getX() - lt.getX();
        final int selectionHeight = rb.getY() - lt.getY();
        final BufferedImage cropCanvas = createCompatibleImage(image, selectionWidth, selectionHeight);
        final Graphics2D g = getGraphics(cropCanvas);
        g.drawImage(image,
                    0, 0, selectionWidth, selectionHeight, /* dest: leftTop(x, y), rightBottom(x, y) */
                    lt.getX(), lt.getY(), rb.getX(), rb.getY(), /* src: leftTop(x, y), rightBottom(x, y) */
                    null);
        g.dispose();
        return cropCanvas;
    }

    public static ImagePoint[] getValidSelection(final ImagePoint coord0, final ImagePoint coord1, final ImagePoint max) {
        if (max == null) {
            throw new IllegalArgumentException("Must know the max point to adjust.");
        }
        ImagePoint lt = coord0;
        ImagePoint rb = coord1;
        if (lt == null) {
            lt = new ImagePoint(0, 0);
        }
        if (rb == null) {
            rb = new ImagePoint(max.getX(), max.getY());
        }
        /* swap X if necessary */
        if (lt.getX() > rb.getX()) {
            int tmp = lt.getX();
            lt.setX(rb.getX());
            rb.setX(tmp);
        }
        /* swap Y if necessary */
        if (lt.getY() > rb.getY()) {
            int tmp = lt.getY();
            lt.setY(rb.getY());
            rb.setY(tmp);
        }
        /* check if leftTop is inside the max */
        if (lt.isRightOf(max) || lt.isBottomOf(max)) {
            throw new IllegalArgumentException("Invalid coordinates.");
        }
        /* check if rightBottom is inside the max */
        if (rb.isRightOf(max)) {
            rb.setX(max.getX());
        }
        if (rb.isBottomOf(max)) {
            rb.setY(max.getY());
        }
        return new ImagePoint[]{lt, rb};
    }

    public static Graphics2D getGraphics(final BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Cant create a canvas for null image.");
        }
        Graphics2D g = image.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g;
    }

    public static boolean isCommonRGB(final byte[] bytes) {
        if (ArrayUtils.isEmptyOrNull(bytes)) {
            return false;
        }
        final Object[] colorSpaceInfo = getColorSpaceInfo(bytes);
        final ColorSpace colorSpace = (ColorSpace) colorSpaceInfo[0];
        final Boolean isAdobeMarked = (Boolean) colorSpaceInfo[1];
        return ColorSpace.RGB.equals(colorSpace) && !isAdobeMarked;
    }

    public static Object[] getColorSpaceInfo(final byte[] bytes) {
        if (ArrayUtils.isEmptyOrNull(bytes)) {
            return null;
        }
        final ImageReader internalReader = getImageReader(bytes);
        ImageInputStream is = null;
        try {
            is = getImageInputStream(bytes);
            internalReader.setInput(is, false, false);
            internalReader.read(0);
            return new Object[]{ColorSpace.RGB, Boolean.FALSE};
        } catch (IOException ex) {
            return checkAdobeMarker(bytes);
        } finally {
            closeQuietly(is);
        }
    }

    private static BufferedImage readConvert(final byte[] bytes, final ImageReader reader) {
        ImageInputStream is = null;
        try {
            is = getImageInputStream(bytes);
            reader.setInput(is, false, false);
            final WritableRaster raster = (WritableRaster) reader.readRaster(0, null);
            final Object[] colorSpaceInfo = checkAdobeMarker(bytes);
            if (ColorSpace.isYCCK((ColorSpace) colorSpaceInfo[0])) {
                convertYcckToCmyk(raster);
            }
            if ((Boolean) colorSpaceInfo[1]) {
                convertInvertedColors(raster);
            }
            final ICC_Profile profile = Sanselan.getICCProfile(bytes);
            return convertCmykToRgb(raster, profile);
        } catch (Exception ex) {
            throw new ImageIOException(ex);
        } finally {
            closeQuietly(is);
        }
    }

    private static Object[] checkAdobeMarker(final byte[] bytes) {
        try {
            // array with [colorSpace, isAdobeMarked]
            final Object[] colorSpaceInfo = {ColorSpace.CMYK, Boolean.FALSE};
            final JpegImageParser parser = new JpegImageParser();
            final ByteSource byteSource = new ByteSourceArray(bytes);
            @SuppressWarnings("rawtypes")
            final ArrayList segments = parser.readSegments(byteSource, new int[]{0xffee}, true);
            if (segments != null && segments.size() >= 1) {
                final UnknownSegment app14Segment = (UnknownSegment) segments.get(0);
                byte[] data = app14Segment.bytes;
                if (data.length >= 12 && data[0] == 'A' && data[1] == 'd' && data[2] == 'o' && data[3] == 'b' && data[4] == 'e') {
                    colorSpaceInfo[1] = Boolean.TRUE; // isAdobeMarked
                    final int transform = app14Segment.bytes[11] & 0xff;
                    if (transform == 2) {
                        colorSpaceInfo[0] = ColorSpace.YCCK;
                    }
                }
            }
            return colorSpaceInfo;
        } catch (Exception ex) {
            throw new ImageIOException(ex);
        }
    }

    private static void convertYcckToCmyk(final WritableRaster raster) {
        int height = raster.getHeight();
        int width = raster.getWidth();
        int stride = width * 4;
        int[] pixelRow = new int[stride];
        for (int h = 0; h < height; h++) {
            raster.getPixels(0, h, width, 1, pixelRow);

            for (int x = 0; x < stride; x += 4) {
                int y = pixelRow[x];
                int cb = pixelRow[x + 1];
                int cr = pixelRow[x + 2];

                int c = (int) (y + 1.402 * cr - 178.956);
                int m = (int) (y - 0.34414 * cb - 0.71414 * cr + 135.95984);
                y = (int) (y + 1.772 * cb - 226.316);

                if (c < 0) {
                    c = 0;
                } else if (c > 255) {
                    c = 255;
                }
                if (m < 0) {
                    m = 0;
                } else if (m > 255) {
                    m = 255;
                }
                if (y < 0) {
                    y = 0;
                } else if (y > 255) {
                    y = 255;
                }

                pixelRow[x] = 255 - c;
                pixelRow[x + 1] = 255 - m;
                pixelRow[x + 2] = 255 - y;
            }

            raster.setPixels(0, h, width, 1, pixelRow);
        }
    }

    private static void convertInvertedColors(final WritableRaster raster) {
        int height = raster.getHeight();
        int width = raster.getWidth();
        int stride = width * 4;
        int[] pixelRow = new int[stride];
        for (int h = 0; h < height; h++) {
            raster.getPixels(0, h, width, 1, pixelRow);
            for (int x = 0; x < stride; x++) {
                pixelRow[x] = 255 - pixelRow[x];
            }
            raster.setPixels(0, h, width, 1, pixelRow);
        }
    }

    private static BufferedImage convertCmykToRgb(final Raster cmykRaster, final ICC_Profile cmykProfile) throws IOException {
        ICC_Profile profile = cmykProfile;
        if (profile == null) {
            profile = ICC_Profile.getInstance(IOUtils.getClasspathInputStream(DEFAULT_COLOR_PROFILE));
        }
        if (profile.getProfileClass() != ICC_Profile.CLASS_DISPLAY) {
            byte[] profileData = profile.getData(); // Need to clone entire profile, due to a JDK 7 bug
            if (profileData[ICC_Profile.icHdrRenderingIntent] == ICC_Profile.icPerceptual) {
                /*
                 * Adjust the orientation of the color profile header bits used
                 * on the CMYK conversion, passing it to big-endian.
                 */
                profileData[ICC_Profile.icHdrDeviceClass] = (byte) (ICC_Profile.icSigDisplayClass >> 24);
                profileData[ICC_Profile.icHdrDeviceClass + 1] = (byte) (ICC_Profile.icSigDisplayClass >> 16);
                profileData[ICC_Profile.icHdrDeviceClass + 2] = (byte) (ICC_Profile.icSigDisplayClass >> 8);
                profileData[ICC_Profile.icHdrDeviceClass + 3] = (byte) (ICC_Profile.icSigDisplayClass);
                profile = ICC_Profile.getInstance(profileData);
            }
        }

        final ICC_ColorSpace cmykCS = new ICC_ColorSpace(profile);
        final BufferedImage rgbImage = new BufferedImage(cmykRaster.getWidth(), cmykRaster.getHeight(), BufferedImage.TYPE_INT_RGB);
        final WritableRaster rgbRaster = rgbImage.getRaster();
        final java.awt.color.ColorSpace rgbCS = rgbImage.getColorModel().getColorSpace();
        final ColorConvertOp cmykToRgb = new ColorConvertOp(cmykCS, rgbCS, null);
        cmykToRgb.filter(cmykRaster, rgbRaster);
        return rgbImage;
    }

    public enum ColorSpace {

        RGB,
        CMYK,
        YCCK;

        static boolean isRGB(final ColorSpace colorSpace) {
            return colorSpace != null && RGB.equals(colorSpace);
        }

        static boolean isCMYK(final ColorSpace colorSpace) {
            return colorSpace != null && CMYK.equals(colorSpace);
        }

        static boolean isYCCK(final ColorSpace colorSpace) {
            return colorSpace != null && YCCK.equals(colorSpace);
        }
    }

    public enum ImageFormat {

        JPEG("Joint Photographic Experts Group (JPEG)", "jpeg", "jpg", "image/jpeg"),
        PNG("Portable Network Graphics (PNG)", "png", "png", "image/png"),
        GIF("Graphics Interchange Format (GIF)", "gif", "gif", "image/gif"),
        BMP("Microsoft Windows Bitmap (BMP)", "bmp", "bmp", "image/bmp");
        private final String description;
        private final String name;
        private final String extension;
        private final String mimetype;

        private ImageFormat(final String description, final String name, final String extension, final String mimetype) {
            this.description = description;
            this.name = name;
            this.extension = extension;
            this.mimetype = mimetype;
        }

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public String getExtension() {
            return extension;
        }

        public String getMimetype() {
            return mimetype;
        }

        public boolean isJpeg() {
            return JPEG.equals(this);
        }

        public boolean isPng() {
            return PNG.equals(this);
        }

        public boolean isGif() {
            return GIF.equals(this);
        }

        public boolean isBmp() {
            return BMP.equals(this);
        }
    }

    public static class ImagePoint {

        /**
         * horizontal coord.
         */
        private int x;
        /**
         * vertical coord.
         */
        private int y;

        public ImagePoint() {
            this(0, 0);
        }

        public ImagePoint(final int x, final int y) {
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("Coordinates must be positive values.");
            }
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(final int x) {
            if (x < 0) {
                throw new IllegalArgumentException("Coordinates must be positive values.");
            }
            this.x = x;
        }

        public int getY() {
            if (y < 0) {
                throw new IllegalArgumentException("Coordinates must be positive values.");
            }
            return y;
        }

        public void setY(final int y) {
            this.y = y;
        }

        public boolean isLeftOf(final ImagePoint point) {
            return point != null && getX() < point.getX();
        }

        public boolean isRightOf(final ImagePoint point) {
            return point != null && getX() > point.getX();
        }

        public boolean isTopOf(final ImagePoint point) {
            return point != null && getY() < point.getY();
        }

        public boolean isBottomOf(final ImagePoint point) {
            return point != null && getY() > point.getY();
        }

        public boolean isLeftTopOf(final ImagePoint point) {
            return isLeftOf(point) && isTopOf(point);
        }

        public boolean isRightBottomOf(final ImagePoint point) {
            return isRightOf(point) && isBottomOf(point);
        }

        @Override
        public String toString() {
            return "P(x=" + x + ", y=" + y + ")";
        }
    }
}
