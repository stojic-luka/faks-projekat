package com.augmentedcooking.Services.User;

import java.util.Optional;

import org.bson.types.ObjectId;

import com.augmentedcooking.Models.Database.User.User;

public interface IUserService {
    Optional<User> createUser(String email, String username, String password);

    Optional<User> findUserById(ObjectId id);

    Optional<User> findUserByPublicId(String id);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    boolean updateUserById(ObjectId id, User updatedUser);

    boolean updateUserPasswordById(ObjectId id, String password);

    boolean deleteUserById(ObjectId id);
}
