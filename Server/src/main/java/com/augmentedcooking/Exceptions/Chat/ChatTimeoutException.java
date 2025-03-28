package com.augmentedcooking.Exceptions.Chat;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class ChatTimeoutException extends BaseResponseException {

    public ChatTimeoutException() {
        super(HttpStatus.REQUEST_TIMEOUT);
    }

    public ChatTimeoutException(String message) {
        super(HttpStatus.REQUEST_TIMEOUT, message);
    }
}
