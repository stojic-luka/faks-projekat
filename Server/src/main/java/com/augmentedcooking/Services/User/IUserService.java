package com.augmentedcooking.Services.User;

import java.util.Optional;

import com.augmentedcooking.Models.Database.User.User;

public interface IUserService {
    Optional<User> createUser(String email, String username, String password);

    Optional<User> findUserById(String userId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    boolean updateUserById(String userId, User updatedUser);

    boolean updateUserPasswordById(String userId, String password);

    boolean deleteUserById(String userId);
}
