using AugmentedCooking.src.Models.Chat.Enums;

namespace AugmentedCooking.src.Models.Chat;

public class ChatMessage {
    public required string Id { get; set; }
    public required string UserId { get; set; }
    public required UserRole Role { get; set; }
    public required MessageType Type { get; set; }
    public required ChatContent Content { get; set; }
    public required string Model { get; set; }
    public required MessageStatus Status { get; set; }
    public required long Timestamp { get; set; }
}
