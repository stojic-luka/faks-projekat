using System.Windows;
using DesktopClient.src.ViewModels;
using Microsoft.Extensions.DependencyInjection;

namespace DesktopClient.src.Views {
    public partial class MainWindow : Window {
        private readonly MainViewModel _mainViewModel;

        public MainWindow() {
            InitializeComponent();
            _mainViewModel = App.ServiceProvider.GetRequiredService<MainViewModel>();
            this.DataContext = _mainViewModel;

            this.Closing += MainWindow_Closing;
        }

        private void MainWindow_Closing(object? sender, System.ComponentModel.CancelEventArgs e) {
            foreach (Window window in Application.Current.Windows) {
                if (window != this) 
                    window.Close();
            }
        }
    }
}
