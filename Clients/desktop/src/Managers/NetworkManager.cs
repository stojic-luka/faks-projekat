using System.Text;
using AugmentedCooking.src.Models.Response;
using AugmentedCooking.src.Services.UserServices;
using Newtonsoft.Json;
using Windows.Storage.Streams;

namespace AugmentedCooking.src.Managers;

public interface INetworkManager {
    Task<ApiResponse<T>> GetAsync<T>(string url, CancellationToken ct = default);
    Task<ApiResponse<T>> PostAsync<T>(string url, string jsonData, CancellationToken ct = default);
    Task<ApiResponse<T>> PutAsync<T>(string url, string jsonData, CancellationToken ct = default);
    Task<ApiResponse<T>> DeleteAsync<T>(string url, CancellationToken ct = default);

    Task<IInputStream> StreamGetAsync(string url, CancellationToken ct = default);
    Task<IInputStream> StreamPostAsync(string url, string jsonData, CancellationToken ct = default);
}

public class NetworkManager(HttpClient httpClient, IUserSession userSession) : INetworkManager {
    private readonly HttpClient _httpClient = httpClient ?? throw new ArgumentNullException(nameof(httpClient));
    private readonly IUserSession _userSession = userSession ?? throw new ArgumentNullException(nameof(userSession));

    public Task<ApiResponse<T>> GetAsync<T>(string url, CancellationToken ct) => SendRequestAsync<T>(HttpMethod.Get, url, ct);
    public Task<ApiResponse<T>> PostAsync<T>(string url, string jsonData, CancellationToken ct) => SendRequestAsync<T>(HttpMethod.Post, url, jsonData, ct);
    public Task<ApiResponse<T>> PutAsync<T>(string url, string jsonData, CancellationToken ct) => SendRequestAsync<T>(HttpMethod.Put, url, jsonData, ct);
    public Task<ApiResponse<T>> DeleteAsync<T>(string url, CancellationToken ct) => SendRequestAsync<T>(HttpMethod.Delete, url, ct);

    private Task<ApiResponse<T>> SendRequestAsync<T>(HttpMethod method, string url, CancellationToken ct) => SendRequestAsync<T>(method, url, string.Empty, ct);
    private async Task<ApiResponse<T>> SendRequestAsync<T>(HttpMethod method, string url, string jsonData = "", CancellationToken ct = default) {
        try {
            using var request = new HttpRequestMessage(method, url);
            if (!string.IsNullOrWhiteSpace(jsonData))
                request.Content = new StringContent(
                    jsonData,
                    Encoding.UTF8,
                    "application/json"
                );

            if (_userSession.IsLoggedIn && !string.IsNullOrWhiteSpace(_userSession.CurrentUser!.AccessToken))
                request.Headers.Authorization = new("Bearer", _userSession.CurrentUser.AccessToken);

            HttpResponseMessage response = await _httpClient.SendAsync(request, ct);

            string content = await response.Content.ReadAsStringAsync(ct);
            System.Diagnostics.Debug.WriteLine(content);

            var parsed = JsonConvert.DeserializeObject<ApiResponse<T>>(content);
            if (parsed != null)
                return parsed;
        }
        catch (Exception ex) {
            System.Diagnostics.Debug.WriteLine($"Error in SendRequestAsync: {ex.Message}");
        }

        return new ApiResponse<T>(new Error {
            Code = string.Empty,
            Message = "Unknown error occurred.",
        });
    }

    public Task<IInputStream> StreamGetAsync(string url, CancellationToken ct = default) => SendStreamRequestAsync(HttpMethod.Get, url, ct);
    public Task<IInputStream> StreamPostAsync(string url, string jsonData, CancellationToken ct = default) => SendStreamRequestAsync(HttpMethod.Post, url, jsonData, ct);

    private Task<IInputStream> SendStreamRequestAsync(HttpMethod method, string url, CancellationToken ct) => SendStreamRequestAsync(method, url, string.Empty, ct);
    private async Task<IInputStream> SendStreamRequestAsync(HttpMethod method, string url, string jsonData = "", CancellationToken ct = default) {
        try {
            using var request = new HttpRequestMessage(method, url);
            if (!string.IsNullOrWhiteSpace(jsonData))
                request.Content = new StringContent(
                    jsonData,
                    Encoding.UTF8,
                    "application/json"
                );

            if (_userSession.IsLoggedIn && !string.IsNullOrWhiteSpace(_userSession.CurrentUser!.AccessToken))
                request.Headers.Authorization = new("Bearer", _userSession.CurrentUser.AccessToken);

            HttpResponseMessage response = await _httpClient.SendAsync(request, ct);

            var netStream = await response.Content.ReadAsStreamAsync(ct);
            return netStream.AsInputStream();
        }
        catch (Exception) { }

        // TODO?: handle streaming errors
        return null!;
    }
}
