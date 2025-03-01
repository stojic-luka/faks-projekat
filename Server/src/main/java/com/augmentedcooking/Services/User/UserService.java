package com.augmentedcooking.Services.User;

import java.util.Optional;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentedcooking.Models.Database.User.User;
import com.augmentedcooking.Repositories.User.UserRepository;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> createUser(String email, String username, String password) {
        return userRepository.createUser(email, username, password);
    }

    @Override
    public Optional<User> findUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByPublicId(String id) {
        return userRepository.findByPublicId(UUID.fromString(id));
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
    public boolean updateUserById(ObjectId id, User updatedUser) {
        return userRepository.updateById(id, updatedUser);
    }

    @Override
    public boolean updateUserPasswordById(ObjectId id, String password) {
        return userRepository.updatePasswordById(id, password);
    }

    @Override
    public boolean deleteUserById(ObjectId id) {
        return userRepository.deleteById(id);
    }

}
