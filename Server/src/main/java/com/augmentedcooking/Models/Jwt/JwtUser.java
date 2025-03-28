package com.augmentedcooking.Models.Jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import io.github.thibaultmeyer.cuid.CUID;
import lombok.Getter;

@Getter
public class JwtUser extends JwtAuthenticationToken {
    private final CUID id;

    public JwtUser(String jwt, Collection<? extends GrantedAuthority> authorities, CUID id) {
        super(Jwt.withTokenValue(jwt).build(), authorities);
        this.id = id;
    }
}
