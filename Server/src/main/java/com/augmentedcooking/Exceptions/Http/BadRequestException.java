package com.augmentedcooking.Exceptions.Http;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class BadRequestException extends BaseResponseException {

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
