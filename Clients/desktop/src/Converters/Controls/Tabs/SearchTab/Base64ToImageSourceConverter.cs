using System.Globalization;
using System.Windows.Data;
using System.Windows.Media.Imaging;

namespace DesktopClient.src.Converters.Controls.Tabs.SearchTab {
    class Base64ToImageSourceConverter : IValueConverter {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture) {
            if (value == null || value.ToString() == string.Empty)
                return null;

            try {
                byte[] imageBytes = System.Convert.FromBase64String((string)value);
                var image = new BitmapImage();
                using (var stream = new System.IO.MemoryStream(imageBytes)) {
                    image.BeginInit();
                    image.StreamSource = stream;
                    image.CacheOption = BitmapCacheOption.OnLoad;
                    image.EndInit();
                }
                return image;
            } catch {
                return null;
            }
        }
        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture) {
            throw new NotImplementedException();
        }
    }
}
