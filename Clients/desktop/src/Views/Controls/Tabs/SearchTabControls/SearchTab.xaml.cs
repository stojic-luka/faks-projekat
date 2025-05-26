using AugmentedCooking.src.ViewModels.Controls.Tabs.SearchTab;

namespace AugmentedCooking.src.Views.Controls.Tabs {
    public partial class SearchTab : ContentView {
        readonly SearchRecipeViewModel _searchRecipeViewModel;

        public SearchTab(SearchRecipeViewModel searchRecipeViewModel) {
            InitializeComponent();

            _searchRecipeViewModel = searchRecipeViewModel ?? throw new ArgumentNullException(nameof(searchRecipeViewModel));
            BindingContext = _searchRecipeViewModel;
        }

        public SearchTab() : this(App.Services.GetRequiredService<SearchRecipeViewModel>()) { }

        void OnTextChanged(object sender, TextChangedEventArgs e) {
            var entry = (Entry) sender;
            if (entry.Text is null)
                return;

            var filtered = new string([.. entry.Text.Where(c => char.IsLetterOrDigit(c) || c == ' ' || c == ',')]);

            if (filtered != entry.Text)
                entry.Text = filtered;
        }

        async void OnLoadMoreRecipes(object sender, EventArgs e) => await _searchRecipeViewModel.FetchRecipesAsync();
    }
}
