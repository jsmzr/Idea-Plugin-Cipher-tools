package com.jsmzr.cipher.util;

import com.jsmzr.cipher.model.CipherModel;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class CommonsCipherUtil {
    private final static String NES_IV = "CBC";
    private final static String NES_PPD = "NoPadding";

    public static String encrypt(CipherModel alg, String content, String key, String iv) {
        byte[] contentBytes = CommonsConverter.stringToBytes(content);
        return CommonsConverter.bytesToHexString(doFinal(true, alg, contentBytes, key, iv, contentBytes.length));
    }

    public static String decrypt(CipherModel alg, String content, String key, String iv) {
        byte[] contentBytes = CommonsConverter.cipherTextConvert(content);
        return CommonsConverter.bytesToString(doFinal(false, alg, contentBytes, key, iv, contentBytes.length));
    }

    private static byte[] doFinal(boolean isEncrypt, CipherModel cm, byte[] content, String key, String iv, int size) {
        byte[] keyBytes;
        byte[] ivBytes;
        if (content == null || content.length <= 0) {
            throw new RuntimeException("content is null");
        }
        keyBytes = CommonsConverter.stringToBytes(key);
        ivBytes = CommonsConverter.stringToBytes(iv);
        if (keyBytes == null || keyBytes.length <= 0) {
            throw new RuntimeException("key byte[] is null");
        }
        if (keyBytes.length != cm.getKeySize()) {
            throw new RuntimeException(String.format("key size should be %s", cm.getKeySize()));
        }
        if (NES_IV.equals(cm.getWork()) && (iv == null || iv.length() != cm.getIvSize())) {
            throw new RuntimeException(String.format("iv size should be %s", cm.getIvSize()));
        }
        if (NES_PPD.equals(cm.getPend()) && content.length % 8 != 0) {
            throw new RuntimeException(String.format("Nopadding mode content should be multiple 8, content %s", content.length));
        }
        Key sk = new SecretKeySpec(keyBytes, cm.getAlg());
        byte[] result;
        try {
            Cipher cipher = Cipher.getInstance(cm.getDetail());
            if (NES_IV.equals(cm.getWork())) {
                cipher.init(isEncrypt? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, sk, new IvParameterSpec(ivBytes));
            }else {
                cipher.init(isEncrypt? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, sk);
            }
            if (NES_PPD.equals(cm.getPend())) {
                result = cipher.doFinal(content, 0, size);
            } else {
                result = cipher.doFinal(content);
            }

        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }


}
