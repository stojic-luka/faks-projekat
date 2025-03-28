package com.augmentedcooking.Utils.impl;

import java.security.Security;
import java.time.Instant;
import java.util.Date;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentedcooking.Config.Security.JwtSecretsConfig;
import com.augmentedcooking.Managers.Security.JwtKeyManager;
import com.augmentedcooking.Models.Database.User.User;
import com.augmentedcooking.Models.Jwt.ECKeyPair;
import com.augmentedcooking.Utils.base.IJwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtils implements IJwtUtils {

    private static ECKeyPair ACCESS_TOKEN_KEYS, REFRESH_TOKEN_KEYS;
    private long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 day
    private long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 5; // 5 days

    @Autowired
    public JwtUtils(JwtSecretsConfig jwtSecretsConfig) {
        var jwtAccessTokenProperties = jwtSecretsConfig.getJwtSecretProperties().getAccess();
        var jwtRefreshTokenProperties = jwtSecretsConfig.getJwtSecretProperties().getRefresh();

        ACCESS_TOKEN_KEYS = JwtKeyManager.loadAccessKeys(
                jwtAccessTokenProperties.getPrivateKey(),
                jwtAccessTokenProperties.getPublicKey());
        REFRESH_TOKEN_KEYS = JwtKeyManager.loadRefreshKeys(
                jwtRefreshTokenProperties.getPrivateKey(),
                jwtRefreshTokenProperties.getPublicKey());

        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Generates an access token for the given user and claims.
     *
     * @param user   the user for which the access token is generated
     * @param claims the claims to be included in the jwt
     * @return the generated access token
     */
    @Override
    public String generateAccess(User user) {
        Claims claims = Jwts.claims();
        claims.setSubject(user.getId().toString());
        claims.put("roles", user.getRoles());

        return generate(claims, ACCESS_TOKEN_KEYS, ACCESS_TOKEN_EXPIRATION_TIME, SignatureAlgorithm.ES256);
    }

    /**
     * Generates an refresh token for the given user and claims.
     *
     * @param user   the user for which the refresh token is generated
     * @param claims the claims to be included in the jwt
     * @return the generated refresh token
     */
    @Override
    public String generateRefresh(User user) {
        Claims claims = Jwts.claims();
        claims.setSubject(user.getId().toString());

        return generate(claims, REFRESH_TOKEN_KEYS, REFRESH_TOKEN_EXPIRATION_TIME, SignatureAlgorithm.ES512);
    }

    private String generate(Claims claims, ECKeyPair keyPair, long expirationTime, SignatureAlgorithm algorithm) {
        long currentTimeMillis = Instant.now().toEpochMilli();
        claims.setIssuedAt(new Date(currentTimeMillis));
        claims.setExpiration(new Date(currentTimeMillis + expirationTime));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(keyPair.getPrivateKey(), algorithm)
                .compact();
    }

    /**
     * Parses the given access token and returns the contained claims.
     *
     * @param token the access token to parse
     * @return the claims contained in the token
     * @throws JwtException if the token is invalid
     */
    @Override
    public Claims parseAccess(String token) throws JwtException {
        return parse(token, ACCESS_TOKEN_KEYS);
    }

    /**
     * Parses the given refresh token and returns the contained claims.
     *
     * @param token the refresh token to parse
     * @return the claims contained in the token
     * @throws JwtException if the token is invalid
     */
    @Override
    public Claims parseRefresh(String token) throws JwtException {
        return parse(token, REFRESH_TOKEN_KEYS);
    }

    private Claims parse(String token, ECKeyPair keyPair) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the given access token is valid.
     *
     * @param token the access token to check
     * @return true if the token is valid for the given user, false otherwise
     */
    @Override
    public boolean validateAccess(String token) {
        return validate(token, ACCESS_TOKEN_KEYS);
    }

    /**
     * Checks if the given refresh token is valid.
     *
     * @param token the refresh token to check
     * @return true if the token is valid for the given user, false otherwise
     */
    @Override
    public boolean validateRefresh(String token) {
        return validate(token, REFRESH_TOKEN_KEYS);
    }

    private boolean validate(String token, ECKeyPair keyPair) {
        try {
            parse(token, keyPair);
            return !isExpired(token, keyPair);
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Checks if the given access token is expired.
     *
     * @param token the access token to check
     * @return true if the token is expired, false otherwise
     */
    @Override
    public boolean isExpiredAccess(String token) {
        return isExpired(token, ACCESS_TOKEN_KEYS);
    }

    /**
     * Checks if the given refresh token is expired.
     *
     * @param token the refresh token to check
     * @return true if the token is expired, false otherwise
     */
    @Override
    public boolean isExpiredRefresh(String token) {
        return isExpired(token, REFRESH_TOKEN_KEYS);
    }

    private boolean isExpired(String token, ECKeyPair keyPair) {
        try {
            io.jsonwebtoken.Claims claims = parse(token, keyPair);
            return claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }
}
