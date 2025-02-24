package com.augmentedcooking.Exceptions;

import com.augmentedcooking.Enums.Http.HttpStatus;

import lombok.Getter;

@Getter
public class BaseResponseException extends RuntimeException {
    private final int responseCode;
    private final String errorCode;
    private final String message;

    public BaseResponseException(HttpStatus httpStatus) {
        this.responseCode = httpStatus.responseCode();
        this.errorCode = httpStatus.errorCode().value();
        this.message = httpStatus.message().value();
    }

    public BaseResponseException(HttpStatus httpStatus, String message) {
        this.responseCode = httpStatus.responseCode();
        this.errorCode = httpStatus.errorCode().value();
        this.message = message;
    }

    public BaseResponseException(int responseCode, String errorCode, String message) {
        this.responseCode = responseCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}
