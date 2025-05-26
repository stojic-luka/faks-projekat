using AugmentedCooking.src.ViewModels.Controls.Tabs.LoginTab;

namespace AugmentedCooking.src.Views.Controls.Tabs {
    public partial class LoginTab : ContentView {
        readonly LoginTabViewModel _loginTabViewModel;

        public LoginTab(LoginTabViewModel loginTabViewModel) {
            InitializeComponent();

            _loginTabViewModel = loginTabViewModel ?? throw new ArgumentNullException(nameof(loginTabViewModel));
            BindingContext = _loginTabViewModel;
        }

        public LoginTab() : this(App.Services.GetRequiredService<LoginTabViewModel>()) { }
    }
}
