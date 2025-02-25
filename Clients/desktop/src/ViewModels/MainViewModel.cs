using DesktopClient.src.Enums;
using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace DesktopClient.src.ViewModels {
    public class MainViewModel : INotifyPropertyChanged {
        private MainTabs _currentTab;
        public MainTabs CurrentTab {
            get => _currentTab;
            set {
                if (_currentTab != value) {
                    _currentTab = value;
                    OnPropertyChanged();
                }
            }
        }

        public event PropertyChangedEventHandler? PropertyChanged;
        protected void OnPropertyChanged([CallerMemberName] string propertyName = "") {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));  
        }
    }
}
