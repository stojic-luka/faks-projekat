using AugmentedCooking.src.Repositories;
using AugmentedCooking.src.Services;

namespace AugmentedCooking.src.Views;
public partial class LoginPage : ContentPage {
    private readonly IAuthService _auth;
    private readonly ISecureTokenStorage _secureTokenStorage;

    private bool _isAuthBusy;
    public bool IsAuthBusy {
        get => _isAuthBusy;
        set { _isAuthBusy = value; OnPropertyChanged(); }
    }

    public LoginPage(IAuthService auth, ISecureTokenStorage secureTokenStorage) {
        InitializeComponent();

        _auth = auth;
        _secureTokenStorage = secureTokenStorage;
    }

    protected override async void OnAppearing() {
        base.OnAppearing();
        await StartAuthFlow();
    }

    async Task StartAuthFlow() {
        if (IsAuthBusy) return;
        IsAuthBusy = true;

        try {
            var (authUrl, expectedState) = await _auth.GetAuthorizeUrlAsync();

            var result = await WebAuthenticator.Default.AuthenticateAsync(authUrl, new Uri(_opts.RedirectUri));

            if (!result.Properties.TryGetValue("state", out var retState) || retState != expectedState)
                throw new Exception("Invalid OAuth state");

            if (!result.Properties.TryGetValue("code", out var code))
                throw new Exception("No authorization code returned");

            var tokens = await _auth.ExchangeCodeAsync(code, expectedState);

            await _secureTokenStorage.SaveTokensAsync(tokens);

            Application.Current.MainPage = new MainPage();
        }
        catch (Exception ex) {
            await Application.Current.MainPage.DisplayAlert("Error", ex.Message, "OK");
        }
        finally {
            IsAuthBusy = false;
        }
    }
}
