using AugmentedCooking.src.ViewModels.Controls.Tabs.SubmitTab;

namespace AugmentedCooking.src.Views.Controls.Tabs {
    /// <summary>
    /// Interaction logic for SubmitTab.xaml
    /// </summary>
    public partial class SubmitTab : ContentView {
        readonly SubmitRecipeViewModel _submitRecipeViewModel;

        public SubmitTab(SubmitRecipeViewModel submitRecipeViewModel) {
            InitializeComponent();

            _submitRecipeViewModel = submitRecipeViewModel ?? throw new ArgumentNullException(nameof(submitRecipeViewModel));
            BindingContext = _submitRecipeViewModel;
        }

        public SubmitTab() : this(App.Services.GetRequiredService<SubmitRecipeViewModel>()) { }
    }
}
