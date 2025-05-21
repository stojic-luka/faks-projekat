package com.augmentedcooking.Utils.base;

import com.augmentedcooking.Models.Auth.Oauth2.AuthorizationCode;

public interface IAuthorizationCodeService {
        public String createAuthorizationCode(
                        String clientId,
                        String redirectUri,
                        String scope,
                        String userId,
                        String codeChallenge,
                        String codeChallengeMethod);

        public AuthorizationCode validateAuthorizationCode(
                        String code,
                        String clientId,
                        String redirectUri,
                        String codeVerifier);
}
