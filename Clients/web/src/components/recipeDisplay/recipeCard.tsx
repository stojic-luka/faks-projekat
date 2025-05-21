import { memo } from "react";
import { Recipe } from "../../types/recipesTypes";

interface Props {
  recipe: Recipe;
  onClick: () => void;
}
const RecipeCard = memo(({ recipe: { title, instructions, image }, onClick }: Props) => {
  return (
    <div
      onClick={onClick}
      className="flex flex-col sm:flex-row items-stretch overflow-hidden rounded-lg
        shadow-md bg-white dark:bg-[#292929] hover:shadow-xl transition-shadow
        duration-300 cursor-pointer my-2 mr-2 first-of-type:mt-0 last-of-type:mb-0"
      style={{ height: image?.metadata.height }}
    >
      {image?.data && (
        <img src={`data:image/jpeg;base64,${image.data}`} alt={title} className="object-cover max-h-40 sm:max-h-none max-w-60 w-full" />
      )}
      <div className="flex flex-col justify-between p-4 sm:w-2/3">
        <h1 className="text-lg font-bold text-gray-800 dark:text-white mb-2 truncate">{title}</h1>
        <p className="text-sm text-gray-600 dark:text-gray-300">{instructions.length > 200 ? instructions.slice(0, 200) + "..." : instructions}</p>
      </div>
    </div>
  );
});

export default RecipeCard;
