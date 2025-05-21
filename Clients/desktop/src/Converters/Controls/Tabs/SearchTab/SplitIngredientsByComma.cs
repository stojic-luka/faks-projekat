using System.Collections.ObjectModel;
using System.Globalization;

namespace AugmentedCooking.src.Converters.Controls.Tabs.SearchTab {
    class SplitIngredientsByComma : IValueConverter {
        public object? Convert(object? value, Type targetType, object? parameter, CultureInfo c) {
            throw new NotImplementedException();
        }

        public object? ConvertBack(object? value, Type targetType, object? parameter, CultureInfo c) {
            if (value is not string)
                return Array.Empty<string>();

            if (!string.IsNullOrEmpty((string) value))
                return new ObservableCollection<string>(
                    ((string) value)
                        .Split(",")
                        .Select(s => s.Trim())
                        .Where(s => !string.IsNullOrEmpty(s))
                        .ToArray()
                );

            return Array.Empty<string>();
        }
    }
}
