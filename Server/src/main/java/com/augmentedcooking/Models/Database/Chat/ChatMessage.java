package com.augmentedcooking.Models.Database.Chat;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.augmentedcooking.Containts.Constants;
import com.augmentedcooking.Enums.Response.MessageRoles;
import com.augmentedcooking.Enums.Response.ResponseTypes;

import io.github.thibaultmeyer.cuid.CUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = Constants.DB.Collections.USER_MESSAGES)
public class ChatMessage {

    @Id
    private CUID id;
    private ResponseTypes type;
    private String model;
    private MessageRoles role;
    private MessageContent content;
    private long timestamp;
    private CUID userId;

    public ChatMessage(ResponseTypes type, String model, MessageRoles role, MessageContent content, long timestamp,
            CUID userId) {
        this.id = CUID.randomCUID2();
        this.type = type;
        this.model = model;
        this.role = role;
        this.content = content;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageContent {

        private String text;
        private List<MessageContent.ChatImage> images;

        @Getter
        @Setter
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ChatImage {
            private byte[] data;
            private String format;
            private String description;
            private MessageContent.ChatImage.Metadata metadata;

            @Getter
            @Setter
            @ToString
            @AllArgsConstructor
            @NoArgsConstructor
            public static class Metadata {
                private int height;
                private int width;
            }
        }
    }
}
