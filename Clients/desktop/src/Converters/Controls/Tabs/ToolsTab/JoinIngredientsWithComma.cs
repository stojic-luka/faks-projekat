using System.Globalization;
using System.Windows.Data;

namespace DesktopClient.src.Converters.Controls.Tabs.ToolsTab {
    class JoinIngredientsWithComma : IValueConverter {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture) {
            return value is string[] v ? string.Join(",\n", v) : "";
        }
        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture) {
            throw new NotImplementedException();
        }
    }
}
