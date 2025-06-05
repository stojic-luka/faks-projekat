using AugmentedCooking.src.Enums;
using AugmentedCooking.src.Services.UserServices;
using CommunityToolkit.Mvvm.ComponentModel;

namespace AugmentedCooking.src.ViewModels {
    public partial class MainViewModel : ObservableObject {
        readonly IUserSession _userSession;

        [ObservableProperty]
        private MainTabs currentTab = MainTabs.Login;

        public MainViewModel(IUserSession userSession) {
            _userSession = userSession ?? throw new ArgumentNullException(nameof(userSession));

            userSession.SignedIn += user => MainThread.BeginInvokeOnMainThread(() => CurrentTab = MainTabs.Home);
            userSession.SignedOut += () => MainThread.BeginInvokeOnMainThread(() => CurrentTab = MainTabs.Login);
        }
    }
}
