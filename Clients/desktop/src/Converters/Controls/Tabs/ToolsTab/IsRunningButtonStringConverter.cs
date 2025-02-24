using System.Globalization;
using System.Windows.Data;

namespace DesktopClient.src.Converters.Controls.Tabs.ToolsTab {
    class IsRunningButtonStringConverter : IValueConverter {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture) {
            return (bool) value ? "Stop" : "Start";
        }
        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture) {
            throw new NotImplementedException();
        }
    }
}
