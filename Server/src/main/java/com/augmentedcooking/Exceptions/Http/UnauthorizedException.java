package com.augmentedcooking.Exceptions.Http;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class UnauthorizedException extends BaseResponseException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
