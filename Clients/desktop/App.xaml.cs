using System.Runtime.ExceptionServices;
using AugmentedCooking.src.Services.AuthServices;
using AugmentedCooking.src.Services.UserServices;
using AugmentedCooking.src.Views;

namespace AugmentedCooking;

public partial class App : Application {
    public static IServiceProvider Services { get; private set; } = default!;

    public App(IServiceProvider services) {
        InitializeComponent();

        // AppDomain.CurrentDomain.FirstChanceException += CurrentDomain_FirstChanceException;
        Services = services ?? throw new ArgumentNullException(nameof(services));

        MainPage = new AppShell();
    }

    private void CurrentDomain_FirstChanceException(object sender, FirstChanceExceptionEventArgs e) {
        System.Diagnostics.Debug.WriteLine($"***** Handling Unhandled Exception *****: {e.Exception.Message}");
    }
}
