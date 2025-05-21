using System.Globalization;

namespace AugmentedCooking.src.Converters.Controls.Tabs.ToolsTab {
    class JoinIngredientsWithComma : IValueConverter {
        public object? Convert(object? value, Type targetType, object? parameter, CultureInfo c) {
            return value is string[] v ? string.Join(",\n", v) : "";
        }

        public object? ConvertBack(object? value, Type targetType, object? parameter, CultureInfo c) {
            throw new NotImplementedException();
        }
    }
}
