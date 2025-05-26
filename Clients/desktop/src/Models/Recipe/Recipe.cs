namespace AugmentedCooking.src.Models.Recipe;

public class Recipe {
    public static Recipe Empty => new() { Id = "", Title = "", Ingredients = [], Instructions = "" };

    public required string Id { get; set; }
    public required string Title { get; set; }
    public required string[] Ingredients { get; set; }
    public required string Instructions { get; set; }
    public RecipeImage? Image { get; set; }
}

public class RecipeImage {
    public required string ContentType { get; set; }
    public required string Data { get; set; }
    public required RecipeMetadata Metadata { get; set; }
}

public class RecipeMetadata {
    public required int Height { get; set; }
    public required int Width { get; set; }
}
