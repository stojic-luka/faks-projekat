using DesktopClient.src.ViewModels.Controls;
using Microsoft.Extensions.DependencyInjection;
using System.Windows.Controls;

namespace DesktopClient.src.Views.Controls {
    /// <summary>
    /// Interaction logic for Sidebar.xaml
    /// </summary>
    public partial class Sidebar : UserControl {
        public Sidebar() {
            InitializeComponent();
            this.DataContext = App.ServiceProvider.GetRequiredService<SidebarViewModel>();
        }
    }
}
