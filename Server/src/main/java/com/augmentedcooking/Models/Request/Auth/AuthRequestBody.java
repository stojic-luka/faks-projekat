package com.augmentedcooking.Models.Request.Auth;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequestBody {
    private String username;
    private String password;
    private String email;
}
