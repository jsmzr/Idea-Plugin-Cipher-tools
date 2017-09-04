package com.jsmzr.cipher.util;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {
    private final static String RSA_ALG = "RSA";
    private final static String HEX_DIG = "0123456789ABCDEFabcdef";

    public static String encrypt(String data, String publicKey) {
        byte[] bytes = doFinal(true, data, publicKey);
        return CommonsConverter.bytesToHexString(bytes);
    }

    public static String decrypt(String data, String privateKey) {
        byte[] bytes = doFinal(false, data, privateKey);
        return CommonsConverter.bytesToString(bytes);
    }

    private static byte[] doFinal(boolean isEncrypt, String data, String key) {
        byte[] keyBytes = convetKey(key);
        byte[] dataBytes = CommonsConverter.stringToBytes(data);
        Key secKey;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALG);
            secKey = isEncrypt ?
                    keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes)) :
                    keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("init key failed");
        }
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALG);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secKey);
            return cipher.doFinal(dataBytes);
        }catch (Exception e) {
            throw new RuntimeException("encrypt error: " + e.getMessage());
        }
    }

    private static byte[] convetKey(String key) {
        if (key == null || "".equals(key.trim())) {
            throw new RuntimeException("key is null");
        }
        boolean isPem = false;
        for (int index=0, size=key.length(); index<size; index++) {
            if (!HEX_DIG.contains(key.substring(index, index+1))){
                isPem = true;
                break;
            }
        }
        return isPem ?
                Base64.getDecoder().decode(key) :
                CommonsConverter.hexStringToByte(key);
    }
}
