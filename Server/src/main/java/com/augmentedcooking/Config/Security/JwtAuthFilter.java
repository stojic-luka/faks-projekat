package com.augmentedcooking.Config.Security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.augmentedcooking.Utils.JwtUtils;

import io.jsonwebtoken.Claims;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Autowired
    public JwtAuthFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJWT(request);
            if (jwt != null && jwtUtils.validateAccess(jwt)) {
                Claims claims = jwtUtils.parseAccess(jwt);
                Object roles = claims.get("roles", Object.class);

                // List<String> roles = ((List<?>) rolesObject).stream()
                // .filter(String.class::isInstance)
                // .map(String.class::cast)
                // .collect(Collectors.toList());

                // List<GrantedAuthority> authorities = roles.stream()
                // .map(SimpleGrantedAuthority::new)
                // .collect(Collectors.toList());

                List<GrantedAuthority> authorities = ((List<?>) roles).stream()
                        .filter(String.class::isInstance)
                        .map(String.class::cast)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                var authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.println("Cannot set user authentication: " + e);
        }
        filterChain.doFilter(request, response);
    }

    private String getJWT(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
