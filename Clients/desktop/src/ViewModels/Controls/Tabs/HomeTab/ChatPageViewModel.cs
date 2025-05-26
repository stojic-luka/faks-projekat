using System.Collections.ObjectModel;
using AugmentedCooking.src.Models.Chat;
using AugmentedCooking.src.Models.Chat.Enums;
using AugmentedCooking.src.Models.Response.Chat.Streamed;
using AugmentedCooking.src.Services.ChatServices;
using AugmentedCooking.src.Services.UserServices;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;

namespace AugmentedCooking.src.ViewModels.Controls.Tabs.HomeTab;

public partial class ChatPageViewModel : ObservableObject {
    // injected services
    readonly IChatService _chatService;
    readonly IMessagesService _chatStorage;
    readonly IChatSettingsService _chatSettingsService;
    readonly IUserSession _userSession;

    CancellationTokenSource _cts = new();

    public event Action<int>? ScrollToEndRequested;

    public ChatPageViewModel(IChatService chatService, IMessagesService chatStorage, IChatSettingsService chatSettingsService, IUserSession userSession) {
        _chatService = chatService;
        _chatStorage = chatStorage;
        _chatSettingsService = chatSettingsService;
        _userSession = userSession;

        StreamResponse = _chatSettingsService.StreamResponse;
        AvailableModels = _chatSettingsService.AvailableModels;
        SelectedModel = _chatSettingsService.SelectedModel;

        _chatSettingsService.StreamResponseChanged += (_, val) => StreamResponse = val;
        _chatSettingsService.SelectedModelChanged += (_, mdl) => SelectedModel = mdl;

        Messages.CollectionChanged += (_, __) => OnPropertyChanged(nameof(IsEmpty));

        _ = LoadMessagesAsync();
    }


    [ObservableProperty]
    ObservableCollection<ChatMessage> messages = [];

    public bool IsEmpty => Messages.Count == 0;

    [ObservableProperty]
    string inputText = string.Empty;

    bool isPendingChat;
    public bool IsPendingChat {
        get => isPendingChat;
        set {
            if (SetProperty(ref isPendingChat, value)) {
                OnPropertyChanged(nameof(IsNotPendingChat));
                OnPropertyChanged(nameof(SendButtonIcon));
            }
        }
    }


    public bool IsNotPendingChat => !IsPendingChat;
    public string SendButtonIcon => IsPendingChat ? "■" : "➤";

    [ObservableProperty]
    bool isErrorChat;

    [ObservableProperty]
    string errorMessage = string.Empty;

    [ObservableProperty]
    bool isSettingsVisible;

    [ObservableProperty]
    bool streamResponse;

    [ObservableProperty]
    IList<string> availableModels;

    [ObservableProperty]
    string selectedModel;

    [RelayCommand]
    void ToggleSettings() => IsSettingsVisible = !IsSettingsVisible;

    [RelayCommand]
    Task UploadImageAsync() {
        return Task.CompletedTask;
    }

    [RelayCommand]
    async Task SendOrAbortAsync() {
        if (IsPendingChat) {
            _cts?.Cancel();
            return;
        }

        if (string.IsNullOrWhiteSpace(InputText))
            return;

        IsPendingChat = true;
        ErrorMessage = string.Empty;

        var userMsg = new ChatMessage {
            Id = $"temp-{DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()}",
            UserId = _userSession.CurrentUser!.Id,
            Role = UserRole.USER,
            Type = MessageType.USER,
            Content = new ChatContent { Text = InputText },
            Model = SelectedModel,
            Status = MessageStatus.SENT,
            Timestamp = DateTimeOffset.UtcNow.ToUnixTimeSeconds()
        };
        Messages.Add(userMsg);
        await _chatStorage.AddAsync(userMsg);
        ScrollToEndRequested?.Invoke(Messages.Count - 1);

        InputText = string.Empty;
        _cts = new();

        ChatMessage? botRef = null;
        try {
            if (StreamResponse) {
                await foreach (var chunk in _chatService.StreamMessageAsync(userMsg.Content.Text, "", _cts.Token)) {
                    if (chunk.Type == MessageType.STREAMED_METADATA && chunk is AiChatStreamedMetadataResponse metadataChunk) {
                        botRef = new ChatMessage {
                            Id = chunk.Id,
                            UserId = metadataChunk.Content.UserId,
                            Role = UserRole.ASSISTANT,
                            Type = metadataChunk.Type,
                            Content = new ChatContent {
                                Text = "",
                                Images = []
                            },
                            Model = metadataChunk.Content.Model,
                            Status = MessageStatus.STREAMING,
                            Timestamp = metadataChunk.Content.Timestamp
                        };
                        Messages.Add(botRef);
                        await _chatStorage.AddAsync(botRef);
                        ScrollToEndRequested?.Invoke(Messages.Count - 1);
                    }
                    else if (botRef is not null) {
                        if (chunk.Type == MessageType.STREAMED_TEXT && chunk is AiChatStreamedTextResponse textChunk) {
                            botRef.Content.Text += textChunk.Content;

                            var idx = Messages.IndexOf(botRef);
                            Messages[idx] = botRef;
                            await _chatStorage.UpdateAsync(botRef);
                        }
                        else if (chunk.Type == MessageType.STREAMED_IMAGE && chunk is AiChatStreamedImageResponse imageChunk) {
                            botRef.Content.Images.Add(new ChatImageContent {
                                Data = imageChunk.Content.Data,
                                Format = imageChunk.Content.Format,
                                Width = imageChunk.Content.Width,
                                Height = imageChunk.Content.Height,
                                Description = imageChunk.Content.Description,
                            });

                            var idx = Messages.IndexOf(botRef);
                            Messages[idx] = botRef;
                            await _chatStorage.UpdateAsync(botRef);
                        }
                    }
                }

                if (botRef is not null) {
                    botRef.Status = MessageStatus.DONE;
                    await _chatStorage.UpdateAsync(botRef);
                }
            }
            else {
                var resp = await _chatService.SendMessageAsync(userMsg.Content.Text, "", _cts.Token);
                var done = new ChatMessage {
                    Id = resp.Id,
                    UserId = resp.UserId,
                    Role = UserRole.ASSISTANT,
                    Type = MessageType.COMPLETE,
                    Content = new ChatContent { Text = resp.Content.Text },
                    Model = resp.Model,
                    Status = MessageStatus.DONE,
                    Timestamp = resp.Timestamp
                };
                Messages.Add(done);
                ScrollToEndRequested?.Invoke(Messages.Count - 1);
                await _chatStorage.AddAsync(done);
            }
        }
        catch (OperationCanceledException) { /* aborted */ }
        catch (Exception ex) {
            ErrorMessage = ex.Message;
            IsErrorChat = true;
            var errMsg = botRef ?? userMsg;
            errMsg.Status = MessageStatus.ERROR;
            await _chatStorage.UpdateAsync(errMsg);
        }
        finally {
            IsPendingChat = false;
            OnPropertyChanged(nameof(IsNotPendingChat));
        }
    }



    [RelayCommand]
    async Task ClearChatAsync() {
        if (!_userSession.IsLoggedIn)
            return;

        _cts?.Cancel();
        Messages.Clear();
        await _chatStorage.DeleteAllAsync(_userSession.CurrentUser!.Id);
    }

    async Task LoadMessagesAsync() {
        if (!_userSession.IsLoggedIn)
            return;

        var stored = await _chatStorage.GetAllAsync(_userSession.CurrentUser!.Id);
        foreach (var m in stored)
            Messages.Add(m);

        ScrollToEndRequested?.Invoke(Messages.Count - 1);
    }
}
