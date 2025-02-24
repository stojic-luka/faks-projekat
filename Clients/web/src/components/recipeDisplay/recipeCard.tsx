import { memo, useMemo } from "react";
import { Recipe } from "../../types/recipesTypes";

interface Props {
  recipe: Recipe;
  onClick: () => void;
}
const RecipeCard = memo(({ recipe: { title, ingredients, image }, onClick }: Props) => {
  const formattedIngredients = useMemo(() => {
    const ingredientsList = ingredients.join(", ");
    return ingredientsList.length > 50 ? ingredientsList.slice(0, 50) + "..." : ingredientsList;
  }, [ingredients]);

  return (
    <>
      <div
        className="flex flex-col sm:flex-row items-stretch overflow-hidden rounded-lg shadow-md bg-white hover:shadow-xl transition-shadow duration-300 cursor-pointer"
        onClick={onClick}
      >
        {image && <img src={`data:image/jpeg;base64,${image}`} alt={title} className="object-cover max-h-40 sm:max-h-none max-w-60 w-full my-2" />}
        <div className="flex flex-col justify-between p-4 sm:w-2/3">
          <h1 className="text-lg font-bold text-gray-800 mb-2 truncate">{title}</h1>
          <p className="flex flex-col text-sm text-gray-600 my-auto">
            <span className="font-semibold">Ingredients:</span>
            <span>{formattedIngredients}</span>
          </p>
        </div>
      </div>
    </>
  );
});

export default RecipeCard;
