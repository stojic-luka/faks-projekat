package com.augmentedcooking.Exceptions.User;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class UserAlreadyExistException extends BaseResponseException {

    public UserAlreadyExistException() {
        super(HttpStatus.CONFLICT, "User already exists");
    }
}
