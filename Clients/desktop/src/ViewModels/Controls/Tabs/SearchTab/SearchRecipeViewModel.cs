using System.Collections.ObjectModel;
using System.Windows.Input;
using AugmentedCooking.src.Models.Recipe;
using AugmentedCooking.src.Services.RecipeServices;
using AugmentedCooking.src.ViewModels.Windows;
using AugmentedCooking.src.Views.Windows;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;

namespace AugmentedCooking.src.ViewModels.Controls.Tabs.SearchTab {
    public partial class SearchRecipeViewModel : ObservableObject {
        private int _page = 0;
        private const byte PAGE_LIMIT = 20;

        [ObservableProperty]
        private string _searchText = string.Empty;

        [ObservableProperty]
        private ObservableCollection<Recipe> _fetchedRecipes = [];

        public IRelayCommand<Recipe> SelectRecipeCommand { get; }
        public IAsyncRelayCommand FetchRecipesCommand { get; }

        private readonly IRecipeService _recipeService;

        public SearchRecipeViewModel(IRecipeService recipeService) {
            _recipeService = recipeService ?? throw new ArgumentNullException(nameof(recipeService));

            SelectRecipeCommand = new RelayCommand<Recipe>(OnItemClick);

            FetchRecipesCommand = new AsyncRelayCommand(
                FetchRecipesAsync,
                () => !_isFetching && !_isLastFetch && !string.IsNullOrWhiteSpace(SearchText)
            );
        }

        private ContentPage? _currentWindow = null;

        partial void OnSearchTextChanged(string value) {
            if (!string.IsNullOrWhiteSpace(value)) {
                _isFirstFetch = true;
                _isLastFetch = false;
            }
            else {
                _isFirstFetch = false;
            }
        }

        private void OnItemClick(object? parameter) {
            if (parameter != null && parameter is Recipe clickedItem) {
                // _currentWindow?.close();

                _currentWindow = new RecipeDetailsPage {
                    BindingContext = new RecipeDetailsViewModel(clickedItem),
                };
                // _currentWindow.Show();
            }
        }

        private bool _isFirstFetch = false;
        private bool _isFetching = false;
        private bool _isLastFetch = false;

        public async Task FetchRecipesAsync() {
            _isFetching = true;

            TaskCompletionSource<bool> clearingTask = new();
            if (_isFirstFetch) {
                _page = 0;
                await MainThread.InvokeOnMainThreadAsync(() => {
                    FetchedRecipes.Clear();
                    clearingTask.SetResult(true);
                });
            }
            else
                clearingTask.SetResult(true);

            Recipe[]? recipes = await _recipeService.GetRecipeByIngredientsAsync(
                _page,
                PAGE_LIMIT,
                [.. SearchText.Split(',')
                    .Select(i => i.Trim())
                    .Where(i => !string.IsNullOrWhiteSpace(i))
                ]
            );

            if (recipes?.Length == 0) {
                _isLastFetch = true;
                _isFetching = false;
                return;
            }

            await clearingTask.Task;

            await MainThread.InvokeOnMainThreadAsync(() => {
                if (recipes != null && recipes.Length > 0) {
                    _page++;
                    foreach (Recipe recipe in recipes)
                        FetchedRecipes.Add(recipe);
                }
            });

            _isFetching = false;
        }
    }
}
