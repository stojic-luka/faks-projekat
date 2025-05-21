using System.Windows.Input;
using AugmentedCooking.src.Helpers;
using AugmentedCooking.src.Models;
using AugmentedCooking.src.Services;

namespace AugmentedCooking.src.ViewModels.Controls.Tabs.ToolsTab {
    public class RandomRecipeViewModel : BaseViewModel {
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

        public RandomRecipeViewModel(RecipeService recipeService) {
            _recipeService = recipeService ?? throw new ArgumentNullException(nameof(recipeService));

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
    }
}
