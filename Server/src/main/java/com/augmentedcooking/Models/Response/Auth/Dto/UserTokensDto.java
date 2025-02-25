package com.augmentedcooking.Models.Response.Auth.Dto;

import lombok.Getter;
import lombok.Setter;

import com.augmentedcooking.Models.Database.User.User;

import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class UserTokensDto {
    private User user;
    private String accessToken;
    private String refreshToken;
}
