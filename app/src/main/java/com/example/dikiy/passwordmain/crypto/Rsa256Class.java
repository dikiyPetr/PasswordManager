package com.example.dikiy.passwordmain.crypto;

import android.util.Base64;
import android.util.Log;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class Rsa256Class {
    private String key = "";
    private String encoded = "";

    public void init(String text) {
        byte[] encodedBytes = null;
        while (true) {
            Key privateKey = null;
            try {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(256);
                KeyPair kp = kpg.genKeyPair();
                key = savePublicKey(kp.getPublic());
                privateKey = kp.getPrivate();
            } catch (Exception e) {
                Log.e("Crypto", "RSA key pair error");
            }
            try {
                Cipher c = Cipher.getInstance("RSA");
                c.init(Cipher.ENCRYPT_MODE, privateKey);
                encodedBytes = c.doFinal(text.getBytes());
                break;
            } catch (Exception e) {
                Log.e("Crypto", "RSA encryption error");
            }
        }
        encoded = Base64.encodeToString(encodedBytes, Base64.DEFAULT);
    }

    public static String decode(String encodedPass, String decodeKey) {
        Key publicKey = loadPublicKey(decodeKey);
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, publicKey);
            decodedBytes = c.doFinal(Base64.decode(encodedPass, Base64.DEFAULT));
        } catch (Exception e) {
            Log.e("Crypto", "RSA decryption error");
        }
        if (decodedBytes!=null)
            return new String(decodedBytes);
        else
            return "";
    }

    public String getEncodedBytes() {
        return encoded;
    }

    public String getKey() {
        return key;
    }

    public static Key loadPublicKey(String stored) {
        try {
            byte[] byteKey = Base64.decode(stored.getBytes(), Base64.DEFAULT);
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePublic(X509publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String savePublicKey(Key publ) {
        byte encoded[] = publ.getEncoded();
        String encodedKey = Base64.encodeToString(encoded, Base64.DEFAULT);
        return encodedKey;
    }
}
