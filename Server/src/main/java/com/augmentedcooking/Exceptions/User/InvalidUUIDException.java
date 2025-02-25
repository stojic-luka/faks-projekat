package com.augmentedcooking.Exceptions.User;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class InvalidUUIDException extends BaseResponseException {

    public InvalidUUIDException() {
        super(HttpStatus.BAD_REQUEST, "Invalid UUID");
    }
}
