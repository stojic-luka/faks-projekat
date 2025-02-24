namespace AugmentedCooking.src.Models {
    public class Recipe {
        public static Recipe Empty => new() { Id = -1, Title = "", Ingredients = [], Instructions = "", Image = "" };

        public required int Id { get; set; }
        public required string Title { get; set; }
        public required string[] Ingredients { get; set; }
        public required string Instructions { get; set; }
        public required string Image { get; set; }

        public override string ToString() {
            return $@"Recipe:\n"
                   + "Id: {Id}"
                   + "Title: {Title}"
                   + $"Ingredients: [{string.Join(", ", Ingredients)}]"
                   + "Instructions: {Instructions}"
                   + "Image: {Image}";
        }
    }
}
