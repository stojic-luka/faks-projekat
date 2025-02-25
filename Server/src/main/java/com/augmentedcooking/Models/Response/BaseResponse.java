package com.augmentedcooking.Models.Response;

import java.time.Instant;
import java.util.HashMap;

import com.augmentedcooking.Enums.Response.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponse {
    private ResponseStatus status;
    private HashMap<String, Object> meta;

    public BaseResponse(ResponseStatus status) {
        this.status = status;

        this.meta = new HashMap<>();
        this.meta.put("timestamp", Instant.now().toEpochMilli());
    }
}
