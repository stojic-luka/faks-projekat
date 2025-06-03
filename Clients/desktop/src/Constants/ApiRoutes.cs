namespace AugmentedCooking.src.Constants {
    public static class ApiRoutes {
        public const string BaseUrl = "http://172.24.133.85:8080";

        public static class Auth {
            public const string Login = "/api/v1/auth/login";                                               // POST
            public const string Register = "/api/v1/auth/register";                                         // POST
            public const string RefreshToken = "/api/v1/auth/refresh";                                      // GET
        }

        public static class Users {
            public const string Self = "/api/v1/user/self";                                                 // GET
        }

        public static class Recipes {
            public const string ListAll = "/api/v1/recipe?page={0}&limit={1}";                              // GET
            public const string ListByIngredients = "/api/v1/recipe?page={0}&limit={1}&ingredients={2}";    // GET
            public const string Random = "/api/v1/recipe/random";                                           // GET
            public const string Favorite = "/api/v1/recipe/favorite?page={0}&limit={1}";                    // GET
            public const string Create = "/api/v1/recipe";                                                  // POST
            public const string UpdateById = "/api/v1/recipe?id={0}";                                       // PUT
            public const string DeleteById = "/api/v1/recipe?id={0}";                                       // DELETE
        }

        public static class Chat {
            public const string SendMessage = "/api/v1/chat";                                               // POST
        }
    }
}
