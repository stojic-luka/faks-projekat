using System.Text;
using AugmentedCooking.src.Models.Deserialization;
using Newtonsoft.Json;

namespace AugmentedCooking.src.Managers;

public interface INetworkManager {
    Task<(int, ApiResponse<T>)> DeleteAsync<T>(string url);
    Task<(int, ApiResponse<T>)> GetAsync<T>(string url);
    Task<(int, ApiResponse<T>)> PostAsync<T>(string url, string jsonData);
    Task<(int, ApiResponse<T>)> PutAsync<T>(string url, string jsonData);
}

public class NetworkManager(HttpClient httpClient) : INetworkManager {
    private readonly HttpClient _httpClient =
        httpClient ?? throw new ArgumentNullException(nameof(httpClient));

    public Task<(int, ApiResponse<T>)> GetAsync<T>(string url) =>
        SendRequestAsync<T>(HttpMethod.Get, url);

    public Task<(int, ApiResponse<T>)> PostAsync<T>(string url, string jsonData) =>
        SendRequestAsync<T>(HttpMethod.Post, url, jsonData);

    public Task<(int, ApiResponse<T>)> PutAsync<T>(string url, string jsonData) =>
        SendRequestAsync<T>(HttpMethod.Put, url, jsonData);

    public Task<(int, ApiResponse<T>)> DeleteAsync<T>(string url) =>
        SendRequestAsync<T>(HttpMethod.Delete, url);

    private async Task<(int, ApiResponse<T>)> SendRequestAsync<T>(HttpMethod method, string url, string jsonData = "") {
        int statusCode = -1;
        try {
            var request = new HttpRequestMessage(method, url);
            if (!string.IsNullOrEmpty(jsonData))
                request.Content = new StringContent(
                    jsonData,
                    Encoding.UTF8,
                    "application/json"
                );

            HttpResponseMessage response = await _httpClient.SendAsync(request);
            statusCode = (int) response.StatusCode;

            string content = await response.Content.ReadAsStringAsync();
            ApiResponse<T>? deserializedBody = JsonConvert.DeserializeObject<ApiResponse<T>>(content);

#nullable disable
            return (statusCode, deserializedBody);
#nullable restore
        }
        catch (Exception) {
            return (statusCode, new ApiResponse<T> { Success = false, Error = null });
        }
    }
}
