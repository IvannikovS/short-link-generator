package ru.dsr.api.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class ShortCodeGenerator {
    public static String generateShortCode() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[4];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, 6);
    }
}
