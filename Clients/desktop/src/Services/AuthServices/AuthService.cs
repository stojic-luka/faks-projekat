using AugmentedCooking.src.Config;
using AugmentedCooking.src.Helpers;
using AugmentedCooking.src.Models.Response.Auth;
using AugmentedCooking.src.Repositories.TokenStorageRepositories;
using AugmentedCooking.src.Utils;

namespace AugmentedCooking.src.Services.AuthServices;

public interface IAuthService {
    Task<(Uri AuthorizeUrl, string State)> GetAuthorizeUrlAsync();
    Task<TokenResponse> ExchangeCodeAsync(
      string code,
      string state,
      CancellationToken cancellationToken = default
    );
    Task<bool> IsLoggedInAsync();
}

public class AuthService(ISecureTokenStorage storage, IStateStore store) : IAuthService {
    readonly ISecureTokenStorage _storage = storage;
    readonly IStateStore _store = store;

    public Task<(Uri AuthorizeUrl, string State)> GetAuthorizeUrlAsync() {
        var codeVerifier = AuthUtils.GenerateRandomString(32);
        var state = AuthUtils.GenerateRandomString(16);
        _store.Store(state, codeVerifier);

        var codeChallenge = AuthUtils.ComputeSha512Base64Url(codeVerifier);

        var qs = new Dictionary<string, string> {
            ["redirect_uri"] = AuthConfig.BaseRedirectUri,
            ["state"] = state,
            ["code_challenge"] = codeChallenge,
            ["code_challenge_method"] = AuthConfig.CodeChallengeMethod
        };

        var url = $"{AuthConfig.BaseUri}?{AuthUtils.ToQueryString(qs)}";
        return Task.FromResult((new Uri(url), state));
    }

    public Task<TokenResponse> ExchangeCodeAsync(string code, string state, CancellationToken cancellationToken = default) {
        throw new NotImplementedException();
    }

    public async Task<bool> IsLoggedInAsync() {
        var token = await _storage.GetAccessTokenAsync();
        return !string.IsNullOrWhiteSpace(token);
    }
}
