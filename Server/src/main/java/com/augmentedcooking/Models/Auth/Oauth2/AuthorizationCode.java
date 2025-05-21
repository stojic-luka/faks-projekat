package com.augmentedcooking.Models.Auth.Oauth2;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorizationCode {
    private final String code, clientId, redirectUri, scope;
    private final String codeChallenge, codeChallengeMethod;
    private final String userId;
    private final Instant expiresAt;

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
}
