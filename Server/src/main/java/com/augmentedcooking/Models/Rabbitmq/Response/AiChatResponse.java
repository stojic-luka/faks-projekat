package com.augmentedcooking.Models.Rabbitmq.Response;

import com.augmentedcooking.Enums.Response.ResponseTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = com.augmentedcooking.Models.Rabbitmq.Response.complete.AiChatCompleteResponse.class, name = "COMPLETE"),
        @JsonSubTypes.Type(value = com.augmentedcooking.Models.Rabbitmq.Response.streamed.AiChatStreamedResponseMetadataBody.class, name = "STREAMED_METADATA"),
        @JsonSubTypes.Type(value = com.augmentedcooking.Models.Rabbitmq.Response.streamed.AiChatStreamedResponseTextBody.class, name = "STREAMED_TEXT"),
        @JsonSubTypes.Type(value = com.augmentedcooking.Models.Rabbitmq.Response.streamed.AiChatStreamedResponseImageBody.class, name = "STREAMED_IMAGE"),
})
public abstract class AiChatResponse {

    private final String id;
    private final ResponseTypes type;

    public AiChatResponse(String requestId, String type) {
        this.id = requestId;
        this.type = ResponseTypes.valueOf(type);
    }
}
