using AugmentedCooking.src.Models.Chat;
using AugmentedCooking.src.Repositories.ChatRepositories;

namespace AugmentedCooking.src.Services.ChatServices;

public interface IMessagesService {
    Task InitializeAsync();
    Task<IList<ChatMessage>> GetAllAsync(string userId);
    Task AddAsync(ChatMessage msg);
    Task UpdateAsync(ChatMessage msg);
    Task DeleteAllAsync(string userId);
}

public class MessagesService(IMessagesRepository chatRepository) : IMessagesService {
    private readonly IMessagesRepository _messagesRepository = chatRepository;

    public async Task InitializeAsync() {
        await _messagesRepository.InitializeAsync();
    }

    public async Task<IList<ChatMessage>> GetAllAsync(string userId) {
        return await _messagesRepository.GetAllAsync(userId);
    }

    public async Task AddAsync(ChatMessage msg) {
        await _messagesRepository.AddAsync(msg);
    }

    public async Task UpdateAsync(ChatMessage msg) {
        await _messagesRepository.UpdateAsync(msg);
    }

    public async Task DeleteAllAsync(string userId) {
        await _messagesRepository.DeleteAllAsync(userId);
    }
}
