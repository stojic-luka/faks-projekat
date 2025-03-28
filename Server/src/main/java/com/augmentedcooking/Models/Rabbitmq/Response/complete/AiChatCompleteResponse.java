package com.augmentedcooking.Models.Rabbitmq.Response.complete;

import com.augmentedcooking.Enums.Response.ResponseTypes;
import com.augmentedcooking.Models.Rabbitmq.Response.AiChatResponse;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;

import java.util.List;

@Getter
@JsonTypeName("COMPLETE")
public class AiChatCompleteResponse extends AiChatResponse {

    private final String role;
    private final String model;
    private final long timestamp;
    private final AiChatResponseMessage message;

    @JsonCreator
    public AiChatCompleteResponse(
            @JsonProperty("request_id") String requestId,
            @JsonProperty("type") String type,
            @JsonProperty("role") String role,
            @JsonProperty("model") String model,
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("message") AiChatResponseMessage message) {
        super(requestId, ResponseTypes.valueOf(type));
        this.role = role;
        this.model = model;
        this.timestamp = timestamp;
        this.message = message;
    }

    @Getter
    public static class AiChatResponseMessage {

        private final String content;
        private final List<AiChatResponseMessageImage> images;

        @JsonCreator
        public AiChatResponseMessage(
                @JsonProperty("content") String content,
                @JsonProperty("images") List<AiChatResponseMessageImage> images) {
            this.content = content;
            this.images = images;
        }

        @Getter
        public static class AiChatResponseMessageImage {

            private final String data;
            private final String format;
            private final String width;
            private final String height;
            private final String description;

            @JsonCreator
            public AiChatResponseMessageImage(
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
    }
}
