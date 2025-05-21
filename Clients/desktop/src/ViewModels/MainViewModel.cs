using AugmentedCooking.src.Enums;
using AugmentedCooking.src.Helpers;

namespace AugmentedCooking.src.ViewModels {
    public class MainViewModel : BaseViewModel {
        private MainTabs _currentTab = MainTabs.Home;
        public MainTabs CurrentTab {
            get => _currentTab;
            set {
                if (_currentTab != value) {
                    _currentTab = value;
                    OnPropertyChanged();
                }
            }
        }
    }
}
