package com.augmentedcooking.Models.Rabbitmq.Response.streamed;

import com.augmentedcooking.Enums.Response.MessageRoles;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;

@Getter
class AiChatStreamedResponseMetadataContent {

    @JsonProperty("user_id")
    private final String userId;
    private final String model;
    private final MessageRoles role;
    private final long timestamp;

    public AiChatStreamedResponseMetadataContent(
            @JsonProperty("user_id") String userId,
            @JsonProperty("model") String model,
            @JsonProperty("role") String role,
            @JsonProperty("timestamp") long timestamp) {
        this.userId = userId;
        this.model = model;
        this.role = MessageRoles.valueOf(role);
        this.timestamp = timestamp;
    }
}

@Getter
@JsonTypeName("STREAMED_METADATA")
public class AiChatStreamedResponseMetadataBody extends AiChatStreamedResponse<AiChatStreamedResponseMetadataContent> {

    @JsonCreator
    public AiChatStreamedResponseMetadataBody(
            @JsonProperty("request_id") String requestId,
            @JsonProperty("type") String type,
            @JsonProperty("sequence") int sequence,
            @JsonProperty("last_chunk") boolean lastChunk,
            @JsonProperty("content") AiChatStreamedResponseMetadataContent content) {
        super(requestId, type, sequence, lastChunk, content);
    }
}
