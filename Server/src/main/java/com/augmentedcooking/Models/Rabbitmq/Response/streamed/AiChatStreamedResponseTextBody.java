package com.augmentedcooking.Models.Rabbitmq.Response.streamed;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;

@Getter
@JsonTypeName("STREAMED_TEXT")
public class AiChatStreamedResponseTextBody extends AiChatStreamedResponse<String> {

    @JsonCreator
    public AiChatStreamedResponseTextBody(
            @JsonProperty("request_id") String requestId,
            @JsonProperty("type") String type,
            @JsonProperty("sequence") int sequence,
            @JsonProperty("last_chunk") boolean lastChunk,
            @JsonProperty("content") String content) {
        super(requestId, type, sequence, lastChunk, content);
    }
}
