package com.augmentedcooking.Exceptions.User;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class InvalidCredentialsException extends BaseResponseException {

    public InvalidCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    public InvalidCredentialsException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
