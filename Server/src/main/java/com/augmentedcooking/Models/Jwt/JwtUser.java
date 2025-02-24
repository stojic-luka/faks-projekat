package com.augmentedcooking.Models.Jwt;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import lombok.Getter;

@Getter
public class JwtUser extends JwtAuthenticationToken {
    private final UUID id;

    public JwtUser(String jwt, Collection<? extends GrantedAuthority> authorities, UUID id) {
        super(Jwt.withTokenValue(jwt).build(), authorities);
        this.id = id;
    }
}
