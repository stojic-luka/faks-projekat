using AugmentedCooking.src.Models.User;
using CommunityToolkit.Mvvm.ComponentModel;

namespace AugmentedCooking.src.Services.UserServices;

public interface IUserSession {
    User? CurrentUser { get; }
    bool IsLoggedIn { get; }
    event Action<User> SignedIn;
    event Action SignedOut;

    void SetCurrentUser(User u);
    void Clear();
}

public partial class UserSession : ObservableObject, IUserSession {
    User? _current;
    public User? CurrentUser => _current;
    public bool IsLoggedIn => _current != null;
    public event Action<User> SignedIn = delegate { };
    public event Action SignedOut = delegate { };

    public void SetCurrentUser(User u) {
        _current = u;
        OnPropertyChanged(nameof(CurrentUser));
        OnPropertyChanged(nameof(IsLoggedIn));
        SignedIn?.Invoke(u);
    }

    public void Clear() {
        _current = null;
        OnPropertyChanged(nameof(CurrentUser));
        OnPropertyChanged(nameof(IsLoggedIn));
        SignedOut?.Invoke();
    }
}
