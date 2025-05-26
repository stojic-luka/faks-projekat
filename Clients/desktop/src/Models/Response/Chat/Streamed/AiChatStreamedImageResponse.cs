using System.Text.Json.Serialization;

namespace AugmentedCooking.src.Models.Response.Chat.Streamed;

public class AiChatStreamedResponseImageContent {
    [JsonPropertyName("data")]
    public required string Data { get; set; }

    [JsonPropertyName("format")]
    public required string Format { get; set; }

    [JsonPropertyName("width")]
    public required int Width { get; set; }

    [JsonPropertyName("height")]
    public required int Height { get; set; }

    [JsonPropertyName("description")]
    public required string Description { get; set; }

}

public class AiChatStreamedImageResponse : AiChatStreamedResponse<AiChatStreamedResponseImageContent> {
}
