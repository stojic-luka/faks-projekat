package com.augmentedcooking.Models.Rabbitmq.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AiRequestBody {
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("user_id")
    private String userId;
    private String model;
    private String prompt;
    private boolean streamed;
    private String imageb64;

    public AiRequestBody(String requestId, String userId, String model, String prompt) {
        this.requestId = requestId;
        this.userId = userId;
        this.model = model;
        this.prompt = prompt;
        this.streamed = false;
        this.imageb64 = null;
    }

    public AiRequestBody(String requestId, String userId, String model, String prompt, boolean streamed) {
        this.requestId = requestId;
        this.userId = userId;
        this.model = model;
        this.prompt = prompt;
        this.streamed = streamed;
        this.imageb64 = null;
    }

    public AiRequestBody(String requestId, String userId, String model, String prompt, boolean streamed,
            String imageb64) {
        this.requestId = requestId;
        this.userId = userId;
        this.model = model;
        this.prompt = prompt;
        this.streamed = streamed;
        this.imageb64 = imageb64 == "" ? null : imageb64;
    }
}
