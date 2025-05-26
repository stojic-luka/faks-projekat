using System.Windows.Input;
using AugmentedCooking.src.Helpers;
using AugmentedCooking.src.Models.Recipe;
using AugmentedCooking.src.Services.RecipeServices;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;

namespace AugmentedCooking.src.ViewModels.Controls.Tabs.ToolsTab {
    public partial class RandomRecipeViewModel : ObservableObject {
        [ObservableProperty]
        private Recipe? _recipe;

        public ICommand FetchRandomRecipe { get; }

        private readonly IRecipeService _recipeService;

        public RandomRecipeViewModel(IRecipeService recipeService) {
            _recipeService = recipeService ?? throw new ArgumentNullException(nameof(recipeService));

            FetchRandomRecipe = new AsyncRelayCommand(FetchRecipeAsync);
        }

        private async Task FetchRecipeAsync() {
            Recipe? recipeResponse = await _recipeService.GetRandomRecipeAsync();
            if (recipeResponse != null)
                Recipe = recipeResponse;
        }
    }
}
