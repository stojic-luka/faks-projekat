using DesktopClient.src.ViewModels.Controls.Tabs.SearchTab;
using Microsoft.Extensions.DependencyInjection;
using System.Windows.Controls;
using System.Windows.Input;

namespace DesktopClient.src.Views.Controls.Tabs {
    public partial class SearchTab : UserControl {
        private readonly SearchRecipeViewModel _searchRecipeViewModel;

        public SearchTab() {
            InitializeComponent();
            _searchRecipeViewModel = App.ServiceProvider.GetRequiredService<SearchRecipeViewModel>();
            this.DataContext = _searchRecipeViewModel;

            ingredientsTextbox.KeyDown += IngredientsTextbox_KeyDown;
            recipesScroll.ScrollChanged += ScrollViewer_ScrollChanged;
        }

        private async void IngredientsTextbox_KeyDown(object sender, KeyEventArgs e) {
            if (e.Key >= Key.D0 && e.Key <= Key.D9 ||
                e.Key >= Key.NumPad0 && e.Key <= Key.NumPad9 ||
                e.Key >= Key.A && e.Key <= Key.Z ||
                e.Key == Key.Space || e.Key == Key.OemComma) {
                e.Handled = false;
            } else if (e.Key == Key.Enter) {
                await _searchRecipeViewModel.FetchRecipesAsync(true);
                recipesScroll.ScrollToTop();
            } else {
                e.Handled = true;
            }
        }

        private async void ScrollViewer_ScrollChanged(object sender, ScrollChangedEventArgs e) {
            var scrollViewer = (ScrollViewer)sender;
            if (scrollViewer != null && scrollViewer.VerticalOffset >= scrollViewer.ScrollableHeight)
                await Task.Run(() => _searchRecipeViewModel.FetchRecipesAsync());
        }
    }
}
