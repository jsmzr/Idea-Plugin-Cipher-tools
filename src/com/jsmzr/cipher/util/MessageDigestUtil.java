package com.jsmzr.cipher.util;

import java.security.MessageDigest;

public class MessageDigestUtil {
    private static String[] SUPPORT_ALGS = new String[]{
            "MD5",
            "SHA-1",
            "SHA-256"
    };
    /*
     * @Param alg MD5 SHA-1 SHA-256
     */
    public static String commons(String alg, String content) {
        if (content == null || content.length() <= 0) {
            return "";
        }
        byte[] contentBytes = CommonsConverter.stringToBytes(content);
        if (contentBytes == null || contentBytes.length <= 0) {
           return "";
        }
        MessageDigest messageDigest = null;
        byte[] resultBytes = null;
        try {
            messageDigest = MessageDigest.getInstance(alg);
            messageDigest.update(contentBytes);
            resultBytes = messageDigest.digest();
        }catch (Exception e) {
            throw new RuntimeException("digest failed");
        }
        return CommonsConverter.bytesToHexString(resultBytes);
    }
}
