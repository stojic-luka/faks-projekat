package com.augmentedcooking.Exceptions.Chat;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class MessageNotFoundException extends BaseResponseException {

    public MessageNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Message not found");
    }

    public MessageNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
