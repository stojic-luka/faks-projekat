using System.Windows.Input;
using AugmentedCooking.src.Helpers;
using AugmentedCooking.src.Models.Recipe;
using AugmentedCooking.src.Services.NetworkingServices;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;

namespace AugmentedCooking.src.ViewModels.Windows {
    internal partial class RecipeDetailsViewModel : ObservableObject {
        public Recipe Recipe { get; }
        public string Title { get; }
        public string Ingredients { get; }
        public string Instructions { get; }
        public Image? Image { get; }

        public ICommand SyncWithGlassesCommand { get; }

        public RecipeDetailsViewModel(Recipe recipe) {
            Recipe = recipe;

            Title = recipe.Title;
            Ingredients = string.Join(";\n", recipe.Ingredients);
            Instructions = recipe.Instructions;

            if (recipe.Image != null && string.IsNullOrEmpty(recipe.Image.Data)) {
                byte[] imageBytes = Convert.FromBase64String(recipe.Image.Data);
                Image = new Image {
                    Source = ImageSource.FromStream(() => new MemoryStream(imageBytes)),
                };
            }

            SyncWithGlassesCommand = new RelayCommand<Recipe>(
                obj => SyncWithGlasses(obj!),
                obj => obj != null
            );
        }

        private TcpServerService? server;

        private async void SyncWithGlasses(object parameter) {
            if (parameter is Recipe recipeToSync) {
                server = new TcpServerService(port: 5000) { RecipeToSync = recipeToSync };

                using var cts = new CancellationTokenSource();
                CancellationToken token = cts.Token;

                Console.CancelKeyPress += (sender, eventArgs) => {
                    eventArgs.Cancel = true;
                    cts.Cancel();
                };

                await server.StartServerAsync(token);
            }
        }

        public void StopTcpServer() => server?.StopServer();
    }
}
