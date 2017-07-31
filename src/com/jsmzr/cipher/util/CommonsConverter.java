package com.jsmzr.cipher.util;

import java.io.UnsupportedEncodingException;

public class CommonsConverter {
    private final static String HEX_STRING = "0123456789abcdef";
    private final static String DEFAULT_ENCODING = "utf-8";

    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte curr : bytes) {
            String tmp = Integer.toHexString(curr & 0xff);
            sb.append(tmp.length() == 1 ? "0"+tmp : tmp);
        }
        return sb.toString();
    }

    public static byte[] hexStringToByte(String content) {
        int size;
        if (content == null || (size =content.length()) ==0 || size % 2 != 0) {
            return  null;
        }
        content = content.toLowerCase();
        checkHexString(content);
        byte[] reslut = new byte[size =size/2];
        for (int i=0; i < size; i++) {
            reslut[i] = (byte) (HEX_STRING.indexOf(content.charAt(i * 2)) * 16 + HEX_STRING.indexOf(content.charAt(i * 2 + 1)));
        }
        return reslut;
    }

    private static void checkHexString(String content) {
        for (int i=0, size=content.length(); i < size; i++) {
            if (HEX_STRING.indexOf(content.charAt(i)) < 0) {
                throw new RuntimeException("isn't hex string");
            }
        }
    }

    public static byte[] stringToBytes(String content) {
        if (content == null || content.length() <= 0) {
            return new byte[0];
        }
        byte[] bytes = null;
        try {
            bytes = content.getBytes(DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("content convert to utf-8 bytes failed");
        }
        return bytes;
    }

    public static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return "";
        }
        String result = null;
        try {
            result = new String(bytes, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("bytes to utf-8 String failed");
        }
        return result;
    }
}
