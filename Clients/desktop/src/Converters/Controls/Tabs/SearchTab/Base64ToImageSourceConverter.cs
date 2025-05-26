using System.Globalization;
using AugmentedCooking.src.Models.Recipe;

namespace AugmentedCooking.src.Converters.Controls.Tabs.SearchTab {
    class Base64ToImageSourceConverter : IValueConverter {
        public object? Convert(object? value, Type targetType, object? parameter, CultureInfo c) {
            if (value is not string base64 || string.IsNullOrWhiteSpace(base64))
                return null;

            try {
                byte[] bytes = System.Convert.FromBase64String(base64);
                return ImageSource.FromStream(() => new MemoryStream(bytes));
            }
            catch {
                return null;
            }
        }

        public object? ConvertBack(object? value, Type targetType, object? parameter, CultureInfo c) {
            throw new NotImplementedException();
        }
    }
}
