using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;

namespace AugmentedCooking.src.Services.ChatServices;

public interface IChatSettingsService {
    bool StreamResponse { get; set; }
    IList<string> AvailableModels { get; }
    string SelectedModel { get; set; }

    event EventHandler<bool> StreamResponseChanged;
    event EventHandler<string> SelectedModelChanged;
}

public partial class ChatSettingsService : ObservableObject, IChatSettingsService {
    const string KeyStream = "chat_stream";
    const string KeyModel = "chat_model";

    readonly IList<string> _models = ["llama3.2:3b"];
    public IList<string> AvailableModels => _models;

    public event EventHandler<bool> StreamResponseChanged = delegate { };
    public event EventHandler<string> SelectedModelChanged = delegate { };

    public ChatSettingsService() {
        _streamResponse = Preferences.Get(KeyStream, false);
        _selectedModel = Preferences.Get(KeyModel, _models[0]);
    }

    // event EventHandler<bool> StreamResponseChanged;

    // event EventHandler<string> SelectedModelChanged;

    [ObservableProperty]
    bool _streamResponse;
    partial void OnStreamResponseChanged(bool value) {
        Preferences.Set(KeyStream, value);
        StreamResponseChanged?.Invoke(this, value);
    }

    [ObservableProperty]
    string _selectedModel;
    partial void OnSelectedModelChanged(string value) {
        Preferences.Set(KeyModel, value);
        SelectedModelChanged?.Invoke(this, value);
    }
}
