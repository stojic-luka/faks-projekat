using DesktopClient.src.ViewModels.Windows;
using System.Windows;

namespace DesktopClient.src.Views.Windows {
    /// <summary>
    /// Interaction logic for RecipeDetailsWindow.xaml
    /// </summary>
    public partial class RecipeDetailsWindow : Window {
        public RecipeDetailsWindow() {
            InitializeComponent();

            this.Closing += (sender, e) => ((RecipeDetailsViewModel) this.DataContext).StopTcpServer();
        }
    }
}
