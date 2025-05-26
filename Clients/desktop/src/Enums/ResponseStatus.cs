using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System.Runtime.Serialization;

namespace AugmentedCooking.src.Enums {
    [JsonConverter(typeof(StringEnumConverter))]
    public enum ResponseStatus {
        [EnumMember(Value = "success")]
        Success,

        [EnumMember(Value = "error")]
        Error
    }
}
