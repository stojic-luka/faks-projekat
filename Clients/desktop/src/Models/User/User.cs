namespace AugmentedCooking.src.Models.User;

public class User {
    public required string Id { get; set; }
    public required string Username { get; set; }
    public required string Email { get; set; }
    public required UserRoles[] Roles { get; set; }

    // TODO?: Remove this later
    public string? AccessToken { get; set; }
}
