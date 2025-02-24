package com.augmentedcooking.Models.Response.Chat;

import java.util.UUID;

import org.bson.types.ObjectId;

import com.augmentedcooking.Models.Database.Chat.Message;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MessageResponseBody {

    private String id;
    private String userPubId;
    private String sender;
    private String content;
    private long timestamp;

    public MessageResponseBody(Message recipeImage) {
        this.id = recipeImage.getId().toHexString();
        this.userPubId = recipeImage.getUserPubId().toString();
        this.sender = recipeImage.getSender();
        this.content = recipeImage.getContent();
        this.timestamp = recipeImage.getTimestamp();
    }

    public MessageResponseBody(ObjectId id, UUID userPubId, String sender, String content, long timestamp) {
        this.id = id.toHexString();
        this.userPubId = userPubId.toString();
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }
}
