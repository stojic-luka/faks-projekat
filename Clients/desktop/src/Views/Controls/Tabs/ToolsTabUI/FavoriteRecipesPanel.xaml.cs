using DesktopClient.src.ViewModels.Controls.Tabs.ToolsTab;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace DesktopClient.src.Views.Controls.Tabs.ToolsTabControls {
    /// <summary>
    /// Interaction logic for FavoriteRecipesPanel.xaml
    /// </summary>
    public partial class FavoriteRecipesPanel : UserControl {
        private readonly FavoriteRecipesViewModel _favoriteRecipesViewModel;

        public FavoriteRecipesPanel() {
            InitializeComponent();
            _favoriteRecipesViewModel = App.ServiceProvider.GetRequiredService<FavoriteRecipesViewModel>();

            this.DataContext = _favoriteRecipesViewModel;
        }
    }
}
