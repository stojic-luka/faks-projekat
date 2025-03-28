package com.augmentedcooking.Services.Chat.impl;

import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentedcooking.Config.RabbitMQ.RabbitMQConfigs;
import com.augmentedcooking.Exceptions.Chat.ChatTimeoutException;
import com.augmentedcooking.Models.Rabbitmq.Request.AiRequestBody;
import com.augmentedcooking.Models.Rabbitmq.Response.AiChatResponse;
import com.augmentedcooking.Models.Rabbitmq.Response.complete.AiChatCompleteResponse;
import com.augmentedcooking.Models.Rabbitmq.Response.streamed.AiChatStreamedResponse;
import com.augmentedcooking.Models.Request.Chat.ChatRequestBody;
import com.augmentedcooking.Services.Chat.base.IAiMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.thibaultmeyer.cuid.CUID;

@Service
public class AiMessageService implements IAiMessageService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfigs.RabbitMQConfigsProperties rabbitMQConfigsProperties;

    private final ConcurrentHashMap<String, CompletableFuture<AiChatCompleteResponse>> pendingRequests = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Consumer<AiChatStreamedResponse<?>>> streamingHandlers = new ConcurrentHashMap<>();

    @Autowired
    public AiMessageService(final RabbitTemplate rabbitTemplate,
            final RabbitMQConfigs.RabbitMQConfigsProperties rabbitMQConfigsProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setReplyTimeout(30000);
        this.rabbitMQConfigsProperties = rabbitMQConfigsProperties;
    }

    @RabbitListener(queues = "${rabbitmq.responseQueue}")
    private void handleResponse(AiChatResponse response) {
        String requestId = response.getRequestId();

        Consumer<AiChatStreamedResponse<?>> streamHandler = streamingHandlers.get(requestId);
        if (streamHandler != null) {
            AiChatStreamedResponse<?> streamedResponse = (AiChatStreamedResponse<?>) response;
            streamHandler.accept(streamedResponse);

            if (streamedResponse.isLastChunk())
                streamingHandlers.remove(requestId);

            return;
        }

        CompletableFuture<AiChatCompleteResponse> future = pendingRequests.remove(requestId);
        if (future != null) {
            try {
                future.complete((AiChatCompleteResponse) response);
            } catch (ClassCastException e) {
                future.completeExceptionally(new IllegalStateException("Unexpected response type", e));
            }
        }
    }

    @Override
    public AiChatCompleteResponse askAI(ChatRequestBody body) {
        String requestId = CUID.randomCUID2().toString();

        CompletableFuture<AiChatCompleteResponse> future = new CompletableFuture<>();
        pendingRequests.put(requestId, future);

        rabbitTemplate.convertAndSend(
                rabbitMQConfigsProperties.getExchangeName(),
                rabbitMQConfigsProperties.getRequestRoutingKey(),
                new AiRequestBody(requestId, body.getModel(), body.getPrompt(), false, body.getImageb64()));

        try {
            return future.get(90, TimeUnit.SECONDS);
        } catch (Exception e) {
            pendingRequests.remove(requestId);
            throw new ChatTimeoutException();
        }
    }

    @Override
    public void askAI(ChatRequestBody body, OutputStream outputStream) {
        String requestId = CUID.randomCUID2().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        CountDownLatch completionLatch = new CountDownLatch(1);

        Consumer<AiChatStreamedResponse<?>> responseHandler = response -> {
            try {
                if (response == null) {
                    completionLatch.countDown();
                    return;
                }

                outputStream.write(objectMapper.writeValueAsBytes(response));
                outputStream.flush();

                if (response.isLastChunk())
                    completionLatch.countDown();
            } catch (Exception e) {
                completionLatch.countDown();
            }
        };

        streamingHandlers.put(requestId, responseHandler);

        rabbitTemplate.convertAndSend(
                rabbitMQConfigsProperties.getExchangeName(),
                rabbitMQConfigsProperties.getRequestRoutingKey(),
                new AiRequestBody(requestId, body.getModel(), body.getPrompt(), true, body.getImageb64()));

        try {
            if (!completionLatch.await(90, TimeUnit.SECONDS))
                streamingHandlers.remove(requestId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            streamingHandlers.remove(requestId);
        }
    }
}
