using System.Windows.Input;

namespace DesktopClient.src.Helpers {
    public class RelayCommand(Action<object> ExcuteMethod, Predicate<object> CanExcuteMethod) : ICommand {
#pragma warning disable CS0067
        public event EventHandler? CanExecuteChanged;

        private Action<object> _execute { get; set; } = ExcuteMethod;
        private Predicate<object> _canExcute { get; set; } = CanExcuteMethod;

        public bool CanExecute(object? parameter) { return _canExcute(parameter!); }
        public void Execute(object? parameter) { _execute(parameter!); }
    }
}