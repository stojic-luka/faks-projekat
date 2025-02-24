using DesktopClient.src.ViewModels.Controls.Tabs.ToolsTab;
using Microsoft.Extensions.DependencyInjection;
using System.Windows.Controls;

namespace DesktopClient.src.Views.Controls.Tabs.ToolsTabControls {
    public partial class RandomRecipePanel : UserControl {
        private readonly RandomRecipeViewModel _randomRecipeViewModel;

        public RandomRecipePanel() {
            InitializeComponent();
            _randomRecipeViewModel = App.ServiceProvider.GetRequiredService<RandomRecipeViewModel>();
            this.DataContext = _randomRecipeViewModel;
        }
    }
}
