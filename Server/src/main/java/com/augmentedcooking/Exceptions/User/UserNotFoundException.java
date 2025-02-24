package com.augmentedcooking.Exceptions.User;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class UserNotFoundException extends BaseResponseException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}
