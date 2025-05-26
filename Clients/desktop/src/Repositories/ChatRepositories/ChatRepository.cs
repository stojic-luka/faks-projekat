using System.Net.Http.Json;
using System.Runtime.CompilerServices;
using System.Text;
using System.Text.Json;
using System.Text.RegularExpressions;
using AugmentedCooking.src.Constants;
using AugmentedCooking.src.Managers;
using AugmentedCooking.src.Models.Response;
using AugmentedCooking.src.Models.Response.Chat;
using AugmentedCooking.src.Models.Response.Chat.Complete;
using AugmentedCooking.src.Models.Response.Chat.Serialization;
using AugmentedCooking.src.Models.Response.Chat.Streamed;

namespace AugmentedCooking.src.Repositories.ChatRepositories;

public interface IChatRepository {
    IAsyncEnumerable<AiChatStreamedResponse> StreamMessageAsync(string prompt, string imageb64, CancellationToken ct);
    Task<AiChatCompleteResponse> SendMessageAsync(string prompt, string imageb64, CancellationToken ct);
}


public class ChatRepository : IChatRepository {
    readonly INetworkManager _networkManager;
    readonly JsonSerializerOptions _jsonOptions;
    static readonly Regex _jsonBoundary = new(@"(?<=\})(?=\{)", RegexOptions.Compiled);

    public ChatRepository(INetworkManager networkManager) {
        _networkManager = networkManager;

        _jsonOptions = new JsonSerializerOptions { PropertyNameCaseInsensitive = true };
        _jsonOptions.Converters.Add(new AiChatStreamedResponseConverter());
    }

    public async IAsyncEnumerable<AiChatStreamedResponse> StreamMessageAsync(string prompt, string imageb64, [EnumeratorCancellation] CancellationToken ct) {
        var inputStream = await _networkManager.StreamPostAsync(
               ApiRoutes.Chat.SendMessage,
               JsonSerializer.Serialize(new {
                   prompt,
                   imageb64,
                   streamed = true
               }),
               ct
           );

        using var reader = new StreamReader(
            inputStream.AsStreamForRead(),
            Encoding.UTF8
        );

        var sb = new StringBuilder();
        var buffer = new char[4096];

        while (!reader.EndOfStream && !ct.IsCancellationRequested) {
            int read = await reader.ReadAsync(buffer, ct);
            if (read <= 0) break;

            sb.Append(buffer, 0, read);
            var text = sb.ToString();
            var parts = _jsonBoundary.Split(text);

            foreach (var part in parts) {
                var chunk = JsonSerializer.Deserialize<AiChatStreamedResponse>(part, _jsonOptions);
                if (chunk != null)
                    yield return chunk;
            }

            sb.Clear();
            sb.Append(parts[^1]);
        }

        var leftover = sb.ToString().Trim();
        if (!string.IsNullOrEmpty(leftover)) {
            var chunk = JsonSerializer.Deserialize<AiChatStreamedResponse>(leftover, _jsonOptions);
            if (chunk != null)
                yield return chunk;
        }
    }

    public async Task<AiChatCompleteResponse> SendMessageAsync(string prompt, string imageb64, CancellationToken ct) {
        var resp = await _networkManager.PostAsync<AiChatCompleteResponse>(
            ApiRoutes.Chat.SendMessage,
            JsonSerializer.Serialize(new {
                prompt,
                imageb64,
                streamed = false
            }),
            ct
        );

        if (resp.IsSuccess)
            return resp.Data!;

        // TODO?: add logic for error handling
        return null!;
    }
}
