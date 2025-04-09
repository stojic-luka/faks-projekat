package com.augmentedcooking.Models.Rabbitmq.Response.streamed;

import com.augmentedcooking.Enums.Response.ResponseTypes;
import com.augmentedcooking.Models.Rabbitmq.Response.AiChatResponse;

import lombok.Getter;

@Getter
public abstract class AiChatStreamedResponse<T> extends AiChatResponse {

    private final int sequence;
    private final boolean lastChunk;
    private final T content;

    public AiChatStreamedResponse(String requestId, String type, int sequence, boolean lastChunk, T content) {
        super(requestId, ResponseTypes.valueOf(type));
        this.sequence = sequence;
        this.lastChunk = lastChunk;
        this.content = content;
    }
}
