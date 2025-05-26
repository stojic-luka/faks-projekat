using System.Text.Json;
using System.Text.Json.Serialization;
using AugmentedCooking.src.Models.Chat.Enums;
using AugmentedCooking.src.Models.Response.Chat.Streamed;

namespace AugmentedCooking.src.Models.Response.Chat.Serialization;

public class AiChatStreamedResponseConverter : JsonConverter<AiChatStreamedResponse> {
    public override AiChatStreamedResponse Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options) {
        using var doc = JsonDocument.ParseValue(ref reader);
        var root = doc.RootElement;

        // var id = root.GetProperty("request_id").GetString();
        Enum.TryParse<MessageType>(root.GetProperty("type").GetString()!, ignoreCase: true, out var parsedType);
        // var sequence = root.GetProperty("sequence").GetInt32();
        // var lastChunk = root.GetProperty("last_chunk").GetBoolean();

        AiChatStreamedResponse result = parsedType switch {
            MessageType.STREAMED_TEXT => JsonSerializer.Deserialize<AiChatStreamedTextResponse>(root.GetRawText(), options)!,
            MessageType.STREAMED_IMAGE => JsonSerializer.Deserialize<AiChatStreamedImageResponse>(root.GetRawText(), options)!,
            MessageType.STREAMED_METADATA => JsonSerializer.Deserialize<AiChatStreamedMetadataResponse>(root.GetRawText(), options)!,
            _ => throw new JsonException($"Unexpected streamed response type: {parsedType}")
        };

        result.Id = root.GetProperty("request_id").GetString()!;
        result.Type = parsedType;
        result.Sequence = root.GetProperty("sequence").GetInt32();
        result.LastChunk = root.GetProperty("last_chunk").GetBoolean();

        // result.Id = id;
        // result.Type = parsedType;
        // result.Sequence = sequence;
        // result.LastChunk = lastChunk;

        return result;
    }

    public override void Write(Utf8JsonWriter writer, AiChatStreamedResponse value, JsonSerializerOptions options) {
        JsonSerializer.Serialize(writer, (object) value, value.GetType(), options);
    }
}
