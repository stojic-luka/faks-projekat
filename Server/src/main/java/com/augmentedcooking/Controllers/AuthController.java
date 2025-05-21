package com.augmentedcooking.Controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.augmentedcooking.Containts.Constants;
import com.augmentedcooking.Exceptions.BaseResponseException;
import com.augmentedcooking.Exceptions.Auth.OAuth2Exception;
import com.augmentedcooking.Exceptions.Http.BadRequestException;
import com.augmentedcooking.Exceptions.Http.InternalServerException;
import com.augmentedcooking.Exceptions.Http.UnauthorizedException;
import com.augmentedcooking.Exceptions.User.InvalidCredentialsException;
import com.augmentedcooking.Models.Database.User.User;
import com.augmentedcooking.Models.Request.Auth.AuthRequestBody;
import com.augmentedcooking.Models.Response.ResponseWrapper;
import com.augmentedcooking.Models.Response.Auth.AuthResponseBody;
import com.augmentedcooking.Models.Response.Auth.Oauth2ResponseBody;
import com.augmentedcooking.Models.Response.Auth.RefreshResponseBody;
import com.augmentedcooking.Models.Response.Auth.Dto.TokensDto;
import com.augmentedcooking.Models.Response.Auth.Dto.UserTokensDto;
import com.augmentedcooking.Services.Auth.IAuthService;
import com.augmentedcooking.Utils.base.IAuthorizationCodeService;
import com.augmentedcooking.Utils.impl.JwtUtils;
import com.augmentedcooking.Utils.impl.ValidationUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final IAuthService authService;

    @Autowired
    public AuthController(final IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
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
                    body.getEmail() != null ? body.getEmail() : body.getUsername(),
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

    @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
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
                    body.getEmail(),
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
                // System.err.println(e);
                // for (StackTraceElement element : e.getStackTrace()) {
                // System.err.println(element);
                // }
                throw (BaseResponseException) new InternalServerException();
            }
            throw (BaseResponseException) e;
        }
    }

    @GetMapping(path = "/refresh")
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
                // System.err.println(e);
                // for (StackTraceElement element : e.getStackTrace()) {
                // System.err.println(element);
                // }
                throw (BaseResponseException) new InternalServerException();
            }
            throw (BaseResponseException) e;
        }
    }

    @RequestMapping(path = "/oauth2", produces = MediaType.APPLICATION_JSON_VALUE)
    public class OAuth2Controller {
        private final IAuthorizationCodeService codeService;

        @Autowired
        public OAuth2Controller(IAuthorizationCodeService codeService) {
            this.codeService = codeService;
        }

        @GetMapping(path = "/authorize")
        public ResponseEntity<?> authorize(
                @RequestParam("response_type") String responseType,
                @RequestParam("client_id") String clientId,
                @RequestParam("redirect_uri") String redirectUri,
                @RequestParam("code_challenge") String codeChallenge,
                @RequestParam("code_challenge_method") String codeChallengeMethod,
                @RequestParam(required = false) String scope,
                @RequestParam(required = false) String state) {
            if (!responseType.equals("code") || !codeChallengeMethod.equals("S256"))
                throw (BaseResponseException) new BadRequestException();

            // TODO!: Validate client_id and redirect_uri

            URI loginPage = UriComponentsBuilder
                    .fromUriString(Constants.URLs.FRONTEND_SIGNIN_URI)
                    .queryParam("response_type", responseType)
                    .queryParam("client_id", clientId)
                    .queryParam("redirect_uri", redirectUri)
                    .queryParam("code_challenge", codeChallenge)
                    .queryParam("code_challenge_method", codeChallengeMethod)
                    .queryParamIfPresent("scope", Optional.ofNullable(scope))
                    .queryParamIfPresent("state", Optional.ofNullable(state))
                    .build().toUri();

            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .location(loginPage)
                    .build();
        }

        @PostMapping(path = "/login")
        public ResponseEntity<?> login(
                @RequestParam(name = "client_id") String clientId,
                @RequestParam(name = "redirect_uri") String redirectUri,
                @RequestParam(required = false) String scope,
                @RequestParam(required = false) String state,
                @RequestParam(name = "code_challenge") String codeChallenge,
                @RequestParam(name = "code_challenge_method") String codeChallengeMethod,
                @RequestBody AuthRequestBody body) {
            if (body == null || (body.getUsername() == null && body.getEmail() == null) || body.getPassword() == null)
                throw (BaseResponseException) new BadRequestException();

            // TODO!: Validate client_id and redirect_uri

            if (body.getEmail() != null && !ValidationUtils.validateEmail(body.getEmail()))
                throw (BaseResponseException) new InvalidCredentialsException();
            if (body.getUsername() != null && !ValidationUtils.validateUsername(body.getUsername()))
                throw (BaseResponseException) new InvalidCredentialsException();
            if (!ValidationUtils.validatePassword(body.getPassword()))
                throw (BaseResponseException) new InvalidCredentialsException();

            User user = authService.authenticate(
                    body.getEmail() != null ? body.getEmail() : body.getUsername(),
                    body.getPassword());

            String code = codeService.createAuthorizationCode(
                    clientId,
                    redirectUri,
                    scope,
                    user.getId().toString(),
                    codeChallenge,
                    codeChallengeMethod);

            URI clientRedirect = UriComponentsBuilder
                    .fromUriString(redirectUri)
                    .queryParam("code", code)
                    .queryParamIfPresent("state", Optional.ofNullable(state))
                    .build().toUri();

            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .location(clientRedirect)
                    .build();
        }

        @PostMapping(path = "/token")
        public ResponseEntity<?> token(
                @RequestParam(name = "grant_type") String grantType,
                @RequestParam String code,
                @RequestParam(name = "redirect_uri") String redirectUri,
                @RequestParam(name = "client_id") String clientId,
                @RequestParam(name = "code_verifier") String codeVerifier,
                @RequestParam(name = "refresh_token", required = false) String refreshToken) {

            // TODO!: Validate client_id and redirect_uri
            // TODO: Implement refresh token flow

            try {
                TokensDto tokensDto;
                switch (grantType) {
                    case "authorization_code":
                        var authCode = codeService.validateAuthorizationCode(code, clientId, redirectUri, codeVerifier);
                        tokensDto = authService.oauth2Login(authCode);
                        break;

                    case "refresh_token":
                        tokensDto = authService.oauth2Refresh(refreshToken, clientId);
                        break;

                    default:
                        throw (BaseResponseException) new OAuth2Exception();
                }

                Oauth2ResponseBody responseBody = new Oauth2ResponseBody(
                        tokensDto.getAccessToken(),
                        Constants.Auth.Jwt.TOKEN_TYPE,
                        (int) (JwtUtils.ACCESS_TOKEN_EXPIRATION / 1000 / 60),
                        tokensDto.getRefreshToken());
                return ResponseWrapper.success(responseBody);
            } catch (OAuth2Exception e) {
                if (!(e instanceof BaseResponseException)) {
                    // System.err.println(e);
                    // for (StackTraceElement element : e.getStackTrace()) {
                    // System.err.println(element);
                    // }
                    throw (BaseResponseException) new InternalServerException();
                }
                throw (BaseResponseException) e;
            }
        }
    }
}
