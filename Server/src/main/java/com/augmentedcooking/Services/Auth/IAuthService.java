package com.augmentedcooking.Services.Auth;

import com.augmentedcooking.Models.Response.Auth.Dto.TokensDto;
import com.augmentedcooking.Models.Response.Auth.Dto.UserTokensDto;

public interface IAuthService {

    UserTokensDto login(String email, String password);

    UserTokensDto signin(String email, String username, String password);

    TokensDto refresh(String token);
}
