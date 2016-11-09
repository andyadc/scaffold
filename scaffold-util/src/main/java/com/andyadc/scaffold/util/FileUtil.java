package com.andyadc.scaffold.util;

import java.io.*;

/**
 * File util class
 *
 * @author andaicheng
 */
public class FileUtil {
    private static final int BUFFER_SIZE = 16 * 1024;


    private FileUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Read input stream as bytes
     *
     * @param inputStream input stream
     * @return byte array from input stream.
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[16 * 1024];

        int bytesRead = inputStream.read(buffer);
        while (bytesRead != -1) {
            outputStream.write(buffer, 0, bytesRead);
            bytesRead = inputStream.read(buffer);
        }

        return outputStream.toByteArray();
    }

    /**
     * Read a file as string
     *
     * @param filePath file path
     * @return string from a file path.
     * @throws IOException
     */
    public static String readFileAsString(String filePath) throws IOException {
        byte[] buffer = new byte[(int) getFile(filePath).length()];
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(getFile(filePath)));
            bis.read(buffer);
        } finally {
            if (bis != null) {
                bis.close();
            }
        }
        return new String(buffer);
    }

    /**
     * Write string to a file
     *
     * @param content content
     * @param filePath file path
     * @throws Exception
     */
    public static void writeStringToFile(String content, String filePath)
            throws Exception {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(getFile(filePath)));
            bos.write(content.getBytes());
            bos.flush();
        } finally {
            if (bos != null) {
                bos.close();
            }
        }
    }

    /**
     * Get file from a file path.
     *
     * @param filePath file path
     * @return the file object of specified file path
     * @throws IOException
     */
    public static File getFile(String filePath) throws IOException {
        return new File(filePath);
    }

    /**
     * Copy a file
     *
     * @param src source
     * @param dst destination
     * @throws IOException
     */
    public static void copy(File src, File dst) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            while (in.read(buffer) > 0) {
                out.write(buffer);
            }
        } finally {
            if (null != in) {
                in.close();
            }
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * Get a file suffix name
     *
     * @param file file
     * @return file suffix name.
     */
    public static String suffix(File file) {
        String name = file.getName();
        int pos = name.lastIndexOf('.');
        return name.substring(pos);
    }

    /**
     * Get a file suffix name
     *
     * @param fileName filename
     * @return file suffix name
     */
    public static String suffix(String fileName) {
        int pos = fileName.lastIndexOf('.');
        return fileName.substring(pos);
    }

    /**
     * Delete a file according to a file path
     *
     * @param filePath file path
     * @return true if and only if the file or directory is successfully deleted; false otherwise
     * @throws IOException
     */
    public static boolean delete(String filePath) throws IOException {
        File file = getFile(filePath);
        return file.delete();
    }
}
