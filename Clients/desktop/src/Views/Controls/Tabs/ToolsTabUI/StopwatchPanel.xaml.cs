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
    /// Interaction logic for StopwatchPanel.xaml
    /// </summary>
    public partial class StopwatchPanel : UserControl {
        private readonly StopwatchViewModel _stopwatchViewModel;

        public StopwatchPanel() {
            InitializeComponent();
            _stopwatchViewModel = App.ServiceProvider.GetRequiredService<StopwatchViewModel>();

            this.DataContext = _stopwatchViewModel;
        }
    }
}
