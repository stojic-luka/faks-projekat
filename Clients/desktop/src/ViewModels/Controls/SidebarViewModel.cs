using System.Windows.Input;
using AugmentedCooking.src.Enums;
using AugmentedCooking.src.Helpers;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;

namespace AugmentedCooking.src.ViewModels.Controls {
    public partial class SidebarViewModel : ObservableObject {
        public RelayCommand<MainTabs> ChangeTabCommand { get; }

        private readonly MainViewModel _mainViewModel;

        public SidebarViewModel(MainViewModel mainViewModel) {
            _mainViewModel = mainViewModel ?? throw new ArgumentNullException(nameof(mainViewModel));

            ChangeTabCommand = new RelayCommand<MainTabs>(
                tab => _mainViewModel.CurrentTab = tab
            );
        }
    }
}
