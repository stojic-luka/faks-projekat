package com.augmentedcooking.Exceptions.Http;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class NotFoundException extends BaseResponseException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}
