package com.augmentedcooking.Exceptions.Chat;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class ChatClearFailedException extends BaseResponseException {

    public ChatClearFailedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to clear chat messages");
    }

    public ChatClearFailedException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
