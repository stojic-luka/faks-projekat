package com.augmentedcooking.Constants;

import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Auth {

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class Jwt {

            public static final String TOKEN_TYPE = "Bearer";
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class URLs {

        private static final String FRONTEND_BASE_URL = "http://localhost:5173";
        public static final String FRONTEND_SIGNIN_URI = FRONTEND_BASE_URL + "/signin";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DB {

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class Collections {

            public static final String USERS = "users";
            public static final String USER_MESSAGES = "messages";
            public static final String USER_FAVORITE_RECIPES = "favoriteRecipes";
            public static final String RECIPES = "recipes";
        }
    }
}
