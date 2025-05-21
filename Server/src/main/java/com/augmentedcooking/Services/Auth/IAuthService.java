package com.augmentedcooking.Services.Auth;

import com.augmentedcooking.Models.Auth.Oauth2.AuthorizationCode;
import com.augmentedcooking.Models.Database.User.User;
import com.augmentedcooking.Models.Response.Auth.Dto.TokensDto;
import com.augmentedcooking.Models.Response.Auth.Dto.UserTokensDto;

public interface IAuthService {

    User authenticate(String identifier, String password);

    UserTokensDto login(String email, String password);

    UserTokensDto signin(String email, String username, String password);

    TokensDto refresh(String token);

    TokensDto oauth2Login(AuthorizationCode authCode);

    TokensDto oauth2Signin(String refreshToken, String clientId);

    TokensDto oauth2Refresh(String refreshToken, String clientId);
}
