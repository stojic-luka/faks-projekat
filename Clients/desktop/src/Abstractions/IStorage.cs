namespace AugmentedCooking.src.Abstractions;

public interface IStorage<T> {
    Task InitializeAsync();
    Task<IList<T>> GetAllAsync(string userId);
    Task AddAsync(T item);
    Task UpdateAsync(T item);
    Task DeleteAllAsync(string userId);
}
