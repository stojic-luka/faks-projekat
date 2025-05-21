package com.augmentedcooking.Utils.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.augmentedcooking.Exceptions.Auth.OAuth2Exception;
import com.augmentedcooking.Models.Auth.Oauth2.AuthorizationCode;
import com.augmentedcooking.Utils.base.IAuthorizationCodeService;

public class MongoAuthorizationCodeService implements IAuthorizationCodeService {
    private final Map<String, AuthorizationCode> store = new ConcurrentHashMap<>();
    // TODO!: move to database from in-memory store

    @Override
    public String createAuthorizationCode(
            String clientId,
            String redirectUri,
            String scope,
            String userId,
            String codeChallenge,
            String codeChallengeMethod) {
        String raw = UUID.randomUUID().toString();
        String code = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(raw.getBytes(
                        StandardCharsets.UTF_8));
        AuthorizationCode ac = new AuthorizationCode(
                code,
                clientId,
                redirectUri,
                scope,
                codeChallenge,
                codeChallengeMethod,
                userId,
                Instant.now().plus(5, ChronoUnit.MINUTES));
        store.put(code, ac);
        return code;
    }

    @Override
    public AuthorizationCode validateAuthorizationCode(String code, String clientId, String redirectUri,
            String codeVerifier) {
        AuthorizationCode ac = store.remove(code);
        if (ac == null || !ac.getClientId().equals(clientId) || !ac.getRedirectUri().equals(redirectUri)
                || ac.isExpired())
            throw new OAuth2Exception();

        if (ac.getCodeChallenge() != null && !ac.getCodeChallenge().isEmpty()) {
            String computed = this.computeCodeChallenge(codeVerifier, ac.getCodeChallengeMethod());
            if (!computed.equals(ac.getCodeChallenge()))
                throw new OAuth2Exception();
        }
        return ac;
    }

    private String computeCodeChallenge(String verifier, String method) {
        try {
            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(
                            MessageDigest
                                    .getInstance("SHA-256")
                                    .digest(verifier.getBytes(StandardCharsets.US_ASCII)));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}
