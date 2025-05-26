using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;
using AugmentedCooking.src.Helpers;
using AugmentedCooking.src.Models.Recipe;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;

namespace AugmentedCooking.src.ViewModels.Controls.Tabs.ToolsTab {
    public partial class FavoriteRecipesViewModel : ObservableObject {
        public ObservableCollection<Recipe> FavoriteRecipes { get; private set; } = [];

        public ICommand AddRecipe { get; }
        public ICommand RemoveRecipe { get; }

        public FavoriteRecipesViewModel() {
            AddRecipe = new RelayCommand<Recipe>(
                obj => Add(obj!),
                obj => obj != null && !FavoriteRecipes.Any(r => r.Id == obj.Id)
            );

            RemoveRecipe = new RelayCommand<Recipe>(
                obj => Remove(obj!),
                obj => obj != null && FavoriteRecipes.Any(r => r.Id == obj.Id)
            );
        }

        private void Add(Recipe recipe) {
            FavoriteRecipes.Add(recipe);
            OnPropertyChanged(nameof(FavoriteRecipes));
        }

        private void Remove(Recipe recipe) {
            System.Diagnostics.Trace.WriteLine("remove");
            FavoriteRecipes.Remove(recipe);
            OnPropertyChanged(nameof(FavoriteRecipes));
        }
    }
}
