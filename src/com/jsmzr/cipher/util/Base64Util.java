package com.jsmzr.cipher.util;

import java.util.Base64;

public class Base64Util {

    public static String encode(String content) {
        return commons(true, content);
    }

    public static String decode(String content) {
        return commons(false, content);
    }

    private static String commons(boolean isEncode, String content) {
        if (content == null || content.length() <=0) {
            return "";
        }
        byte[] contentBytes = CommonsConverter.stringToBytes(content);
        if (contentBytes == null || contentBytes.length <=0) {
            return "";
        }
        return CommonsConverter.bytesToString(isEncode ? Base64.getEncoder().encode(contentBytes) : Base64.getDecoder().decode(contentBytes));
    }
}
