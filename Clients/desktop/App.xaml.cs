using DesktopClient.src.Managers;
using DesktopClient.src.Repositories;
using DesktopClient.src.Services;
using DesktopClient.src.ViewModels;
using DesktopClient.src.ViewModels.Controls;
using DesktopClient.src.ViewModels.Controls.Tabs.SearchTab;
using DesktopClient.src.ViewModels.Controls.Tabs.ToolsTab;
using DesktopClient.src.Views;
using Microsoft.Extensions.DependencyInjection;
using System.Windows;

namespace DesktopClient {
    public static class DependencyInjectionContainer {
        public static IServiceCollection ConfigureDependencies(this IServiceCollection services) {
            services.AddHttpClient();
            services.AddSingleton<NetworkManager>();

            services.AddSingleton<RecipeRepository>(provider => {
                var service = provider.GetRequiredService<NetworkManager>();
                return new(service);
            });

            services.AddSingleton<RecipeService>(provider => {
                var service = provider.GetRequiredService<RecipeRepository>();
                return new(service);
            });

            services.AddSingleton<MainViewModel>();
            services.AddSingleton<SidebarViewModel>();

            services.AddSingleton<SearchRecipeViewModel>();

            services.AddSingleton<FavoriteRecipesViewModel>();
            services.AddSingleton<RandomRecipeViewModel>();
            services.AddSingleton<StopwatchViewModel>();

            return services;
        }
    }

    public partial class App : Application {
        private static IServiceProvider _serviceProvider;
        public static IServiceProvider ServiceProvider => _serviceProvider;

        public App() {
            var serviceCollection = new ServiceCollection();

            _serviceProvider = serviceCollection
                .ConfigureDependencies()
                .BuildServiceProvider();
        }

        protected override void OnStartup(StartupEventArgs e) {
            base.OnStartup(e);

            MainWindow mainWindow = new();
            mainWindow.Show();
        }
    }
}
