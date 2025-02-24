package com.augmentedcooking.Models.Response.Auth;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class RefreshResponseBody {
    private String token;
}
