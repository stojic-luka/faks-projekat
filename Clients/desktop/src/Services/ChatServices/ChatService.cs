using System.Runtime.CompilerServices;
using AugmentedCooking.src.Models.Response.Chat.Complete;
using AugmentedCooking.src.Models.Response.Chat.Streamed;
using AugmentedCooking.src.Repositories.ChatRepositories;

namespace AugmentedCooking.src.Services.ChatServices;

public interface IChatService {
    IAsyncEnumerable<AiChatStreamedResponse> StreamMessageAsync(string prompt, string imageb64, CancellationToken ct);
    Task<AiChatCompleteResponse> SendMessageAsync(string prompt, string imageb64, CancellationToken ct);
}

public class ChatService(IChatRepository chatRepository) : IChatService {
    private readonly IChatRepository _chatRepository = chatRepository;

    public IAsyncEnumerable<AiChatStreamedResponse> StreamMessageAsync(string prompt, string imageb64, CancellationToken ct) {
        return _chatRepository.StreamMessageAsync(prompt, imageb64, ct);
    }

    public async Task<AiChatCompleteResponse> SendMessageAsync(string prompt, string imageb64, CancellationToken ct) {
        return await _chatRepository.SendMessageAsync(prompt, imageb64, ct);
    }
}
