using AugmentedCooking.src.Enums;
using Newtonsoft.Json;

namespace AugmentedCooking.src.Models.Response;

public class ApiResponse<T> {
    [JsonProperty("status")]
    public ResponseStatus Status { get; set; }

    [JsonProperty("meta")]
    public MetaData Meta { get; set; }

    [JsonProperty("data")]
    public T? Data { get; set; }

    [JsonProperty("error")]
    public Error? ApiError { get; set; }

    public bool IsSuccess => Status == ResponseStatus.Success && Data != null;

    public ApiResponse() {
        Status = default;
        Data = default;
        ApiError = null;
        Meta = new MetaData {
            Timestamp = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()
        };
    }

    public ApiResponse(T data) {
        Data = data ?? throw new ArgumentNullException(nameof(data));
        ApiError = null;
        Meta = new MetaData {
            Timestamp = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()
        };
    }

    public ApiResponse(Error error) {
        Data = default;
        ApiError = error ?? throw new ArgumentNullException(nameof(error));
        Meta = new MetaData {
            Timestamp = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()
        };
    }
}

public class MetaData {
    public required long Timestamp { get; set; }
    // public required string ApiVersion { get; set; }
}

public class Error {
    public required string Code { get; set; }
    public required string Message { get; set; }
}
