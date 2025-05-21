using System.Globalization;

namespace AugmentedCooking.src.Converters.Controls.Tabs.ToolsTab {
    class IsRunningButtonStringConverter : IValueConverter {
        public object? Convert(object? value, Type targetType, object? parameter, CultureInfo c) {
            if (value is bool b)
                return b ? "Stop" : "Start";
            return "Start";
        }

        public object? ConvertBack(object? value, Type targetType, object? parameter, CultureInfo c) {
            throw new NotImplementedException();
        }
    }
}
