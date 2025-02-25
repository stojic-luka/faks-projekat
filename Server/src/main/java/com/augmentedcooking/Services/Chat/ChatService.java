package com.augmentedcooking.Services.Chat;

import java.util.List;

import org.springframework.stereotype.Service;

import com.augmentedcooking.Models.Database.Chat.Message;

@Service
public class ChatService implements IChatService {

    @Override
    public List<Message> getMessagesByUserId(String userPubId, int page, int limit) {
        return null;
    }

    @Override
    public List<Message> getPreviousMessages(String userPubId, int page, int limit, long timestamp) {
        return null;
    }

    @Override
    public Message addMessage(String userPubId, Message message) {
        return null;
    }

    @Override
    public Message deleteMessageByUserId(String userPubId, String messageId) {
        return null;
    }

    @Override
    public boolean clearChatMessagesByUserId(String userPubId) {
        return false;
    }

}
