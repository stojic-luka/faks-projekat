package com.augmentedcooking.Exceptions.Http;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class InvalidApiVersionException extends BaseResponseException {

    public InvalidApiVersionException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
