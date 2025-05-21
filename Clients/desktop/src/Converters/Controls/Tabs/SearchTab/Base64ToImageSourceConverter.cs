using System.Globalization;

namespace AugmentedCooking.src.Converters.Controls.Tabs.SearchTab {
    class Base64ToImageSourceConverter : IValueConverter {
        public object? Convert(object? value, Type targetType, object? parameter, CultureInfo c) {
            if (value == null || value.ToString() == string.Empty)
                return null;

            try {
                byte[] imageBytes = System.Convert.FromBase64String((string) value);
                var image = new Image {
                    Source = ImageSource.FromStream(() => new MemoryStream(imageBytes)),
                };
                return image;
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
