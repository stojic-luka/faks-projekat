package com.augmentedcooking.Models.Response;

import org.springframework.http.ResponseEntity;

import com.augmentedcooking.Enums.Http.HttpStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

public class ResponseWrapper<T> extends ResponseEntity<BaseResponse> {
    public ResponseWrapper(ApiResponseData<T> body, HttpStatus status) {
        super(body, org.springframework.http.HttpStatus.valueOf(status.getResponseCode()));
    }

    public ResponseWrapper(ApiResponseError body, HttpStatus status) {
        super(body, org.springframework.http.HttpStatus.valueOf(status.getResponseCode()));
    }

    public static <T> ResponseWrapper<T> success(T data) {
        return new ResponseWrapper<>(new ApiResponseData<>(data), HttpStatus.OK);
    }

    public static ResponseWrapper<?> error(BaseResponseException exception, HttpStatus status) {
        return new ResponseWrapper<>(new ApiResponseError(exception), status);
    }
}
