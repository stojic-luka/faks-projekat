package com.augmentedcooking.Models.Response;

import com.augmentedcooking.Enums.Response.ResponseStatus;
import com.augmentedcooking.Exceptions.BaseResponseException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseError extends BaseResponse {
    private ApiResponseError.Error error;

    public ApiResponseError(BaseResponseException error) {
        super(ResponseStatus.ERROR);
        this.error = new ApiResponseError.Error(error.getErrorCode(), error.getMessage());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Error {
        private String code;
        private String message;
    }
}
