package com.weather.server.domain.model;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordHasher {
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static final Base64.Decoder base64Decoder = Base64.getUrlDecoder();

    public static String hashNewPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return hashPasswordWithPBKDF2(salt, password);
    }

    private static String hashPasswordWithPBKDF2(byte[] salt, String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536,128);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] passwordHash = factory.generateSecret(spec).getEncoded();
        String saltStr= base64Encoder.encodeToString(salt);
        String hashStr= base64Encoder.encodeToString(passwordHash);
        return saltStr+"|"+hashStr;
    }


    public static String hashPasswordWithSpecifiedSalt(String password, String saltStrSrc) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] saltSrc = base64Decoder.decode(saltStrSrc);
        return hashPasswordWithPBKDF2(saltSrc, password);
    }

    public static String getSaltFromDBpassword(String dbPassword){
        String salt = dbPassword.substring(0, dbPassword.indexOf('|'));
        return salt;
    }
}
