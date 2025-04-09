package com.augmentedcooking.Services.Chat.base;

import java.io.OutputStream;

import com.augmentedcooking.Models.Rabbitmq.Response.complete.AiChatCompleteResponse;
import com.augmentedcooking.Models.Request.Chat.ChatRequestBody;

public interface IAiMessageService {

    AiChatCompleteResponse askAI(ChatRequestBody body, String userId);

    void askAI(ChatRequestBody body, OutputStream outputStream, String userId);
}
