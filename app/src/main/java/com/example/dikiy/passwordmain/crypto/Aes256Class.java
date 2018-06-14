package com.example.dikiy.passwordmain.crypto;

import android.util.Base64;
import android.util.Log;

import java.lang.reflect.Array;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Aes256Class {
    public static String encode(String password, String key, boolean mode) {
        Log.v("Crypto[ORIGINAL]password", password);
        Log.v("Crypto[ORIGINAL]key", key);
        SecretKeySpec sks;
        if (mode)
            sks = loadPublicKey(key);
        else
            sks = decodeBase64ToAESKey(key);
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = c.doFinal(password.getBytes());
        } catch (Exception e) {
            Log.e("Crypto", "AES encryption error");
        }
        Log.v("Crypto[ENCODED]:", Base64.encodeToString(encodedBytes, Base64.NO_WRAP));
//        sks = new SecretKeySpec(sks.getEncoded(), "AES");
//        byte[] decodedBytes = null;
//        try {
//            Cipher c = Cipher.getInstance("AES");
//            c.init(Cipher.DECRYPT_MODE, sks);
//            decodedBytes = c.doFinal(encodedBytes);
//        } catch (Exception e) {
//            Log.e("Crypto", "AES decryption error");
//        }
        String passKey = null;
        if (encodedBytes != null)
            passKey = Base64.encodeToString(encodedBytes, Base64.NO_WRAP);
        if (passKey != null) {
            Log.v("Crypto[DECODED]:", passKey);
            return passKey;
        } else {
            return "";
        }
    }

    public static String decode(String password, String key) {
        Log.v("Crypto[decode]password", password);
        Log.v("Crypto[decode]key", key);
        SecretKeySpec sks = decodeBase64ToAESKey(key);
        byte[] decodedBytes = null;
        byte[] encodedBytes = Base64.decode(password.getBytes(), Base64.NO_WRAP);
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, sks);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e) {
            Log.e("Crypto", "AES decryption error");
        }
        String passKey = null;
        if (decodedBytes != null)
            passKey = new String(decodedBytes);

        if (passKey != null) {
            Log.v("Crypto[DECODED]132:", passKey);
            return passKey;
        } else {
            return "";
        }
    }

    public static SecretKeySpec decodeBase64ToAESKey(final String encodedKey)
            throws IllegalArgumentException {
        try {
            // throws IllegalArgumentException - if src is not in valid Base64
            // scheme
            byte[] keyData = Base64.decode(encodedKey.getBytes(), Base64.NO_WRAP);
            final int keysize = keyData.length * Byte.SIZE;

            // this should be checked by a SecretKeyFactory, but that doesn't exist for AES

            switch (keysize) {
                case 128:
                    break;
                default:
                    keyData = new byte[keyData.length + (128 - keysize) / Byte.SIZE];
                    Log.e("Crypto Length", String.valueOf(keyData.length * Byte.SIZE));
            }

            if (Cipher.getMaxAllowedKeyLength("AES") < keysize) {
                // this may be an issue if unlimited crypto is not installed
                throw new IllegalArgumentException("Key size of " + keysize
                        + " not supported in this runtime");
            }

            // throws IllegalArgumentException - if key is empty
            final SecretKeySpec aesKey = new SecretKeySpec(keyData, 0, keyData.length, "AES");
            return aesKey;
        } catch (final NoSuchAlgorithmException e) {
            // AES functionality is a requirement for any Java SE runtime
            throw new IllegalStateException(
                    "AES should always be present in a Java SE runtime", e);
        }
    }

    public static String generatorKey() {
        Key key;
        SecureRandom rand = new SecureRandom();
        KeyGenerator generator = null;
        try {
            generator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        generator.init(128, rand);
        key = generator.generateKey();
        return Base64.encodeToString(key.getEncoded(), Base64.NO_WRAP);
    }

    public static SecretKeySpec loadPublicKey(String string) {
        byte[] decodedKey = Base64.decode(string.getBytes(), Base64.NO_WRAP);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}
