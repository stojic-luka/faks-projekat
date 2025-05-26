namespace AugmentedCooking.src.Repositories.TokenStorageRepositories;

public interface ISecureTokenStorage {
    Task<string?> GetAccessTokenAsync();
    Task SaveTokensAsync(string accessToken, string refreshToken);
    Task RemoveTokensAsync();
}


public class SecureTokenStorage : ISecureTokenStorage {
    const string AccessKey = "access_token";
    const string RefreshKey = "refresh_token";

    public Task<string?> GetAccessTokenAsync() => SecureStorage.Default.GetAsync(AccessKey);

    public async Task SaveTokensAsync(string accessToken, string refreshToken) {
        await SecureStorage.Default.SetAsync(AccessKey, accessToken);
        await SecureStorage.Default.SetAsync(RefreshKey, refreshToken);
    }

    public Task RemoveTokensAsync() {
        SecureStorage.Default.Remove(AccessKey);
        SecureStorage.Default.Remove(RefreshKey);
        return Task.CompletedTask;
    }
}
