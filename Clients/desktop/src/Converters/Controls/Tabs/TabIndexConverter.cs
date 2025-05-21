using System.Globalization;
using AugmentedCooking.src.Enums;

namespace AugmentedCooking.src.Converters.Controls.Tabs {
  class TabIndexConverter : IValueConverter {
    public object? Convert(object? value, Type targetType, object? parameter, CultureInfo c) {
      return value is MainTabs tab ? (int) tab : 0;
    }

    public object? ConvertBack(object? value, Type targetType, object? parameter, CultureInfo c) {
      return value is int index ? (MainTabs) index : MainTabs.Home;
    }
  }
}
