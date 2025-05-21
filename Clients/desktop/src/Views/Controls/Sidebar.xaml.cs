using AugmentedCooking.src.ViewModels.Controls;

namespace AugmentedCooking.src.Views.Controls {
    public partial class Sidebar : ContentView {
        private readonly SidebarViewModel _sidebarViewModel;

        public Sidebar(SidebarViewModel sidebarViewModel) {
            InitializeComponent();

            _sidebarViewModel = sidebarViewModel ?? throw new ArgumentNullException(nameof(sidebarViewModel));
            this.BindingContext = _sidebarViewModel;
        }

        public Sidebar() : this(App.Services.GetRequiredService<SidebarViewModel>()) { }
    }
}
