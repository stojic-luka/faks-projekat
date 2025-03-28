package com.augmentedcooking.Services.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentedcooking.Models.Database.User.User;
import com.augmentedcooking.Repositories.User.IUserRepository;

import io.github.thibaultmeyer.cuid.CUID;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> createUser(String email, String username, String password) {
        return userRepository.createUser(email, username, password);
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return userRepository.findById(CUID.fromString(userId));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean updateUserById(String userId, User updatedUser) {
        return userRepository.updateById(CUID.fromString(userId), updatedUser);
    }

    @Override
    public boolean updateUserPasswordById(String userId, String password) {
        return userRepository.updatePasswordById(CUID.fromString(userId), password);
    }

    @Override
    public boolean deleteUserById(String userId) {
        return userRepository.deleteById(CUID.fromString(userId));
    }

}
