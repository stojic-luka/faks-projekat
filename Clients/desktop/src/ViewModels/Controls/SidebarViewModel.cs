using System.Windows.Input;
using AugmentedCooking.src.Enums;
using AugmentedCooking.src.Helpers;

namespace AugmentedCooking.src.ViewModels.Controls {
    public class SidebarViewModel : BaseViewModel {
        public ICommand ChangeTabCommand { get; }

        private readonly MainViewModel _mainViewModel;

        public SidebarViewModel(MainViewModel mainViewModel) {
            _mainViewModel = mainViewModel ?? throw new ArgumentNullException(nameof(mainViewModel));

            ChangeTabCommand = new RelayCommand(
                tab => _mainViewModel.CurrentTab = (MainTabs) tab,
                tab => tab is MainTabs
            );
        }
    }
}
