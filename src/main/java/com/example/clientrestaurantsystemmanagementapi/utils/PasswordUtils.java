package com.example.clientrestaurantsystemmanagementapi.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {
    private static final int SALT_LENGTH = 16;

    public static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] saltBytes = Base64.getDecoder().decode(salt);
            byte[] passwordBytes = password.getBytes();
            byte[] saltedPassword = new byte[saltBytes.length + passwordBytes.length];
            System.arraycopy(saltBytes, 0, saltedPassword, 0, saltBytes.length);
            System.arraycopy(passwordBytes, 0, saltedPassword, saltBytes.length, passwordBytes.length);

            byte[] hash = digest.digest(saltedPassword);

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occured during encryption" + e.getMessage());
        }
    }
}