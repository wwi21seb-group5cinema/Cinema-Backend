package com.wwi21sebgroup5.cinema.helper;

import com.wwi21sebgroup5.cinema.exceptions.ImageCouldNotBeCompressedException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ImageCompressor {


    //Maximum size of an Image in the database
    public static final int MAX_IMAGE_SIZE_IN_BYTE = 50000; //50kB

    /**
     * @param bytes
     * @param imageQuality Wird genutzt, um das Bild schrittweise zu komprimieren, bis die Maximalgröße unterschritten wurde, der die Qualität zu gering ist.
     *                     Sollte beim ersten Aufruf 1 sein
     * @param type         gibt den typ des Bilds an -> z.B.  "image/png" oder "image/jpeg"
     * @return Byte-Array mit den komprimierten Bild Daten
     * @throws IOException                        wenn beim Lesen und schreiben der streams etwas fehlschlägt
     * @throws ImageCouldNotBeCompressedException wenn die Qualität des Bildes zu stark eingeschränkt wird
     */
    public static byte[] compressImage(byte[] bytes, float imageQuality, String type) throws IOException, ImageCouldNotBeCompressedException {

        if (bytes.length <= MAX_IMAGE_SIZE_IN_BYTE) {
            return bytes;
        }
        if (imageQuality < 0.5) {
            throw new ImageCouldNotBeCompressedException();
        }
        // data is written into a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Get image writers
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByMIMEType(
                type); // Input your Format Name here

        if (!imageWriters.hasNext()) {
            throw new IllegalStateException("Writers Not Found!!");
        }
        ImageWriter imageWriter = imageWriters.next();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);

        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

        // Set the compress quality metrics
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(imageQuality);

        // Create the buffered image
        InputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        //Remove alpha channel to avoid (bogus input errors)
        bufferedImage = removeAlphaChannel(bufferedImage);

        // Compress and insert the image into the byte array.
        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);

        // close all streams
        inputStream.close();
        outputStream.close();
        imageOutputStream.close();
        imageWriter.dispose();

        bytes = outputStream.toByteArray();

        return compressImage(bytes, imageQuality - .05f, type);

    }

    private static BufferedImage removeAlphaChannel(BufferedImage img) {
        if (!img.getColorModel().hasAlpha()) {
            return img;
        }

        BufferedImage target = createImage(img.getWidth(), img.getHeight(), false);
        Graphics2D g = target.createGraphics();
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return target;
    }

    private static BufferedImage createImage(int width, int height, boolean hasAlpha) {
        return new BufferedImage(width, height,
                hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
    }
}
