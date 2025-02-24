package com.augmentedcooking.Utils;

import com.augmentedcooking.Models.Database.User.User;

import io.jsonwebtoken.Claims;

public interface IJwtUtils {

    public String generateAccess(User user);

    public String generateRefresh(User user);

    public Claims parseAccess(String token);

    public Claims parseRefresh(String token);

    public boolean validateAccess(String token);

    public boolean validateRefresh(String token);

    public boolean isExpiredAccess(String token);

    public boolean isExpiredRefresh(String token);
}
