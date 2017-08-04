package com.jsmzr.cipher.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacUtil {
    public static String encrypt(String alg, String content, String key) {
        byte[] keyBytes = CommonsConverter.stringToBytes(key);
        byte[] contentBytes = CommonsConverter.stringToBytes(content);
        Mac mac = null;
        try {
            mac = Mac.getInstance(alg);
            mac.init(new SecretKeySpec(keyBytes, alg));
            return CommonsConverter.bytesToHexString(mac.doFinal(contentBytes));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
