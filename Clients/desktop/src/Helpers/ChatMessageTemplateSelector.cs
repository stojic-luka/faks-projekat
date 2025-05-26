using AugmentedCooking.src.Models.Chat;
using AugmentedCooking.src.Models.Chat.Enums;

namespace AugmentedCooking.src.Helpers;

public class ChatMessageTemplateSelector : DataTemplateSelector {
    public required DataTemplate UserTemplate { get; set; }
    public required DataTemplate BotTemplate { get; set; }

    protected override DataTemplate OnSelectTemplate(object item, BindableObject container) {
        var msg = item as ChatMessage;
        return msg?.Role == UserRole.ASSISTANT ? BotTemplate : UserTemplate;
    }
}
