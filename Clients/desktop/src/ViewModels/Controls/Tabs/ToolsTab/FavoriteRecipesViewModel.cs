using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;
using AugmentedCooking.src.Helpers;
using AugmentedCooking.src.Models;

namespace AugmentedCooking.src.ViewModels.Controls.Tabs.ToolsTab
{
    public class FavoriteRecipesViewModel : BaseViewModel
    {
        public ObservableCollection<Recipe> FavoriteRecipes { get; private set; } = [];

        public ICommand AddRecipe { get; }
        public ICommand RemoveRecipe { get; }

        public FavoriteRecipesViewModel()
        {
            AddRecipe = new RelayCommand(
                obj => Add((Recipe)obj),
                obj => obj != null && !FavoriteRecipes.Any(r => r.Id == ((Recipe)obj).Id)
            );

            RemoveRecipe = new RelayCommand(
                obj => Remove((Recipe)obj),
                obj => obj != null && FavoriteRecipes.Any(r => r.Id == ((Recipe)obj).Id)
            );
        }

        private void Add(Recipe recipe)
        {
            FavoriteRecipes.Add(recipe);
            OnPropertyChanged(nameof(FavoriteRecipes));
        }

        private void Remove(Recipe recipe)
        {
            System.Diagnostics.Trace.WriteLine("remove");
            FavoriteRecipes.Remove(recipe);
            OnPropertyChanged(nameof(FavoriteRecipes));
        }
    }
}
