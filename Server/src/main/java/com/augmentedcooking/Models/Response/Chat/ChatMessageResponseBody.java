package com.augmentedcooking.Models.Response.Chat;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;

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

    @Id
    private String id;
    private String type;
    private String model;
    private String role;
    private MessageContentResponseBody content;
    private long timestamp;

    public ChatMessageResponseBody(ChatMessage recipeImage) {
        this.id = recipeImage.getId().toString();
        this.type = recipeImage.getType().toString();
        this.model = recipeImage.getModel();
        this.role = recipeImage.getRole().toString();
        this.content = new MessageContentResponseBody(recipeImage.getContent());
        this.timestamp = recipeImage.getTimestamp();
    }

    public ChatMessageResponseBody(CUID id, String type, String model, String role, MessageContent content,
            long timestamp) {
        this.id = id.toString();
        this.type = type;
        this.model = model;
        this.role = role;
        this.content = new MessageContentResponseBody(content);
        this.timestamp = timestamp;
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
            private int width;
            private int height;
            private String description;

            public ChatImageResponseBody(ChatImage chatImage) {
                this.data = Base64.getEncoder().encodeToString(chatImage.getData());
                this.format = chatImage.getFormat();
                this.width = chatImage.getWidth();
                this.height = chatImage.getHeight();
                this.description = chatImage.getDescription();
            }

            public ChatImageResponseBody(byte[] data, String format, int width, int height, String description) {
                this.data = Base64.getEncoder().encodeToString(data);
                this.format = format;
                this.width = width;
                this.height = height;
                this.description = description;
            }
        }
    }
}
