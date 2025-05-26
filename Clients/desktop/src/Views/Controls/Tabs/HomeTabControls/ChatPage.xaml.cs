using AugmentedCooking.src.ViewModels.Controls.Tabs.HomeTab;

namespace AugmentedCooking.src.Views.Controls.Tabs;

public partial class ChatPage : ContentView {
    readonly ChatPageViewModel _chatPageViewModel;

    public ChatPage(ChatPageViewModel chatPageViewModel) {
        InitializeComponent();

        _chatPageViewModel = chatPageViewModel ?? throw new ArgumentNullException(nameof(chatPageViewModel));
        BindingContext = _chatPageViewModel;

        _chatPageViewModel.ScrollToEndRequested += idx => MessagesView.ScrollTo(idx, animate: true);
    }

    public ChatPage() : this(App.Services.GetRequiredService<ChatPageViewModel>()) { }
}
