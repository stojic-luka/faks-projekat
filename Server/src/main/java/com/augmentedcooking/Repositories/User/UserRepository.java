package com.augmentedcooking.Repositories.User;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.augmentedcooking.Models.Database.User.User;

import io.github.thibaultmeyer.cuid.CUID;

@Repository
public class UserRepository implements IUserRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<User> createUser(String email, String username, String password) {
        User user = mongoTemplate.insert(new User(email, username, password), "users");
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findById(CUID id) {
        User user = mongoTemplate.findById(id, User.class);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(query, User.class);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        User user = mongoTemplate.findOne(query, User.class);
        return Optional.ofNullable(user);
    }

    @Override
    public boolean updateById(CUID id, User updatedUser) {
        Optional<User> userOptional = this.findById(id);
        if (userOptional.isEmpty())
            return false;

        User user = userOptional.get();
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setUpdatedAt(Instant.now().toEpochMilli());
        mongoTemplate.save(user, "users");
        return true;
    }

    @Override
    public boolean updatePasswordById(CUID id, String password) {
        Optional<User> userOptional = this.findById(id);
        if (userOptional.isEmpty())
            return false;

        User user = userOptional.get();
        user.updatePassword(password);
        user.setUpdatedAt(Instant.now().toEpochMilli());
        mongoTemplate.save(user, "users");
        return true;
    }

    @Override
    public boolean deleteById(CUID id) {
        Optional<User> userOptional = this.findById(id);
        if (userOptional.isEmpty())
            return false;

        mongoTemplate.remove(userOptional.get(), "users");
        return true;
    }
}
