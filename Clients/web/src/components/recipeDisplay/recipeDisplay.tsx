import { useCallback } from "react";
import { Recipe } from "../../types/recipesTypes";
import { SmallestImage } from "../../util";

interface Props {
  recipe: Recipe | undefined;
  openInDesktopButtonVisible?: boolean;
}
const RecipeDisplay = ({ recipe, openInDesktopButtonVisible = false }: Props) => {
  const handleOpenDesktopApp = useCallback(() => {
    window.open("augmentedcooking://recipe/" + recipe?.id);
  }, [recipe?.id]);

  return (
    <>
      <div className="bg-white p-6 rounded-lg">
        <div className="flex flex-col md:flex-row md:items-center md:justify-between mb-4">
          <h1 className="text-2xl font-bold text-gray-800 text-center md:text-left">{recipe?.title || "Recipe Title"}</h1>
          {openInDesktopButtonVisible && (
            <button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600" onClick={() => handleOpenDesktopApp()}>
              Open in desktop app
            </button>
          )}
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
          <div className="overflow-y-auto pr-2 max-h-64">
            <h2 className="text-lg font-semibold text-gray-700 mb-2">Ingredients</h2>
            <ul className="list-disc list-inside text-gray-600">
              {recipe?.ingredients.map((ingredient, index) => (
                <li key={index} className="mb-1">
                  {ingredient}
                </li>
              )) || <p>No ingredients listed.</p>}
            </ul>
          </div>
          <div className="flex justify-center items-center">
            {recipe?.image ? (
              <img
                src={`data:image/jpeg;base64,${recipe?.image}`}
                alt="Recipe"
                className="max-w-full max-h-64 object-cover border border-gray-200 rounded-md"
              />
            ) : (
              <SmallestImage />
            )}
          </div>
        </div>
        <div className="overflow-y-auto max-h-64">
          <h2 className="text-lg font-semibold text-gray-700 mb-2">Instructions</h2>
          <p className="whitespace-pre-wrap text-gray-600 leading-relaxed">{recipe?.instructions || "No instructions provided."}</p>
        </div>
      </div>
    </>
  );
};

export default RecipeDisplay;
