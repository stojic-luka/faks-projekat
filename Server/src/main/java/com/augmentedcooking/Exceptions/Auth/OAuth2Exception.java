package com.augmentedcooking.Exceptions.Auth;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class OAuth2Exception extends BaseResponseException {

    public OAuth2Exception() {
        super(HttpStatus.UNAUTHORIZED, "OAuth2 authentication failed");
    }

    public OAuth2Exception(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
