package com.wwi21sebgroup5.cinema.config;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class ImageCompressor {

    /**
     * Die Methoden compressImage und decompressImage können zu späterem Zeitpunkt durch funktionierende Methoden ersetzt werden
     */

    public static byte[] compressImage(byte[] input) throws IOException {
        /*
        int compressionLevel = Deflater.BEST_COMPRESSION;
        boolean GZIPFormat = false;
        // Create a Deflater object to compress data
        Deflater compressor = new Deflater(compressionLevel, GZIPFormat);
        System.out.println(input.length);
        // Set the input for the compressor
        compressor.setInput(input);

        // Call the finish() method to indicate that we have
        // no more input for the compressor object
        compressor.finish();

        // Compress the data
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] readBuffer = new byte[1024];
        int readCount = 0;

        while (!compressor.finished()) {
            readCount = compressor.deflate(readBuffer);
            if (readCount > 0) {
                // Write compressed data to the output stream
                bao.write(readBuffer, 0, readCount);
            }
        }

        // End the compressor
        compressor.end();
        System.out.println(bao.toByteArray().length);
        // Return the written bytes from output stream
        return bao.toByteArray();

         */
        return input;
    }

    public static byte[] decompressImage(byte[] input)
            throws IOException, DataFormatException {

        /*System.out.println(input.length);
        boolean GZIPFormat = false;
        // Create an Inflater object to compress the data
        Inflater decompressor = new Inflater(GZIPFormat);

        // Set the input for the decompressor
        decompressor.setInput(input);

        // Decompress data
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] readBuffer = new byte[1024];
        int readCount = 0;

        while (!decompressor.finished()) {
            readCount = decompressor.inflate(readBuffer);
            if (readCount > 0) {
                // Write the data to the output stream
                bao.write(readBuffer, 0, readCount);
            }
        }

        // End the decompressor
        decompressor.end();
        System.out.println(bao.toByteArray().length);
        // Return the written bytes from the output stream
        return bao.toByteArray();

         */
        return input;
    }
}