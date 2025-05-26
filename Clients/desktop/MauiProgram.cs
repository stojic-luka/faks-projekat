using AugmentedCooking.src.Helpers;
using AugmentedCooking.src.Managers;
using AugmentedCooking.src.Services.AuthServices;
using AugmentedCooking.src.Services.RecipeServices;
using AugmentedCooking.src.Services.NetworkingServices;
using AugmentedCooking.src.ViewModels;
using AugmentedCooking.src.ViewModels.Controls;
using AugmentedCooking.src.ViewModels.Controls.Tabs.HomeTab;
using AugmentedCooking.src.ViewModels.Controls.Tabs.SearchTab;
using AugmentedCooking.src.ViewModels.Controls.Tabs.ToolsTab;
using AugmentedCooking.src.ViewModels.Windows;
using AugmentedCooking.src.Views;
using AugmentedCooking.src.Views.Windows;
using Microsoft.Extensions.Logging;
using AugmentedCooking.src.Repositories.TokenStorageRepositories;
using AugmentedCooking.src.Services.UserServices;
using AugmentedCooking.src.Repositories.RecipeRepositories;
using AugmentedCooking.src.Services.ChatServices;
using AugmentedCooking.src.Repositories.ChatRepositories;
using CommunityToolkit.Maui;
using AugmentedCooking.src.ViewModels.Controls.Tabs.LoginTab;
using AugmentedCooking.src.Constants;

namespace AugmentedCooking;

public static class MauiProgram {
    public static MauiApp CreateMauiApp() {
        var builder = MauiApp.CreateBuilder();
        builder
            .UseMauiApp<App>()
            .UseMauiCommunityToolkit()
            .ConfigureFonts(fonts => {
                fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
                fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
            });

        builder.Services.AddSingleton<IUserSession, UserSession>();
        builder.Services.AddSingleton<IStateStore, StateStore>();
        builder.Services.AddSingleton<ISecureTokenStorage, SecureTokenStorage>();
        builder.Services.AddSingleton<IUserSession, UserSession>();
        builder.Services.AddSingleton<IAuthService, AuthService>();

        builder.Services.AddScoped(sp => new HttpClient {
            BaseAddress = new Uri(ApiRoutes.BaseUrl)
        });

        builder.Services.AddSingleton<INetworkManager, NetworkManager>();
        builder.Services.AddSingleton<IMessagesService, MessagesService>();
        builder.Services.AddSingleton<IMessagesRepository, MessagesRepository>();
        builder.Services.AddSingleton<IChatSettingsService, ChatSettingsService>();
        builder.Services.AddSingleton<IChatService, ChatService>();
        builder.Services.AddSingleton<IChatRepository, ChatRepository>();
        builder.Services.AddSingleton<IRecipeRepository, RecipeRepository>();
        builder.Services.AddSingleton<IRecipeService, RecipeService>();
        builder.Services.AddSingleton<TcpServerService>();

        // ViewModels
        builder.Services.AddSingleton<MainViewModel>();
        builder.Services.AddSingleton<LoginTabViewModel>();
        builder.Services.AddSingleton<SidebarViewModel>();
        builder.Services.AddTransient<ChatPageViewModel>();
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
