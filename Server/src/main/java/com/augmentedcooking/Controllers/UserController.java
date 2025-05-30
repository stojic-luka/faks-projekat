package com.augmentedcooking.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.augmentedcooking.Exceptions.User.UserNotFoundException;
import com.augmentedcooking.Models.Database.User.User;
import com.augmentedcooking.Models.Response.ResponseWrapper;
import com.augmentedcooking.Models.Response.User.UserResponse;
import com.augmentedcooking.Services.User.IUserService;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(final IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> self(UsernamePasswordAuthenticationToken authentication) {
        Optional<User> user = userService.findUserById(authentication.getName());
        if (user.isEmpty())
            throw new UserNotFoundException();

        return ResponseWrapper.success(new UserResponse(user.get()));
    }
}
