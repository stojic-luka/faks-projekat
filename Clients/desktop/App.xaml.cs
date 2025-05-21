using AugmentedCooking.src.Services;
using AugmentedCooking.src.Views;

namespace AugmentedCooking;

public partial class App : Application {
    private readonly IAuthService _authService;
    public static IServiceProvider Services { get; private set; } = default!;

    public App(IServiceProvider services, IAuthService authService) {
        InitializeComponent();

        Services = services ?? throw new ArgumentNullException(nameof(services));
        _authService = authService ?? throw new ArgumentNullException(nameof(authService));

        MainPage = new ContentPage { };
        InitializeAsync();
    }

    async void InitializeAsync() {
        try {
            if (await _authService.IsLoggedInAsync()) {
                MainPage = new NavigationPage(Services.GetRequiredService<MainPage>());
                return;
            }
        }
        catch { }
        MainPage = new NavigationPage(Services.GetRequiredService<LoginPage>());
    }
}
