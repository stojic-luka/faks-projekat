using AugmentedCooking.src.Models;
using DesktopClient.src.Helpers;
using DesktopClient.src.Services;
using DesktopClient.src.ViewModels.Windows;
using DesktopClient.src.Views.Windows;
using Microsoft.Extensions.DependencyInjection;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows;
using System.Windows.Input;

namespace DesktopClient.src.ViewModels.Controls.Tabs.SearchTab {
    class SearchRecipeViewModel : INotifyPropertyChanged {
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
        public SearchRecipeViewModel() {
            _recipeService = App.ServiceProvider.GetRequiredService<RecipeService>();

            ItemClickCommand = new RelayCommand(
                OnItemClick,
                obj => true
            );
        }

        private Window? _currentWindow = null;
        private void OnItemClick(object parameter) {
            if (parameter is Recipe clickedItem) {
                _currentWindow?.Close();

                _currentWindow = new RecipeDetailsWindow {
                    DataContext = new RecipeDetailsViewModel(clickedItem)
                };
                _currentWindow.Show();
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
                Application.Current.Dispatcher.Invoke(() => {
                    FetchedRecipes.Clear();
                    clearingTask.SetResult(true);
                });
            } else {
                clearingTask.SetResult(true);
            }

            Recipe[]? recipes = await _recipeService.GetRecipeByIngredientsAsync([.. _searchIngredients], _page, PAGE_LIMIT);

            if (recipes?.Length == 0) {
                _isLastFetch = true;
                _isFetching = false;
                return;
            }

            await clearingTask.Task;

            Application.Current.Dispatcher.Invoke(() => {
                if (recipes != null && recipes.Length > 0) {
                    _page++;
                    foreach (Recipe recipe in recipes) {
                        FetchedRecipes.Add(recipe);
                    }
                }
            });

            _isFetching = false;
        }

        public event PropertyChangedEventHandler? PropertyChanged;
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = "") {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
