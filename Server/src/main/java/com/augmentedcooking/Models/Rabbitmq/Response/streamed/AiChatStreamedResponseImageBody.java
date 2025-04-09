package com.augmentedcooking.Models.Rabbitmq.Response.streamed;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;

@Getter
class AiChatStreamedResponseImageContent {
    private final String data;
    private final String format;
    private final String width;
    private final String height;
    private final String description;

    public AiChatStreamedResponseImageContent(
            @JsonProperty("data") String data,
            @JsonProperty("format") String format,
            @JsonProperty("width") String width,
            @JsonProperty("height") String height,
            @JsonProperty("description") String description) {
        this.data = data;
        this.format = format;
        this.width = width;
        this.height = height;
        this.description = description;
    }
}

@Getter
@JsonTypeName("STREAMED_IMAGE")
public class AiChatStreamedResponseImageBody extends AiChatStreamedResponse<AiChatStreamedResponseImageContent> {

    @JsonCreator
    public AiChatStreamedResponseImageBody(
            @JsonProperty("request_id") String requestId,
            @JsonProperty("type") String type,
            @JsonProperty("sequence") int sequence,
            @JsonProperty("last_chunk") boolean lastChunk,
            @JsonProperty("content") AiChatStreamedResponseImageContent content) {
        super(requestId, type, sequence, lastChunk, content);
    }
}
