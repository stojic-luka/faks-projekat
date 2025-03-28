package com.augmentedcooking.Utils.impl;

import java.util.Arrays;

import io.github.thibaultmeyer.cuid.CUID;

public class CUIDConverter {
    private static final int NUMBER_BASE = 36;
    private static final byte MAX_CHARS = 24;
    private static final byte MAX_BYTES = 16;
    private static final char[] DIGITS = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final int[] CHAR_TO_DIGIT = new int[128];

    static {
        Arrays.fill(CHAR_TO_DIGIT, -1);
        for (int i = 0; i < DIGITS.length; i++) {
            CHAR_TO_DIGIT[DIGITS[i]] = i;
        }
    }

    public static byte[] cuidToBytes(CUID cuid) {
        return cuidToBytes(cuid.toString());
    }

    /**
     * Converts a CUID string into a byte array.
     *
     * The CUID must be non-null, non-empty, and only contain valid characters.
     * This method returns a byte array that represents the CUID, trimming any
     * leading zeros.
     *
     * @param cuid the CUID string (must not be null or empty)
     * @return a byte array that represents the CUID
     * @throws IllegalArgumentException if the CUID is null, empty, or contains
     *                                  invalid characters
     */
    public static byte[] cuidToBytes(String cuid) {
        if (!CUID.isValid(cuid))
            throw new IllegalArgumentException("CUID cannot be null or empty and must have length of 24 characters");

        char c = cuid.charAt(0);
        if (c >= 128 || CHAR_TO_DIGIT[c] == -1)
            throw new IllegalArgumentException("Invalid character in CUID: " + c);

        byte[] result = new byte[MAX_BYTES];
        byte start = MAX_BYTES - 1;
        result[start] = (byte) CHAR_TO_DIGIT[c];

        for (int i = 1, len = cuid.length(); i < len; i++) {
            c = cuid.charAt(i);
            int carry = (c < 128) ? CHAR_TO_DIGIT[c] : -1;
            if (carry == -1)
                throw new IllegalArgumentException("Invalid character in CUID: " + c);

            for (int j = MAX_BYTES - 1; j >= start; j--) {
                int value = ((result[j] & 0xFF) * NUMBER_BASE) + carry;
                result[j] = (byte) value;
                carry = value >>> 8;
            }

            if (carry > 0) {
                start--;
                result[start] = (byte) carry;
            }
        }

        if (start == 0)
            return result;

        return Arrays.copyOfRange(result, start, MAX_BYTES);
    }

    /**
     * Converts a byte array into a CUID string.
     *
     * The method encodes the byte array into a base-36 string.
     *
     * @param bytes the byte array to convert (must not be null or empty)
     * @return a CUID string representing the byte array
     * @throws IllegalArgumentException if the byte array is null or empty
     */
    public static String bytesToCuidString(byte[] bytes) {
        if (bytes == null || bytes.length != MAX_BYTES)
            throw new IllegalArgumentException("CUID cannot be null or empty and must have length of 16 bytes");

        short[] digits = new short[MAX_CHARS];
        digits[0] = 0;
        int digitCount = 1, base = 36;
        int carry, temp;

        for (int i = 0; i < bytes.length; i++) {
            carry = bytes[i] & 0xFF;
            for (int j = 0; j < digitCount; j++) {
                temp = ((digits[j] & 0xFF) << 8) + carry;
                digits[j] = (short) (temp % base);
                carry = temp / base;
            }
            while (carry > 0) {
                digits[digitCount++] = (short) (carry % base);
                carry /= base;
            }
        }

        char[] finalResult = new char[digitCount];
        for (int i = 0, j = digitCount - 1; i < digitCount; i++, j--)
            finalResult[i] = DIGITS[digits[j] & 0xFF];

        return new String(finalResult);
    }
}
