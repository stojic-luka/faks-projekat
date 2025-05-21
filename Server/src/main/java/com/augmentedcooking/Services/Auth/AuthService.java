package com.augmentedcooking.Services.Auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentedcooking.Exceptions.BaseResponseException;
import com.augmentedcooking.Exceptions.User.InvalidCredentialsException;
import com.augmentedcooking.Exceptions.User.UserAlreadyExistException;
import com.augmentedcooking.Exceptions.User.UserNotFoundException;
import com.augmentedcooking.Models.Auth.Oauth2.AuthorizationCode;
import com.augmentedcooking.Models.Database.User.User;
import com.augmentedcooking.Models.Response.Auth.Dto.TokensDto;
import com.augmentedcooking.Models.Response.Auth.Dto.UserTokensDto;
import com.augmentedcooking.Repositories.User.IUserRepository;
import com.augmentedcooking.Utils.base.IJwtUtils;
import com.augmentedcooking.Utils.impl.Passwords;

import io.github.thibaultmeyer.cuid.CUID;
import io.jsonwebtoken.Claims;

@Service
public class AuthService implements IAuthService {

    private final IUserRepository userRepository;
    private final IJwtUtils jwtUtils;

    @Autowired
    public AuthService(final IUserRepository userRepository, final IJwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User authenticate(String identifier, String password) {
        Optional<User> userOptional = userRepository.findByEmail(identifier);
        if (userOptional.isEmpty())
            userOptional = userRepository.findByUsername(identifier);
        if (userOptional.isEmpty())
            throw (BaseResponseException) new UserNotFoundException();

        User user = userOptional.get();
        if (!Passwords.isValid(password.toCharArray(), user.getSalt(), user.getPasswordHash()))
            throw (BaseResponseException) new InvalidCredentialsException();

        return user;
    }

    @Override
    public UserTokensDto login(String identifier, String password) {
        Optional<User> userOptional = userRepository.findByEmail(identifier);
        if (userOptional.isEmpty())
            userOptional = userRepository.findByUsername(identifier);
        if (userOptional.isEmpty())
            throw (BaseResponseException) new UserNotFoundException();

        User user = userOptional.get();
        if (!Passwords.isValid(password.toCharArray(), user.getSalt(), user.getPasswordHash()))
            throw (BaseResponseException) new InvalidCredentialsException();

        return new UserTokensDto(
                user,
                jwtUtils.generateAccess(user),
                jwtUtils.generateRefresh(user));
    }

    @Override
    public UserTokensDto signin(String email, String username, String password) {
        if (userRepository.findByEmail(email).isPresent())
            throw (BaseResponseException) new UserAlreadyExistException();

        Optional<User> userOptional = userRepository.createUser(email, username, password);
        if (userOptional.isEmpty())
            throw (BaseResponseException) new UserNotFoundException();

        User user = userOptional.get();
        return new UserTokensDto(
                user,
                jwtUtils.generateAccess(user),
                jwtUtils.generateRefresh(user));
    }

    @Override
    public TokensDto refresh(String token) {
        if (!jwtUtils.validateRefresh(token))
            return null;

        Claims claims = jwtUtils.parseRefresh(token);
        Optional<User> userOptional = userRepository.findById(CUID.fromString(claims.getSubject()));
        if (userOptional.isEmpty())
            return null;

        User user = userOptional.get();
        return new TokensDto(
                jwtUtils.generateAccess(user),
                jwtUtils.generateRefresh(user));
    }

    @Override
    public TokensDto oauth2Login(AuthorizationCode authCode) {
        Optional<User> userOptional = userRepository.findById(CUID.fromString(authCode.getUserId()));
        if (userOptional.isEmpty())
            return null;

        User user = userOptional.get();
        return new TokensDto(
                jwtUtils.generateAccess(user),
                jwtUtils.generateRefresh(user));
    }

    @Override
    public TokensDto oauth2Signin(String refreshToken, String clientId) {
        throw new UnsupportedOperationException("Unimplemented method 'oauth2Signin'");
    }

    @Override
    public TokensDto oauth2Refresh(String refreshToken, String clientId) {

        throw new UnsupportedOperationException("Unimplemented method 'oauth2Refresh'");
    }
}
