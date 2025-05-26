using System.Text.Json.Serialization;
using AugmentedCooking.src.Models.Chat.Enums;

namespace AugmentedCooking.src.Models.Response.Chat;

public abstract class AiChatResponse {
    [JsonPropertyName("request_id")]
    public required string Id { get; set; }
    [JsonPropertyName("type")]
    public required MessageType Type { get; set; }
}
