using System.Text.Json.Serialization;
using AugmentedCooking.src.Models.Response.Chat.Serialization;

namespace AugmentedCooking.src.Models.Response.Chat.Streamed;

[JsonConverter(typeof(AiChatStreamedResponseConverter))]
public abstract class AiChatStreamedResponse : AiChatResponse {
    [JsonPropertyName("sequence")]
    public required int Sequence { get; set; }

    [JsonPropertyName("last_chunk")]
    public required bool LastChunk { get; set; }
}

public class AiChatStreamedResponse<T> : AiChatStreamedResponse {
    [JsonPropertyName("content")]
    public required T Content { get; set; }
}
