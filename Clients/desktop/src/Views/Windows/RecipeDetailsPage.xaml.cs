namespace AugmentedCooking.src.Views.Windows;

public partial class RecipeDetailsPage : ContentPage {
    public RecipeDetailsPage() {
        InitializeComponent();

        // this.Closing += (sender, e) => ((RecipeDetailsViewModel)this.DataContext).StopTcpServer();
    }
}
