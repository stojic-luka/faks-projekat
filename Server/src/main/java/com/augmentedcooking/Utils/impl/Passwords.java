package com.augmentedcooking.Utils.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class Passwords {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10;
    private static final int KEY_LENGTH = 256;

    /**
     * Generates a random salt of 16 bytes.
     *
     * @return a 16 byte array containing the salt
     */
    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Hashes a password using the PBKDF2WithHmacSHA256 algorithm.
     *
     * @param password The password to be hashed. It will be cleared after use.
     * @param salt     The salt to be used in the hashing process.
     * @return The hashed password as a byte array.
     */
    public static byte[] hash(String password, byte[] salt) {
        return hash(password.toCharArray(), salt);
    }

    /**
     * Hashes a password using the PBKDF2WithHmacSHA256 algorithm.
     *
     * @param password The password to be hashed. It will be cleared after use.
     * @param salt     The salt to be used in the hashing process.
     * @return The hashed password as a byte array.
     */
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Verifies if the provided password, when hashed with the specified salt,
     * matches the expected hashed password.
     *
     * @param password     The password to verify.
     * @param salt         The salt used during the hashing process.
     * @param expectedHash The expected hash value to compare against.
     * @return true if the hashed password matches the expected hash, false
     *         otherwise.
     */
    public static boolean isValid(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] pwdHash = hash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length != expectedHash.length)
            return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i])
                return false;
        }
        return true;
    }

    /**
     * Generates a random password of the given length, containing only
     * alphanumeric characters.
     *
     * @param length The length of the password to generate.
     * @return A string containing the generated password.
     */
    public static String generateRandomPassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int c = RANDOM.nextInt(62);
            if (c <= 9) {
                sb.append(String.valueOf(c));
            } else if (c < 36) {
                sb.append((char) ('a' + c - 10));
            } else {
                sb.append((char) ('A' + c - 36));
            }
        }
        return sb.toString();
    }
}
