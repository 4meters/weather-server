package com.weather.server.domain.model;

import java.security.SecureRandom;
import java.util.Base64;

public class PasswordGenerator {
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static String generatePassword() {
        byte[] randomBytes = new byte[8];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
