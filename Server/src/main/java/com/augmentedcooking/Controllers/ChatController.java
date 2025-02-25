package com.augmentedcooking.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.augmentedcooking.Exceptions.BaseResponseException;
import com.augmentedcooking.Exceptions.User.InvalidUUIDException;
import com.augmentedcooking.Models.Database.Chat.Message;
import com.augmentedcooking.Models.Response.ResponseWrapper;
import com.augmentedcooking.Models.Response.Chat.MessageResponseBody;
import com.augmentedcooking.Services.Chat.IChatService;
import com.augmentedcooking.Utils.ValidationUtils;

@RestController
@RequestMapping(path = "/api/v1/chat")
public class ChatController {

    private final IChatService chatService;

    @Autowired
    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getChatMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit,
            UsernamePasswordAuthenticationToken authentication) {
        List<Message> messages = chatService.getMessagesByUserId(authentication.getName(), page, limit);

        List<MessageResponseBody> responseBody = messages.stream()
                .map(r -> new MessageResponseBody(r))
                .collect(Collectors.toList());

        return ResponseWrapper.success(responseBody);
    }

    @PostMapping(produces = {
            MediaType.TEXT_EVENT_STREAM_VALUE,
            MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendChatMessage(UsernamePasswordAuthenticationToken authentication) {

        // TODO!: ADD MESSAGE SENDING LOGIC

        return ResponseWrapper.success(null);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteChatMessage(
            @RequestParam("id") String messageId,
            UsernamePasswordAuthenticationToken authentication) {
        if (!ValidationUtils.validateUUIDString(messageId))
            throw (BaseResponseException) new InvalidUUIDException();

        Message deletedMessage = chatService.deleteMessageByUserId(authentication.getName(), messageId);

        if (deletedMessage == null)
            // TODO: replace with adequate exceptions
            return ResponseWrapper.error(null, null);

        return ResponseWrapper.success(new MessageResponseBody(deletedMessage));
    }

    @DeleteMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> clearChatMessages(UsernamePasswordAuthenticationToken authentication) {
        boolean isClearSuccess = chatService.clearChatMessagesByUserId(authentication.getName());

        if (!isClearSuccess)
            // TODO: replace with adequate exceptions
            return ResponseWrapper.error(null, null);

        return ResponseWrapper.success(null);
    }
}
