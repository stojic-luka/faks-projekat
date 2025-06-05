using AugmentedCooking.src.ViewModels.Windows;

namespace AugmentedCooking.src.Views.Windows;

public partial class RecipeDetailsPage : ContentPage {
    public RecipeDetailsPage() {
        InitializeComponent();

        if (BindingContext is RecipeDetailsViewModel vm)
            vm.RequestClose += () => Application.Current!.CloseWindow(new Window(this));

        // this.Closing += (sender, e) => ((RecipeDetailsViewModel)this.DataContext).StopTcpServer();
    }
}
