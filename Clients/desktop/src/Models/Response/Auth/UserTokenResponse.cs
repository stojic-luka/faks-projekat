using AugmentedCooking.src.Models.User;
using Newtonsoft.Json;

namespace AugmentedCooking.src.Models.Response.Auth {
    public class UserTokenResponse {
        public required string Token { get; set; }
        public required User.User User { get; set; }

        [JsonConstructor]
        public UserTokenResponse(
            [JsonProperty("token")] string token,
            [JsonProperty("user")] User.User user
        ) {
            Token = token;
            User = user;
        }

        public override string ToString() {
            return $"UserTokenResponse: Token={Token}, User={User}";
        }
    }
}
