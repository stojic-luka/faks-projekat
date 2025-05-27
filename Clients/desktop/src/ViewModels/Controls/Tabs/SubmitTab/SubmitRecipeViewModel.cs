using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using AugmentedCooking.src.Models.Recipe;
using AugmentedCooking.src.Services.RecipeServices;

namespace AugmentedCooking.src.ViewModels.Controls.Tabs.SubmitTab {
  public partial class SubmitRecipeViewModel : ObservableObject {
    private readonly IRecipeService _recipeService;

    [ObservableProperty]
    private string _newRecipeTitle = string.Empty;

    [ObservableProperty]
    private string _newRecipeIngredients = string.Empty;

    [ObservableProperty]
    private string _newRecipeInstructions = string.Empty;

    [ObservableProperty]
    private string _deleteRecipeId = string.Empty;

    public SubmitRecipeViewModel(IRecipeService recipeService) {
      _recipeService = recipeService ?? throw new ArgumentNullException(nameof(recipeService));
    }

    // whenever any input changes, reevaluate AddRecipeCommand.CanExecute
    partial void OnNewRecipeTitleChanged(string value)
      => AddRecipeCommand.NotifyCanExecuteChanged();
    partial void OnNewRecipeIngredientsChanged(string value)
      => AddRecipeCommand.NotifyCanExecuteChanged();
    partial void OnNewRecipeInstructionsChanged(string value)
      => AddRecipeCommand.NotifyCanExecuteChanged();

    // reevaluate DeleteRecipeCommand.CanExecute when the ID changes
    partial void OnDeleteRecipeIdChanged(string value)
      => DeleteRecipeCommand.NotifyCanExecuteChanged();

    // AddRecipeCommand binds to the “Add Recipe” button
    [RelayCommand(CanExecute = nameof(CanAddRecipe))]
    private async Task AddRecipeAsync() {
      var recipe = new Recipe {
        Id = "",
        Title = NewRecipeTitle.Trim(),
        Ingredients = [.. NewRecipeIngredients
                    .Split(',', StringSplitOptions.RemoveEmptyEntries)
                    .Select(s => s.Trim())],
        Instructions = NewRecipeInstructions.Trim(),
        Image = null
      };

      await _recipeService.AddRecipeAsync(recipe);

      // clear the form
      NewRecipeTitle = string.Empty;
      NewRecipeIngredients = string.Empty;
      NewRecipeInstructions = string.Empty;
    }

    private bool CanAddRecipe() =>
      !string.IsNullOrWhiteSpace(NewRecipeTitle)
      && !string.IsNullOrWhiteSpace(NewRecipeIngredients)
      && !string.IsNullOrWhiteSpace(NewRecipeInstructions);

    [RelayCommand(CanExecute = nameof(CanDeleteRecipe))]
    private async Task DeleteRecipeAsync() {
      await _recipeService.DeleteRecipeAsync(DeleteRecipeId.Trim());

      DeleteRecipeId = string.Empty;
    }

    private bool CanDeleteRecipe() =>
      !string.IsNullOrWhiteSpace(DeleteRecipeId);
  }
}
