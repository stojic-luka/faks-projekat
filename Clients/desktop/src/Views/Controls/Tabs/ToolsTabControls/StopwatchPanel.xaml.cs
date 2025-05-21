using AugmentedCooking.src.ViewModels.Controls.Tabs.ToolsTab;

namespace AugmentedCooking.src.Views.Controls.Tabs.ToolsTabControls {
    /// <summary>
    /// Interaction logic for StopwatchPanel.xaml
    /// </summary>
    public partial class StopwatchPanel : ContentView {
        private readonly StopwatchViewModel _stopwatchViewModel;

        public StopwatchPanel(StopwatchViewModel stopwatchViewModel) {
            InitializeComponent();

            _stopwatchViewModel = stopwatchViewModel ?? throw new ArgumentNullException(nameof(stopwatchViewModel));
            this.BindingContext = _stopwatchViewModel;
        }

        public StopwatchPanel() : this(App.Services.GetRequiredService<StopwatchViewModel>()) { }
    }
}
