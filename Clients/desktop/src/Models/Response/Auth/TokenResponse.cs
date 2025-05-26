using System.Text.Json.Serialization;

namespace AugmentedCooking.src.Models.Response.Auth;

public class TokenResponse {
    [JsonPropertyName("access_token")]
    public string AccessToken { get; set; } = default!;

    [JsonPropertyName("refresh_token")]
    public string RefreshToken { get; set; } = default!;

    [JsonPropertyName("expires_in")]
    public int ExpiresIn { get; set; }
}
