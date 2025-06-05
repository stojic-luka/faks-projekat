// RecipeDetailsViewModel.cs
using System;
using System.IO;
using System.Threading;
using System.Windows.Input;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Maui.Controls;
using AugmentedCooking.src.Models.Recipe;
using AugmentedCooking.src.Services.NetworkingServices;
using AugmentedCooking.src.Services.RecipeServices;
using AugmentedCooking.src.ViewModels.Controls.Tabs.SubmitTab;
using AugmentedCooking.src.Enums;

namespace AugmentedCooking.src.ViewModels.Windows {
    internal partial class RecipeDetailsViewModel : ObservableObject {
        public Recipe Recipe { get; }
        public string Title { get; }
        public string Ingredients { get; }
        public string Instructions { get; }
        public Image? Image { get; }

        public ICommand SyncWithGlassesCommand { get; }
        public ICommand EditCommand { get; }
        public ICommand DeleteCommand { get; }
        public ICommand CloseCommand { get; }

        private TcpServerService? server;

        public event Action RequestClose = delegate { };

        public RecipeDetailsViewModel(Recipe recipe) {
            Recipe = recipe ?? throw new ArgumentNullException(nameof(recipe));

            Title = recipe.Title;
            Ingredients = string.Join(";\n", recipe.Ingredients);
            Instructions = recipe.Instructions;

            if (recipe.Image != null && !string.IsNullOrEmpty(recipe.Image.Data)) {
                byte[] imageBytes = Convert.FromBase64String(recipe.Image.Data);
                Image = new Image {
                    Source = ImageSource.FromStream(() =>
                        new MemoryStream(imageBytes))
                };
            }

            SyncWithGlassesCommand = new RelayCommand<Recipe>(
                obj => SyncWithGlasses(obj!),
                obj => obj != null);

            EditCommand = new RelayCommand(EditRecipe);
            DeleteCommand = new RelayCommand(DeleteRecipe);

            CloseCommand = new RelayCommand(CloseWindow);
        }

        private async void SyncWithGlasses(object parameter) {
            if (parameter is Recipe recipeToSync) {
                server = new TcpServerService(port: 5000) {
                    RecipeToSync = recipeToSync
                };
                using var cts = new CancellationTokenSource();
                Console.CancelKeyPress += (s, e) => {
                    e.Cancel = true;
                    cts.Cancel();
                };
                await server.StartServerAsync(cts.Token);
            }
        }

        private void EditRecipe() {
            var mainVm = App.Services.GetRequiredService<MainViewModel>();
            mainVm.CurrentTab = MainTabs.Submit;

            var submitVm = App.Services.GetRequiredService<SubmitRecipeViewModel>();
            submitVm.NewRecipeTitle = Recipe.Title;
            submitVm.NewRecipeIngredients = string.Join(",", Recipe.Ingredients);
            submitVm.NewRecipeInstructions = Recipe.Instructions;

            CloseWindow();
        }

        private async void DeleteRecipe() {
            var recipeService = App.Services.GetRequiredService<IRecipeService>();
            await recipeService.DeleteRecipeAsync(Recipe.Id);
            CloseWindow();
        }

        private void CloseWindow() {
            StopTcpServer();
            RequestClose?.Invoke();
        }

        public void StopTcpServer() => server?.StopServer();
    }
}
