using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;
using AugmentedCooking.src.Helpers;
using AugmentedCooking.src.Models;
using AugmentedCooking.src.Services;
using AugmentedCooking.src.ViewModels.Windows;
using AugmentedCooking.src.Views.Windows;

namespace AugmentedCooking.src.ViewModels.Controls.Tabs.SearchTab {
    public class SearchRecipeViewModel : BaseViewModel {
        private int _page = 0;
        private const byte PAGE_LIMIT = 20;
        private bool _isLastFetch = false;

        private ObservableCollection<string> _searchIngredients = [];
        public ObservableCollection<string> SearchIngredients {
            get => _searchIngredients;
            set {
                if (!_searchIngredients.SequenceEqual(value)) {
                    _searchIngredients = value;
                    _page = 0;
                    _isLastFetch = false;
                }
            }
        }

        private ObservableCollection<Recipe> _fetchedRecipes = [];
        public ObservableCollection<Recipe> FetchedRecipes {
            get => _fetchedRecipes;
            private set {
                _fetchedRecipes = value;
                OnPropertyChanged();
            }
        }

        public ICommand ItemClickCommand { get; }

        private readonly RecipeService _recipeService;

        public SearchRecipeViewModel(RecipeService recipeService) {
            _recipeService = recipeService ?? throw new ArgumentNullException(nameof(recipeService));

            ItemClickCommand = new RelayCommand(OnItemClick, obj => true);
        }

        private ContentPage? _currentWindow = null;

        private void OnItemClick(object parameter) {
            if (parameter is Recipe clickedItem) {
                // _currentWindow?.close();

                _currentWindow = new RecipeDetailsPage {
                    BindingContext = new RecipeDetailsViewModel(clickedItem),
                };
                // _currentWindow.Show();
            }
        }

        private bool _isFetching = false;

        public async Task FetchRecipesAsync(bool isFirstFetch = false) {
            if (_searchIngredients.Count == 0 || _isLastFetch || _isFetching)
                return;

            _isFetching = true;

            TaskCompletionSource<bool> clearingTask = new();
            if (isFirstFetch) {
                _page = 0;
                await MainThread.InvokeOnMainThreadAsync(() => {
                    FetchedRecipes.Clear();
                    clearingTask.SetResult(true);
                });
            }
            else
                clearingTask.SetResult(true);

            Recipe[]? recipes = await _recipeService.GetRecipeByIngredientsAsync(
                [.. _searchIngredients],
                _page,
                PAGE_LIMIT
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
