package com.augmentedcooking.Enums.Response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseTypes {
    COMPLETE("COMPLETE"),
    STREAMED_METADATA("STREAMED_METADATA"),
    STREAMED_TEXT("STREAMED_TEXT"),
    STREAMED_IMAGE("STREAMED_IMAGE");

    private final String type;

    ResponseTypes(String type) {
        this.type = type;
    }

    @JsonValue
    public String type() {
        return type;
    }
}
