package com.augmentedcooking.Controllers;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.augmentedcooking.Exceptions.BaseResponseException;
import com.augmentedcooking.Exceptions.Chat.ChatClearFailedException;
import com.augmentedcooking.Exceptions.Chat.MessageNotFoundException;
import com.augmentedcooking.Exceptions.Http.BadRequestException;
import com.augmentedcooking.Exceptions.Http.InternalServerException;
import com.augmentedcooking.Exceptions.User.InvalidCUIDException;
import com.augmentedcooking.Models.Database.Chat.ChatMessage;
import com.augmentedcooking.Models.Rabbitmq.Response.complete.AiChatCompleteResponse;
import com.augmentedcooking.Models.Request.Chat.ChatRequestBody;
import com.augmentedcooking.Models.Response.ResponseWrapper;
import com.augmentedcooking.Models.Response.Chat.ChatMessageResponseBody;
import com.augmentedcooking.Services.Chat.base.IAiMessageService;
import com.augmentedcooking.Services.Chat.base.IChatService;

import io.github.thibaultmeyer.cuid.CUID;

@RestController
@RequestMapping(path = "/api/v1/chat")
public class ChatController {

    private final IChatService chatService;
    private final IAiMessageService aiMessageService;

    @Autowired
    public ChatController(final IChatService chatService, final IAiMessageService aiMessageService) {
        this.chatService = chatService;
        this.aiMessageService = aiMessageService;
    }

    // @GetMapping(path = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseWrapper<List<ChatMessageResponseBody>> getChatMessages(
    // @RequestParam(defaultValue = "0") int page,
    // @RequestParam(defaultValue = "20") int limit,
    // UsernamePasswordAuthenticationToken authentication) {
    // List<ChatMessage> messages =
    // chatService.getMessagesByUserId(authentication.getName(), page, limit);

    // List<ChatMessageResponseBody> responseBody = messages.stream()
    // .map(r -> new ChatMessageResponseBody(r))
    // .collect(Collectors.toList());

    // return ResponseWrapper.success(responseBody);
    // }

    @PostMapping(produces = {
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object sendChatMessage(
            @RequestBody ChatRequestBody body,
            UsernamePasswordAuthenticationToken authentication) {
        if (body == null
                || body.getModel() == null || body.getModel().isEmpty()
                || body.getPrompt() == null || body.getPrompt().isEmpty())
            throw (BaseResponseException) new BadRequestException();

        if (!body.isStreamed()) {
            AiChatCompleteResponse response = aiMessageService.askAI(body, authentication.getName());
            return ResponseWrapper.success(response, MediaType.APPLICATION_JSON);
        }

        return new StreamingResponseBody() {
            @Override
            public void writeTo(@NonNull OutputStream outputStream) {
                try {
                    aiMessageService.askAI(body, outputStream, authentication.getName());
                } catch (Exception e) {
                    if (!(e instanceof BaseResponseException)) {
                        System.err.println(e);
                        for (StackTraceElement element : e.getStackTrace()) {
                            System.err.println(element);
                        }
                        throw (BaseResponseException) new InternalServerException();
                    }
                    throw (BaseResponseException) e;
                }
            }
        };
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseWrapper<ChatMessageResponseBody> deleteChatMessage(
            @RequestParam("id") String messageId,
            UsernamePasswordAuthenticationToken authentication) {
        if (!CUID.isValid(messageId))
            throw (BaseResponseException) new InvalidCUIDException();

        ChatMessage deletedMessage = chatService.deleteMessageByUserId(authentication.getName(), messageId);
        if (deletedMessage == null)
            throw (BaseResponseException) new MessageNotFoundException();

        return ResponseWrapper.success(new ChatMessageResponseBody(deletedMessage));
    }

    @DeleteMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> clearChatMessages(UsernamePasswordAuthenticationToken authentication) {
        boolean isClearSuccess = chatService.clearChatMessagesByUserId(authentication.getName());
        if (!isClearSuccess)
            throw (BaseResponseException) new ChatClearFailedException();

        return ResponseWrapper.success(null);
    }

    @GetMapping(path = "/models", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseWrapper<List<String>> getAvailableAiModels() {
        return ResponseWrapper.success(
                Arrays.asList(
                        "llama3.2:3b",
                        "deepseek-r1:8b",
                        "llava:7b"));
    }
}
