using DesktopClient.src.Enums;
using System.Globalization;
using System.Windows.Data;

namespace DesktopClient.src.Converters.Controls.Tabs {
    class TabIndexConverter : IValueConverter {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture) {
            return value is MainTabs tab ? (int) tab : 0;
        }
        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture) {
            return value is int index ? (MainTabs) index : MainTabs.Home;
        }
    }
}
