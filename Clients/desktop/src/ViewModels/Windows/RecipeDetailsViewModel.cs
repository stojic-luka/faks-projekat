using System.Windows.Input;
using AugmentedCooking.src.Helpers;
using AugmentedCooking.src.Models;
using AugmentedCooking.src.Services;

namespace AugmentedCooking.src.ViewModels.Windows {
    internal class RecipeDetailsViewModel : BaseViewModel {
        public Recipe Recipe { get; }
        public string Title { get; }
        public string Ingredients { get; }
        public string Instructions { get; }
        public Image Image { get; }

        public ICommand SyncWithGlassesCommand { get; }

        public RecipeDetailsViewModel(Recipe recipe) {
            Recipe = recipe;

            Title = recipe.Title;
            Ingredients = string.Join(";\n", recipe.Ingredients);
            Instructions = recipe.Instructions;

            byte[] imageBytes = Convert.FromBase64String((string) recipe.Image);
            Image = new Image {
                Source = ImageSource.FromStream(() => new MemoryStream(imageBytes)),
            };

            SyncWithGlassesCommand = new RelayCommand(
                SyncWithGlasses,
                obj => obj != null && obj is Recipe
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
