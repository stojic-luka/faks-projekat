package com.augmentedcooking.Repositories.User;

import java.util.Optional;

import com.augmentedcooking.Models.Database.User.User;

import io.github.thibaultmeyer.cuid.CUID;

public interface IUserRepository {

    Optional<User> createUser(String email, String username, String password);

    Optional<User> findById(CUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean updateById(CUID id, User updatedUser);

    boolean updatePasswordById(CUID id, String password);

    boolean deleteById(CUID id);
}
