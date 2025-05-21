using System.Collections.Concurrent;

namespace AugmentedCooking.src.Helpers;

public interface IStateStore {
    void Store(string state, string codeVerifier);
    string Retrieve(string state);
}

public class StateStore : IStateStore {
    private readonly ConcurrentDictionary<string, string> _store = new();

    public void Store(string key, string value) {
        _store[key] = value;
    }

    public string Retrieve(string key) {
        if (!_store.TryRemove(key, out var value))
            throw new InvalidOperationException("Invalid or expired state");
        return value;
    }
}
