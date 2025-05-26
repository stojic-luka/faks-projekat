using System.Text.Json.Serialization;

namespace AugmentedCooking.src.Models.Response.Chat.Streamed;

public class AiChatStreamedResponseMetadataContent {
    [JsonPropertyName("user_id")]
    public required string UserId { get; set; }

    [JsonPropertyName("model")]
    public required string Model { get; set; }

    [JsonPropertyName("role")]
    public required string Role { get; set; }

    [JsonPropertyName("timestamp")]
    public required long Timestamp { get; set; }

}

public class AiChatStreamedMetadataResponse : AiChatStreamedResponse<AiChatStreamedResponseMetadataContent> {
}
