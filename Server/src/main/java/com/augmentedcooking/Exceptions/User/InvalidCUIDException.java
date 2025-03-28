package com.augmentedcooking.Exceptions.User;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class InvalidCUIDException extends BaseResponseException {

    public InvalidCUIDException() {
        super(HttpStatus.BAD_REQUEST, "Invalid CUID");
    }
}
