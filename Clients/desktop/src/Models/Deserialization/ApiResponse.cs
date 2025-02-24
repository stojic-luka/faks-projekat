namespace DesktopClient.src.Models.Deserialization {
    public class ApiResponse<T> {
        public bool Success { get; set; }
        public MetaData? Meta { get; set; }
        public T? Data { get; set; }
        public ApiError? Error { get; set; }
    }

    public class MetaData {
        public long Timestamp { get; set; }
        public required string ApiVersion { get; set; }
    }

    public class ApiError {
        public required string Code { get; set; }
        public required string Message { get; set; }
    }
}
