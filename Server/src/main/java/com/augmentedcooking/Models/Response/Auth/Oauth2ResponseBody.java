package com.augmentedcooking.Models.Response.Auth;

import lombok.Getter;

import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class Oauth2ResponseBody {
    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private String refreshToken;
    private String scope;

    public Oauth2ResponseBody(String accessToken, String tokenType, int expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }
}
