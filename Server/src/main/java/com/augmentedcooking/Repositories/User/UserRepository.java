package com.augmentedcooking.Repositories.User;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.augmentedcooking.Models.Database.User.User;

@Repository
public class UserRepository implements IUserRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<User> createUser(String email, String username, String password) {
        User user = mongoTemplate.insert(new User(email, username, password));
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findById(ObjectId id) {
        User user = mongoTemplate.findById(id, User.class);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByPublicId(UUID id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("pubId").is(id));
        User user = mongoTemplate.findOne(query, User.class);
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
    public boolean updateById(ObjectId id, User updatedUser) {
        Optional<User> userOptional = this.findById(id);
        if (userOptional.isEmpty())
            return false;

        User user = userOptional.get();
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setUpdatedAt(Instant.now().toEpochMilli());
        mongoTemplate.save(user);
        return true;
    }

    @Override
    public boolean updatePasswordById(ObjectId id, String password) {
        Optional<User> userOptional = this.findById(id);
        if (userOptional.isEmpty())
            return false;

        User user = userOptional.get();
        user.updatePassword(password);
        mongoTemplate.save(user);
        return true;
    }

    @Override
    public boolean deleteById(ObjectId id) {
        Optional<User> userOptional = this.findById(id);
        if (userOptional.isEmpty())
            return false;

        mongoTemplate.remove(userOptional.get());
        return true;
    }
}
