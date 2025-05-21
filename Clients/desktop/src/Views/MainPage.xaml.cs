using AugmentedCooking.src.ViewModels;

namespace AugmentedCooking.src.Views;

public partial class MainPage : ContentPage {
    private readonly MainViewModel _mainViewModel;

    public MainPage(MainViewModel mainViewModel) {
        InitializeComponent();

        _mainViewModel = mainViewModel ?? throw new ArgumentNullException(nameof(mainViewModel));
        this.BindingContext = _mainViewModel;

        // this.Closing += MainWindow_Closing;
    }

    // private void MainWindow_Closing(object? sender, System.ComponentModel.CancelEventArgs e)
    // {
    //     foreach (Window window in Application.Current.Windows)
    //     {
    //         if (window != this)
    //             window.Close();
    //     }
    // }
}
