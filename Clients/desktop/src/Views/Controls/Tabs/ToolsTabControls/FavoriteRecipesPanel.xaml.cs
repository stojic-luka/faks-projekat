using AugmentedCooking.src.ViewModels.Controls.Tabs.ToolsTab;

namespace AugmentedCooking.src.Views.Controls.Tabs.ToolsTabControls {
    /// <summary>
    /// Interaction logic for FavoriteRecipesPanel.xaml
    /// </summary>
    public partial class FavoriteRecipesPanel : ContentView {
        private readonly FavoriteRecipesViewModel _favoriteRecipesViewModel;

        public FavoriteRecipesPanel(FavoriteRecipesViewModel favoriteRecipesViewModel) {
            InitializeComponent();

            _favoriteRecipesViewModel = favoriteRecipesViewModel ?? throw new ArgumentNullException(nameof(favoriteRecipesViewModel));
            this.BindingContext = favoriteRecipesViewModel;
        }

        public FavoriteRecipesPanel() : this(App.Services.GetRequiredService<FavoriteRecipesViewModel>()) { }
    }
}
