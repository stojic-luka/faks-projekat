package com.augmentedcooking.Models.Response.Chat;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import com.augmentedcooking.Models.Database.Chat.ChatMessage;
import com.augmentedcooking.Models.Database.Chat.ChatMessage.MessageContent;
import com.augmentedcooking.Models.Database.Chat.ChatMessage.MessageContent.ChatImage;

import io.github.thibaultmeyer.cuid.CUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponseBody {

    private String id;
    private String type;
    private String model;
    private String role;
    private MessageContentResponseBody content;
    private long timestamp;
    private String userId;

    public ChatMessageResponseBody(ChatMessage chatMessage) {
        this.id = chatMessage.getId().toString();
        this.type = chatMessage.getType().toString();
        this.model = chatMessage.getModel();
        this.role = chatMessage.getRole().toString();
        this.content = new MessageContentResponseBody(chatMessage.getContent());
        this.timestamp = chatMessage.getTimestamp();
        this.userId = chatMessage.getUserId().toString();
    }

    public ChatMessageResponseBody(CUID id, String type, String model, String role, MessageContent content,
            long timestamp, CUID userId) {
        this.id = id.toString();
        this.type = type;
        this.model = model;
        this.role = role;
        this.content = new MessageContentResponseBody(content);
        this.timestamp = timestamp;
        this.userId = userId.toString();
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class MessageContentResponseBody {

        private String text;
        private List<ChatImageResponseBody> images;

        public MessageContentResponseBody(MessageContent messageContent) {
            this.text = messageContent.getText();
            this.images = messageContent.getImages().stream()
                    .map(ChatImageResponseBody::new)
                    .collect(Collectors.toList());
        }

        public MessageContentResponseBody(String text, List<ChatImage> images) {
            this.text = text;
            this.images = images.stream()
                    .map(ChatImageResponseBody::new)
                    .collect(Collectors.toList());
        }

        @Getter
        @Setter
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ChatImageResponseBody {

            private String data;
            private String format;
            private MessageContent.ChatImage.Metadata metadata;
            private String description;

            public ChatImageResponseBody(ChatImage chatImage) {
                this.data = Base64.getEncoder().encodeToString(chatImage.getData());
                this.format = chatImage.getFormat();
                this.metadata = chatImage.getMetadata();
                this.description = chatImage.getDescription();
            }

            public ChatImageResponseBody(byte[] data, String format, MessageContent.ChatImage.Metadata metadata,
                    String description) {
                this.data = Base64.getEncoder().encodeToString(data);
                this.format = format;
                this.metadata = metadata;
                this.description = description;
            }
        }
    }
}
