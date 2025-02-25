using System.Net.Sockets;
using System.Net;
using System.Text;
using AugmentedCooking.src.Models;
using System.IO;

namespace DesktopClient.src.Services {
    public class TcpServerService(int port) {
        private readonly TcpListener _server = new(IPAddress.Any, port);
        private bool IsRunning;
        private TcpClient? _connectedClient;

        public required Recipe RecipeToSync { get; set; } = Recipe.Empty;

        public async Task StartServerAsync(CancellationToken cancellationToken) {
            try {
                _server.Start();
                IsRunning = true;
                Console.WriteLine("Server started...");

                while (IsRunning && !cancellationToken.IsCancellationRequested) {
                    TcpClient? client = await AcceptSingleClientAsync(cancellationToken);
                    if (client != null) {
                        _ = Task.Run(() => HandleClientAsync(client, cancellationToken), cancellationToken);
                    }
                }
            } catch (Exception ex) {
                Console.WriteLine($"Error starting server: {ex.Message}");
            }

        }

        private async Task<TcpClient?> AcceptSingleClientAsync(CancellationToken cancellationToken) {
            while (IsRunning && !cancellationToken.IsCancellationRequested) {
                try {
                    if (_connectedClient == null)
                        return await _server.AcceptTcpClientAsync(cancellationToken);
                    else
                        await Task.Delay(1000, cancellationToken);
                } catch {
                    return null;
                }
            }
            return null;
        }

        private async Task HandleClientAsync(TcpClient client, CancellationToken cancellationToken) {
            try {
                _connectedClient = client;
                using var stream = client.GetStream();
                int currentIndex = 0;

                while (IsRunning && !cancellationToken.IsCancellationRequested) {
                    byte[] buffer = new byte[1024];
                    int bytesRead = await stream.ReadAsync(buffer, cancellationToken);

                    if (bytesRead == 0)
                        break;

                    string received = Encoding.ASCII.GetString(buffer, 0, bytesRead).Trim();
                    Console.WriteLine($"Received: {received}");

                    string? response = received switch {
                        "SYNC" => "SYNCED",
                        "NEXT" => currentIndex == 0 ? RecipeToSync.Title : RecipeToSync.Ingredients.ElementAtOrDefault(currentIndex) ?? "END",
                        "DISCONNECT" => "DISCONNECTING",
                        _ => null
                    };

                    if (received == "DISCONNECT" || response == null)
                        break;

                    byte[] responseBytes = Encoding.ASCII.GetBytes(response);
                    await stream.WriteAsync(responseBytes, cancellationToken);
                }
            } catch (Exception ex) {
                Console.WriteLine($"Client error: {ex.Message}");
            } finally {
                StopServer();
            }
        }

        public void StopServer() {
            try {
                if (_connectedClient != null) {
                    _connectedClient.Close();
                    _connectedClient = null;
                }
                IsRunning = false;
                _server.Stop();
                Console.WriteLine("Server stopped.");
            } catch (Exception ex) {
                Console.WriteLine($"Error stopping server: {ex.Message}");
            }
        }
    }
}
