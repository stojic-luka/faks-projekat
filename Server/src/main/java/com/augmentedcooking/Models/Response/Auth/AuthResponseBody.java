package com.augmentedcooking.Models.Response.Auth;

import lombok.Getter;
import lombok.Setter;

import com.augmentedcooking.Models.Database.User.User;
import com.augmentedcooking.Models.Response.User.UserResponse;

import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseBody {
    private UserResponse user;
    private String token;

    public AuthResponseBody(User user, String token) {
        this.user = new UserResponse(user);
        this.token = token;
    }
}
