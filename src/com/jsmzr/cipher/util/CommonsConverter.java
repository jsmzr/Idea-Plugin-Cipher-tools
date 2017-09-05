package com.jsmzr.cipher.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class CommonsConverter {
    private final static String HEX_STRING = "0123456789abcdef";
    private final static String DEFAULT_ENCODING = "utf-8";
    private final static String BASE64_DIG = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/=+";

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

    public static boolean isHex(String data) {
        if (data == null || "".equals(data.trim()) || data.length()%2 != 0) {
            return false;
        }
        char[] chars = data.toCharArray();
        for (char c : chars) {
            if (c < '0' || (c > '9' && c < 'A') || (c > 'f' && c < 'a') || c> 'f') {
                return false;
            }
        }
        return true;
    }

    public static boolean isBase64(String data) {
        if (data == null || "".equals(data.trim())) {
            return false;
        }
        int size = data.length();
        int mod = size % 4;
        if ((mod == 1) ||
                (mod == 2 && "=".equals(data.substring(size-1))) ||
                (mod == 3 && "==".equals(data.substring(size-2)))) {
            return false;
        }
        if (mod != 0) {
            data += 2 == mod ? "==" : "=";
        }
        for (int index=0, len=data.length(); index < len; index++) {
            if (!BASE64_DIG.contains(data.substring(index, index+1))) {
                return false;
            }
        }
        return true;
    }

    public static byte[] cipherTextConvert(String data) {
        if (isHex(data)) {
            return hexStringToByte(data);
        }
        if (isBase64(data)) {
            return Base64.getDecoder().decode(data);
        }else {
            throw new RuntimeException("can't resolve cipherText coding");
        }
    }
}
