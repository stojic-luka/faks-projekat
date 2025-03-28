package com.augmentedcooking.Exceptions.Chat;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class InvalidCuidException extends BaseResponseException {

    public InvalidCuidException() {
        super(HttpStatus.BAD_REQUEST, "Invalid CUID provided.");
    }

    public InvalidCuidException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
