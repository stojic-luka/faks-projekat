using System.Text.Json;
using AugmentedCooking.src.Constants;
using AugmentedCooking.src.Managers;
using AugmentedCooking.src.Models.Response.Auth;
using AugmentedCooking.src.Models.User;
using AugmentedCooking.src.Services.UserServices;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;

namespace AugmentedCooking.src.ViewModels.Controls.Tabs.LoginTab {
    public partial class LoginTabViewModel : ObservableObject {
        private readonly IUserSession _userSession;

        // TODO?: remove this later and use authManager
        private readonly INetworkManager _networkManager;

        [ObservableProperty]
        private string _username = string.Empty;

        [ObservableProperty]
        private string _password = string.Empty;

        [ObservableProperty]
        private bool _isLoginFailed;

        public IAsyncRelayCommand SignInCommand { get; }

        public LoginTabViewModel(IUserSession userSession, INetworkManager networkManager) {
            _networkManager = networkManager;
            _userSession = userSession ?? throw new ArgumentNullException(nameof(userSession));

            SignInCommand = new AsyncRelayCommand(
              SignInAsync,
              CanSignIn
            );

            PropertyChanged += (_, e) => {
                if (e.PropertyName is nameof(Username) or nameof(Password))
                    SignInCommand.NotifyCanExecuteChanged();
            };
        }

        private bool CanSignIn() => !string.IsNullOrWhiteSpace(Username) && !string.IsNullOrWhiteSpace(Password);

        private async Task SignInAsync() {
            var resp = await _networkManager.PostAsync<UserTokenResponse>(
                ApiRoutes.Auth.Login,
                JsonSerializer.Serialize(new {
                    username = Username,
                    password = Password
                })
            );

            if (!resp.IsSuccess) {
                IsLoginFailed = true;
                return;
            }

            var user = resp.Data!.User;
            user.AccessToken = resp.Data.Token;
            _userSession.SetCurrentUser(user);
        }
    }
}
