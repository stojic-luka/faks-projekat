package com.augmentedcooking.Models.Rabbitmq.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiRequestBody {
    @JsonProperty("request_id")
    private String requestId;
    private String model;
    private String prompt;
    private boolean streamed;
    private String imageb64;

    public AiRequestBody(String requestId, String model, String prompt) {
        this.requestId = requestId;
        this.model = model;
        this.prompt = prompt;
        this.streamed = false;
        this.imageb64 = null;
    }

    public AiRequestBody(String requestId, String model, String prompt, boolean streamed) {
        this.requestId = requestId;
        this.model = model;
        this.prompt = prompt;
        this.streamed = streamed;
        this.imageb64 = null;
    }
}
