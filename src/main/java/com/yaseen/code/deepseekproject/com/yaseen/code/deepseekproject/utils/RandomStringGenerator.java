package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomStringGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String getString() {
        int length = RANDOM.nextInt(7) + 8; // Generates a length between 8 and 14
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return sb.toString();
    }
}
