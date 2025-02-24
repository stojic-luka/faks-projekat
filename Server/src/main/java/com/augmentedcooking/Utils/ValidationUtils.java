package com.augmentedcooking.Utils;

public class ValidationUtils {

    public static boolean validateUsername(String username) {
        String usernamePattern = "[a-zA-Z0-9]{3,15}";
        return username != null && username.matches(usernamePattern);
    }

    public static boolean validateEmail(String email) {
        String emailPattern = "[^\\s@]+@[^\\s@]+\\.[^\\s@]+";
        return email != null && email.matches(emailPattern);
    }

    public static boolean validatePassword(String password) {
        String passwordPattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*()_+={}|\\[\\]:;,.<>?-]{10,}";
        return password != null && password.matches(passwordPattern);
    }

    public static boolean validateUUIDString(String uuid) {
        String uuidPattern = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
        return uuid != null && uuid.matches(uuidPattern);
    }
}
