using AugmentedCooking.src.Managers;
using AugmentedCooking.src.Repositories;
using AugmentedCooking.src.Services;
using AugmentedCooking.src.ViewModels;
using AugmentedCooking.src.ViewModels.Controls;
using AugmentedCooking.src.ViewModels.Controls.Tabs.SearchTab;
using AugmentedCooking.src.ViewModels.Controls.Tabs.ToolsTab;
using AugmentedCooking.src.ViewModels.Windows;
using AugmentedCooking.src.Views;
using AugmentedCooking.src.Views.Windows;
using Microsoft.Extensions.Logging;

namespace AugmentedCooking;

public static class MauiProgram {
    public static MauiApp CreateMauiApp() {
        var builder = MauiApp.CreateBuilder();
        builder
            .UseMauiApp<App>()
            .ConfigureFonts(fonts => {
                fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
                fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
            });

        builder.Services.AddSingleton<ISecureTokenStorage, SecureTokenStorage>();
        builder.Services.AddSingleton<IAuthService, AuthService>();

        builder.Services.AddScoped(sp => new HttpClient { });

        builder.Services.AddSingleton<INetworkManager, NetworkManager>();
        builder.Services.AddSingleton<IRecipeRepository, RecipeRepository>();
        builder.Services.AddSingleton<IRecipeService, RecipeService>();
        builder.Services.AddSingleton<TcpServerService>();

        // ViewModels
        builder.Services.AddSingleton<MainViewModel>();
        builder.Services.AddSingleton<SidebarViewModel>();
        builder.Services.AddTransient<SearchRecipeViewModel>();
        builder.Services.AddTransient<FavoriteRecipesViewModel>();
        builder.Services.AddTransient<RandomRecipeViewModel>();
        builder.Services.AddTransient<StopwatchViewModel>();
        builder.Services.AddTransient<RecipeDetailsViewModel>();

        // Pages
        builder.Services.AddSingleton<MainPage>();
        builder.Services.AddTransient<RecipeDetailsPage>();

#if DEBUG
        builder.Logging.AddDebug();
#endif

        var mauiApp = builder.Build();
        return mauiApp;
    }
}
