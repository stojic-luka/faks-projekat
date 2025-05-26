using AugmentedCooking.src.Abstractions;
using AugmentedCooking.src.Models.Chat;

namespace AugmentedCooking.src.Repositories.ChatRepositories;

public interface IMessagesRepository : IStorage<ChatMessage> {
}


public class MessagesRepository() : IMessagesRepository {
    public Task InitializeAsync() {
        throw new NotImplementedException();
    }

    public Task<IList<ChatMessage>> GetAllAsync(string userId) {
        throw new NotImplementedException();
    }

    public Task AddAsync(ChatMessage msg) {
        throw new NotImplementedException();
    }

    public Task UpdateAsync(ChatMessage msg) {
        throw new NotImplementedException();
    }

    public Task DeleteAllAsync(string userId) {
        throw new NotImplementedException();
    }
}
