using System.Net;
using System.Text;
using System.Security.Cryptography;

namespace AugmentedCooking.src.Utils;

public static class AuthUtils {
    public static string Base64UrlEncode(byte[] bytes) {
        var s = Convert.ToBase64String(bytes)
            .TrimEnd('=')
            .Replace('+', '-')
            .Replace('/', '_');
        return s;
    }

    public static string GenerateRandomString(int bytesLength) {
        var bytes = new byte[bytesLength];
        using var rng = RandomNumberGenerator.Create();
        rng.GetBytes(bytes);
        return Base64UrlEncode(bytes);
    }

    public static string ComputeSha512Base64Url(string input) => ComputeSha512Base64Url(Encoding.ASCII.GetBytes(input));

    public static string ComputeSha512Base64Url(byte[] input) => Base64UrlEncode(SHA512.HashData(input));

    public static string ToQueryString(IDictionary<string, string> dict) {
        var sb = new StringBuilder();
        foreach (var kv in dict) {
            if (sb.Length > 0) sb.Append('&');
            sb.Append(WebUtility.UrlEncode(kv.Key));
            sb.Append('=');
            sb.Append(WebUtility.UrlEncode(kv.Value));
        }
        return sb.ToString();
    }
}
