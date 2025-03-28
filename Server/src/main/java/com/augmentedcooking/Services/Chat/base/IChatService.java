package com.augmentedcooking.Services.Chat.base;

import java.util.List;

import com.augmentedcooking.Models.Database.Chat.ChatMessage;

public interface IChatService {

    List<ChatMessage> getMessagesByUserId(String userPubId, int page, int limit);

    List<ChatMessage> getMessagesByUserId(String userPubId, int page, int limit, long timestamp);

    ChatMessage addMessage(String userPubId, ChatMessage message);

    ChatMessage deleteMessageByUserId(String userPubId, String messageId);

    boolean clearChatMessagesByUserId(String userPubId);
}
