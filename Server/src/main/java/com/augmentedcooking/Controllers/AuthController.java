package com.augmentedcooking.Controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.augmentedcooking.Exceptions.BaseResponseException;
import com.augmentedcooking.Exceptions.Http.BadRequestException;
import com.augmentedcooking.Exceptions.Http.InternalServerException;
import com.augmentedcooking.Exceptions.Http.UnauthorizedException;
import com.augmentedcooking.Exceptions.User.InvalidCredentialsException;
import com.augmentedcooking.Models.Request.Auth.AuthRequestBody;
import com.augmentedcooking.Models.Response.ResponseWrapper;
import com.augmentedcooking.Models.Response.Auth.AuthResponseBody;
import com.augmentedcooking.Models.Response.Auth.RefreshResponseBody;
import com.augmentedcooking.Models.Response.Auth.Dto.TokensDto;
import com.augmentedcooking.Models.Response.Auth.Dto.UserTokensDto;
import com.augmentedcooking.Services.Auth.IAuthService;
import com.augmentedcooking.Utils.ValidationUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(
            @RequestBody AuthRequestBody body,
            HttpServletResponse response) {
        try {
            if (body == null || (body.getUsername() == null && body.getEmail() == null) || body.getPassword() == null)
                throw (BaseResponseException) new BadRequestException();

            if (body.getEmail() != null && !ValidationUtils.validateEmail(body.getEmail()))
                throw (BaseResponseException) new InvalidCredentialsException();
            if (body.getUsername() != null && !ValidationUtils.validateUsername(body.getUsername()))
                throw (BaseResponseException) new InvalidCredentialsException();
            if (!ValidationUtils.validatePassword(body.getPassword()))
                throw (BaseResponseException) new InvalidCredentialsException();

            UserTokensDto userTokensDto = authService.login(
                    body.getUsername(),
                    body.getPassword());

            Cookie responseRefreshTokenCookie = new Cookie("refresh-token", userTokensDto.getRefreshToken());
            responseRefreshTokenCookie.setHttpOnly(true);
            responseRefreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
            responseRefreshTokenCookie.setComment("SameSite=Strict");
            // Only true if using secure protocol (eg. HTTPS)
            responseRefreshTokenCookie.setSecure(false);

            response.addCookie(responseRefreshTokenCookie);

            var responseBody = new AuthResponseBody(userTokensDto.getUser(), userTokensDto.getAccessToken());
            return ResponseWrapper.success(responseBody);
        } catch (Exception e) {
            if (!(e instanceof BaseResponseException)) {
                System.err.println(e);
                for (StackTraceElement element : e.getStackTrace()) {
                    System.err.println(element);
                }
                throw (BaseResponseException) new InternalServerException();
            }
            throw (BaseResponseException) e;
        }
    }

    @PostMapping(path = "/signin", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signin(
            @RequestBody AuthRequestBody body,
            HttpServletResponse response) {
        try {
            if (body == null || body.getUsername() == null || body.getPassword() == null || body.getEmail() == null)
                throw (BaseResponseException) new BadRequestException();

            if (!ValidationUtils.validateEmail(body.getEmail()))
                throw (BaseResponseException) new InvalidCredentialsException();
            if (!ValidationUtils.validateUsername(body.getUsername()))
                throw (BaseResponseException) new InvalidCredentialsException();
            if (!ValidationUtils.validatePassword(body.getPassword()))
                throw (BaseResponseException) new InvalidCredentialsException();

            UserTokensDto userTokensDto = authService.signin(
                    body.getUsername(),
                    body.getPassword(),
                    body.getEmail());

            Cookie responseRefreshTokenCookie = new Cookie("refresh-token", userTokensDto.getRefreshToken());
            responseRefreshTokenCookie.setHttpOnly(true);
            responseRefreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
            responseRefreshTokenCookie.setComment("SameSite=Strict");
            // Only true if using secure protocol (eg. HTTPS)
            responseRefreshTokenCookie.setSecure(false);

            response.addCookie(responseRefreshTokenCookie);

            var responseBody = new AuthResponseBody(userTokensDto.getUser(), userTokensDto.getAccessToken());
            return ResponseWrapper.success(responseBody);
        } catch (Exception e) {
            if (!(e instanceof BaseResponseException)) {
                System.err.println(e);
                for (StackTraceElement element : e.getStackTrace()) {
                    System.err.println(element);
                }
                throw (BaseResponseException) new InternalServerException();
            }
            throw (BaseResponseException) e;
        }
    }

    @GetMapping(path = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refresh(
            HttpServletResponse response,
            HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies == null || cookies.length == 0)
                throw (BaseResponseException) new UnauthorizedException();

            Optional<Cookie> refreshTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "refresh-token".equals(cookie.getName()))
                    .findFirst();

            if (refreshTokenCookie.isEmpty())
                throw (BaseResponseException) new UnauthorizedException();

            TokensDto tokensDto = authService.refresh(refreshTokenCookie.get().getValue());

            Cookie responseRefreshTokenCookie = new Cookie("refresh-token", tokensDto.getRefreshToken());
            responseRefreshTokenCookie.setHttpOnly(true);
            responseRefreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
            responseRefreshTokenCookie.setComment("SameSite=Strict");
            // Only true if using secure protocol (eg. HTTPS)
            responseRefreshTokenCookie.setSecure(false);
            response.addCookie(responseRefreshTokenCookie);

            RefreshResponseBody responseBody = new RefreshResponseBody(tokensDto.getAccessToken());
            return ResponseWrapper.success(responseBody);
        } catch (Exception e) {
            if (!(e instanceof BaseResponseException)) {
                System.err.println(e);
                for (StackTraceElement element : e.getStackTrace()) {
                    System.err.println(element);
                }
                throw (BaseResponseException) new InternalServerException();
            }
            throw (BaseResponseException) e;
        }
    }
}
