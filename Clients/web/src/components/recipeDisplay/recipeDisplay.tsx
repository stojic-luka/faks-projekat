import { useCallback } from "react";
import { Recipe } from "../../types/recipesTypes";
import { SmallestImage } from "../../util";

interface Props {
  recipe: Recipe | undefined;
  openInDesktopButtonVisible?: boolean;
}
const RecipeDisplay = ({ recipe, openInDesktopButtonVisible = false }: Props) => {
  const handleOpenDesktopApp = useCallback(() => {
    if (recipe?.id) {
      window.open(`augmentedcooking://recipe/${recipe.id}`);
    } else {
      alert("An error occurred while opening the recipe in the desktop app.");
    }
  }, [recipe?.id]);

  return (
    <div className="p-4 shadow-md rounded-lg border dark:border-neutral-700 bg-[#f4f4f4] dark:bg-[#292929] focus:outline-none">
      <div className="flex justify-between mb-4">
        <h1 className="text-xl h-auto my-auto font-bold text-gray-800 dark:text-white">{recipe?.title || "Recipe Title"}</h1>
        {openInDesktopButtonVisible && (
          <button
            className="bg-blue-600 text-white px-2 py-2 rounded hover:bg-blue-700 transition-colors duration-200 mt-4 md:mt-0 text-sm whitespace-nowrap"
            onClick={handleOpenDesktopApp}
          >
            Open in desktop app
          </button>
        )}
      </div>
      <div className="flex flex-col md:flex-row gap-4">
        <div className="md:w-1/2 h-64 overflow-y-auto scrollbar-redesign dark:scrollbar-redesign-dark p-4 bg-white dark:bg-[#212121] rounded-lg border dark:border-neutral-700">
          <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-2">Ingredients</h2>
          {recipe?.ingredients && recipe.ingredients.length > 0 ? (
            <ul className="list-disc list-inside text-gray-700 dark:text-gray-300">
              {recipe.ingredients.map((ingredient, index) => (
                <li key={index}>{ingredient}</li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-600 dark:text-gray-400">No ingredients listed.</p>
          )}
        </div>
        <div className="md:w-1/2 h-64 flex items-center justify-center bg-white dark:bg-[#212121] rounded-lg overflow-hidden border dark:border-neutral-700">
          {recipe?.image ? (
            <img src={`data:image/jpeg;base64,${recipe.image.data}`} alt="Recipe" className="object-cover w-full h-full" />
          ) : (
            <SmallestImage />
          )}
        </div>
      </div>
      <div className="mt-4 h-64 overflow-y-auto p-4 bg-white dark:bg-[#212121] rounded-lg border scrollbar-redesign dark:scrollbar-redesign-dark dark:border-neutral-700">
        <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-2">Instructions</h2>
        <p className="text-gray-700 dark:text-gray-300">{recipe?.instructions || "No instructions provided."}</p>
      </div>
    </div>
  );
};

export default RecipeDisplay;
