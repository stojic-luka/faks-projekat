package com.augmentedcooking.Models.Response;

import lombok.Setter;

import com.augmentedcooking.Enums.Response.ResponseStatus;

import lombok.Getter;

@Getter
@Setter
public class ApiResponseData<T> extends BaseResponse {
    private T data;

    public ApiResponseData(T data) {
        super(ResponseStatus.SUCCESS);
        this.data = data;
    }
}
