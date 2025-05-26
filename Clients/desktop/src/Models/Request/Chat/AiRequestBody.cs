using System.Text.Json.Serialization;

namespace AugmentedCooking.src.Models.Request.Chat {
    public class AiRequestBody {
        [JsonPropertyName("request_id")]
        public required string RequestId { get; set; }

        [JsonPropertyName("user_id")]
        public required string UserId { get; set; }

        [JsonPropertyName("model")]
        public required string Model { get; set; }

        [JsonPropertyName("prompt")]
        public required string Prompt { get; set; }

        [JsonPropertyName("streamed")]
        public bool? Streamed { get; set; }

        [JsonPropertyName("imageb64")]
        public string? ImageB64 { get; set; }
    }
}
