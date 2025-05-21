namespace AugmentedCooking.src.Models.Deserialization.Auth;

public class TokenResponse {
    public string AccessToken { get; set; } = default!;
    public string RefreshToken { get; set; } = default!;
    public int ExpiresIn { get; set; }
}
