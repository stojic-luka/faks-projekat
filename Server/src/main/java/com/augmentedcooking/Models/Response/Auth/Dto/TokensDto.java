package com.augmentedcooking.Models.Response.Auth.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class TokensDto {
    private String accessToken;
    private String refreshToken;
}
