package com.augmentedcooking.Enums.Response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageRoles {
    ASSISTANT("ASSISTANT"),
    USER("USER");

    private final String role;

    MessageRoles(String role) {
        this.role = role;
    }

    @JsonValue
    public String role() {
        return role;
    }
}
