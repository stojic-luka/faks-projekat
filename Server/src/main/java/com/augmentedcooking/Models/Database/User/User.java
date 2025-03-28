package com.augmentedcooking.Models.Database.User;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.augmentedcooking.Utils.impl.Passwords;

import io.github.thibaultmeyer.cuid.CUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private CUID id;
    private String username;
    private String email;
    private byte[] passwordHash;
    private byte[] salt;
    private List<String> roles;
    private long createdAt;
    private long updatedAt;

    public User(String email, String username, String password) {
        this.id = CUID.randomCUID2();
        this.username = username;
        this.email = email;
        updatePassword(password);
        this.roles = List.of("user");
        this.createdAt = Instant.now().toEpochMilli();
        this.updatedAt = this.createdAt;
    }

    public boolean updatePassword(String password) {
        try {
            this.salt = Passwords.getNextSalt();
            this.passwordHash = Passwords.hash(password, this.salt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
