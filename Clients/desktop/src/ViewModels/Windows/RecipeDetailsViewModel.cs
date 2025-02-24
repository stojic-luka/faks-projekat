using AugmentedCooking.src.Models;
using DesktopClient.src.Helpers;
using DesktopClient.src.Services;
using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;
using System.Windows.Media.Imaging;

namespace DesktopClient.src.ViewModels.Windows {
    internal class RecipeDetailsViewModel {
        public Recipe Recipe { get; }
        public string Title { get; }
        public string Ingredients { get; }
        public string Instructions { get; }
        public BitmapImage Image { get; }

        public ICommand SyncWithGlassesCommand { get; }

        public RecipeDetailsViewModel(Recipe recipe) {
            Recipe = recipe;

            Title = recipe.Title;
            Ingredients = string.Join(";\n", recipe.Ingredients);
            Instructions = recipe.Instructions;

            byte[] imageBytes = Convert.FromBase64String((string)recipe.Image);
            Image = new BitmapImage();
            using (var stream = new System.IO.MemoryStream(imageBytes)) {
                Image.BeginInit();
                Image.StreamSource = stream;
                Image.CacheOption = BitmapCacheOption.OnLoad;
                Image.EndInit();
            }

            SyncWithGlassesCommand = new RelayCommand(
                SyncWithGlasses,
                obj => obj != null && obj is Recipe
            );
        }

        private TcpServerService server;
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

        public event PropertyChangedEventHandler? PropertyChanged;
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = "") {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
