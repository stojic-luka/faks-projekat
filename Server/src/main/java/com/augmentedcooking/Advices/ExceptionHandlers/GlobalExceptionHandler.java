package com.augmentedcooking.Advices.ExceptionHandlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;
import com.augmentedcooking.Models.Response.ApiResponseError;
import com.augmentedcooking.Models.Response.ResponseWrapper;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ BaseResponseException.class })
    public ResponseWrapper<ApiResponseError> handleBaseException(BaseResponseException exception) {
        // TODO: check if apiVersion has more efficient way of passing
        // var meta = exception.getApiVersion() != null
        // ? new BaseResponse.Meta(exception.getApiVersion())
        // : new BaseResponse.Meta();

        return new ResponseWrapper<ApiResponseError>(
                new ApiResponseError(exception),
                HttpStatus.fromCode(exception.getResponseCode()));
    }
}
