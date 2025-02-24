package com.augmentedcooking.Models.Response.User;

import java.util.List;
import java.util.UUID;

import com.augmentedcooking.Models.Database.User.User;

import lombok.Getter;

@Getter
public class UserResponse {

    private final UUID pubId;
    private final String username;
    private final String email;
    private final List<String> roles;

    public UserResponse(UUID pubId, String username, String email, List<String> roles) {
        this.pubId = pubId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public UserResponse(User user) {
        this.pubId = user.getPubId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
