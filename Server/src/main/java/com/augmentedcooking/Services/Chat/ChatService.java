package com.augmentedcooking.Services.Chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentedcooking.Models.Database.Chat.Message;
import com.augmentedcooking.Repositories.Chat.IChatRepository;

@Service
public class ChatService implements IChatService {

    private final IChatRepository chatRepository;

    @Autowired
    public ChatService(final IChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

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
