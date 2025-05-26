namespace AugmentedCooking.src.Models.Chat;

public class ChatContent {
    public required string Text { get; set; }
    public List<ChatImageContent> Images { get; set; } = [];
}

public class ChatImageContent {
    public required string Data { get; set; }
    public required string Format { get; set; }
    public required int Width { get; set; }
    public required int Height { get; set; }
    public required string Description { get; set; }
}
