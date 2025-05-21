using AugmentedCooking.src.ViewModels.Controls.Tabs.ToolsTab;

namespace AugmentedCooking.src.Views.Controls.Tabs.ToolsTabControls {
    public partial class RandomRecipePanel : ContentView {
        private readonly RandomRecipeViewModel _randomRecipeViewModel;

        public RandomRecipePanel(RandomRecipeViewModel randomRecipeViewModel) {
            InitializeComponent();

            _randomRecipeViewModel = randomRecipeViewModel ?? throw new ArgumentNullException(nameof(randomRecipeViewModel));
            this.BindingContext = _randomRecipeViewModel;
        }

        public RandomRecipePanel() : this(App.Services.GetRequiredService<RandomRecipeViewModel>()) { }
    }
}
