package com.augmentedcooking.Models.Response.User;

import java.util.List;

import com.augmentedcooking.Models.Database.User.User;

import io.github.thibaultmeyer.cuid.CUID;
import lombok.Getter;

@Getter
public class UserResponse {

    private final String id;
    private final String username;
    private final String email;
    private final List<String> roles;

    public UserResponse(CUID id, String username, String email, List<String> roles) {
        this.id = id.toString();
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public UserResponse(User user) {
        this.id = user.getId().toString();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
