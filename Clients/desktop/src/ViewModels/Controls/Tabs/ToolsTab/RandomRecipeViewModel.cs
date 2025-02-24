using AugmentedCooking.src.Models;
using DesktopClient.src.Helpers;
using DesktopClient.src.Services;
using Microsoft.Extensions.DependencyInjection;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;

namespace DesktopClient.src.ViewModels.Controls.Tabs.ToolsTab {
    class RandomRecipeViewModel : INotifyPropertyChanged {
        private Recipe? _recipe;
        public Recipe Recipe {
            get => _recipe!;
            private set {
                _recipe = value;
                OnPropertyChanged(nameof(Recipe));
            }
        }

        public ICommand FetchRandomRecipe { get; }

        private readonly RecipeService _recipeService;
        public RandomRecipeViewModel() {
            _recipeService = App.ServiceProvider.GetRequiredService<RecipeService>();

            FetchRandomRecipe = new RelayCommand(
                async obj => await Task.Run(() => FetchRecipeAsync()),
                obj => true
            );
        }

        private async void FetchRecipeAsync() {
            Recipe? recipeResponse = await _recipeService.GetRandomRecipeAsync();
            if (recipeResponse != null) 
                Recipe = recipeResponse;
        }

        public event PropertyChangedEventHandler? PropertyChanged;
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = "") {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
