package com.augmentedcooking.Exceptions.Http;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class InternalServerException extends BaseResponseException {

    public InternalServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
