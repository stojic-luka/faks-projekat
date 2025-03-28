package com.augmentedcooking.Services.Chat.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentedcooking.Models.Database.Chat.ChatMessage;
import com.augmentedcooking.Repositories.Chat.IChatRepository;
import com.augmentedcooking.Services.Chat.base.IChatService;

@Service
public class ChatService implements IChatService {

    private final IChatRepository chatRepository;

    @Autowired
    public ChatService(final IChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public List<ChatMessage> getMessagesByUserId(String userId, int page, int limit) {
        return null;
    }

    @Override
    public List<ChatMessage> getMessagesByUserId(String userId, int page, int limit, long timestamp) {
        return null;
    }

    @Override
    public ChatMessage addMessage(String userId, ChatMessage message) {
        return null;
    }

    @Override
    public ChatMessage deleteMessageByUserId(String userId, String messageId) {
        return null;
    }

    @Override
    public boolean clearChatMessagesByUserId(String userId) {
        return false;
    }
}
