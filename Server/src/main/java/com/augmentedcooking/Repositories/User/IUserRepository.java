package com.augmentedcooking.Repositories.User;

import java.util.Optional;
import java.util.UUID;

import org.bson.types.ObjectId;

import com.augmentedcooking.Models.Database.User.User;

public interface IUserRepository {

    Optional<User> createUser(String email, String username, String password);

    Optional<User> findById(ObjectId id);

    Optional<User> findByPublicId(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean updateById(ObjectId id, User updatedUser);

    boolean updatePasswordById(ObjectId id, String password);

    boolean deleteById(ObjectId id);
}
