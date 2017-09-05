package com.jsmzr.cipher.util;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {
    private final static String RSA_ALG = "RSA";

    public static String encrypt(String data, String publicKey) {
        byte[] bytes = doFinal(true, data, publicKey);
        return CommonsConverter.bytesToHexString(bytes);
    }

    public static String decrypt(String data, String privateKey) {
        byte[] bytes = doFinal(false, data, privateKey);
        return CommonsConverter.bytesToString(bytes);
    }

    private static byte[] doFinal(boolean isEncrypt, String data, String key) {
        byte[] keyBytes =   CommonsConverter.isHex(key) ?
                CommonsConverter.hexStringToByte(key):
                CommonsConverter.isBase64(key) ?
                        Base64.getDecoder().decode(key) :
                        CommonsConverter.stringToBytes(key);

        byte[] dataBytes = isEncrypt ?
                CommonsConverter.stringToBytes(data) :
                CommonsConverter.cipherTextConvert(data);
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
}
