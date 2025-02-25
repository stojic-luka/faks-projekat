<<<<<<< Updated upstream
package com.augmentedcooking.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.augmentedcooking.Models.Response.ResponseWrapper;
import com.augmentedcooking.Services.User.IUserService;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRecipes(UsernamePasswordAuthenticationToken authentication) {

        // testing
        return ResponseWrapper.success(authentication.getName());
    }
}
=======
package com.augmentedcooking.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.augmentedcooking.Models.Response.ResponseWrapper;
import com.augmentedcooking.Services.User.IUserService;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRecipes(UsernamePasswordAuthenticationToken authentication) {

        // testing
        return ResponseWrapper.success(authentication.getName());
    }
}
>>>>>>> Stashed changes
