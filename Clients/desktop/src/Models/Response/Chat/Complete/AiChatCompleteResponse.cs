using System.Text.Json.Serialization;

namespace AugmentedCooking.src.Models.Response.Chat.Complete;

public class AiChatCompleteResponse : AiChatResponse {
    [JsonPropertyName("user_id")]
    public required string UserId { get; set; }

    [JsonPropertyName("role")]
    public required string Role { get; set; }

    [JsonPropertyName("model")]
    public required string Model { get; set; }

    [JsonPropertyName("timestamp")]
    public required long Timestamp { get; set; }

    [JsonPropertyName("content")]
    public required AiChatResponseMessage Content { get; set; }
}

public class AiChatResponseMessage {
    [JsonPropertyName("text")]
    public required string Text { get; set; }

    [JsonPropertyName("images")]
    public required IList<AiChatResponseMessageImage> Images { get; set; }
}

public class AiChatResponseMessageImage {
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
