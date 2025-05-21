namespace AugmentedCooking.src.Config;

public static class AuthConfig {
    public const string BaseUri = "http://localhost:8080";

    public const string BaseRedirectUri = "augmentedcooking://";

    public const string AuthorizeEndpoint = $"{BaseUri}/api/v1/oauth2/authorize";

    public const string TokenEndpoint = $"{BaseUri}/oauth2/token";

    public const string CodeChallengeMethod = "S256";
}
