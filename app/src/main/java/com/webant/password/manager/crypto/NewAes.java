package com.webant.password.manager.crypto;

import android.util.Base64;
import android.util.Log;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class NewAes {
    public static String encrypt(String value, String key) {
        try {
            IvParameterSpec iv = new IvParameterSpec(validation(key).getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(validation(key).getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            if (encrypted != null)
                return Base64.encodeToString(encrypted, Base64.NO_WRAP);
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }


    public static String decrypt(String encrypted, String key) {
        try {
            IvParameterSpec iv = new IvParameterSpec(validation(key).getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(validation(key).getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.NO_WRAP));
            if (original != null)
                return new String(original);
            else
                return "";
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
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

    public static String validation(String string) {
        if (string.length() == 16) {
            return string;
        } else if (string.length() > 16) {
            return string.substring(0, 16);
        } else {
            for (int i = string.length(); i < 16; i++) {
                string += "x";
            }
            return string;
        }

    }
}
