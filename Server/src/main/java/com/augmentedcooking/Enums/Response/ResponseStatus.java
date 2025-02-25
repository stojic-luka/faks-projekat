package com.augmentedcooking.Enums.Response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseStatus {
    SUCCESS("success"),
    ERROR("error");

    private final String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String status() {
        return status;
    }
}
