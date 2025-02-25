using DesktopClient.src.Enums;
using DesktopClient.src.Helpers;
using Microsoft.Extensions.DependencyInjection;
using System.Windows.Input;

namespace DesktopClient.src.ViewModels.Controls {
    public class SidebarViewModel {
        public ICommand ChangeTabCommand { get; }

        private readonly MainViewModel _mainViewModel;
        public SidebarViewModel() {
            _mainViewModel = App.ServiceProvider.GetRequiredService<MainViewModel>();
            ChangeTabCommand = new RelayCommand(
                tab => _mainViewModel.CurrentTab = (MainTabs) tab,
                tab => tab is MainTabs
            );
        }
    }
}
