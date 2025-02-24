package com.augmentedcooking.Services.Chat;

import java.util.List;

import com.augmentedcooking.Models.Database.Chat.Message;

public interface IChatService {

    List<Message> getMessagesByUserId(String userPubId, int page, int limit);

    List<Message> getPreviousMessages(String userPubId, int page, int limit, long timestamp);

    Message addMessage(String userPubId, Message message);

    Message deleteMessageByUserId(String userPubId, String messageId);

    boolean clearChatMessagesByUserId(String userPubId);
}
