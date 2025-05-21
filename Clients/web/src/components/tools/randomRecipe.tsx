import { useState, useMemo } from "react";
import { useFetchRandomRecipe } from "../../hooks/recipe";
import { ErrorMessage } from "../shared";
import { RecipeDisplay } from "../recipeDisplay";

export const RandomRecipe = () => {
  const [pointer, setPointer] = useState(0);
  const { data: recipesData, isError: isErrorRecipes, error: recipesError, fetchNextPage, isFetching, isFetchingNextPage } = useFetchRandomRecipe();

  const recipes = useMemo(() => {
    return recipesData?.pages.flatMap((page) => (page.data ? [page.data] : [])) || [];
  }, [recipesData?.pages]);

  const handleNext = async () => {
    if (pointer < recipes.length - 1) {
      setPointer(pointer + 1);
    } else {
      await fetchNextPage();
      setPointer(pointer + 1);
    }
  };

  const handlePrev = () => {
    if (pointer > 0) setPointer(pointer - 1);
  };

  return (
    <div className="flex flex-col max-w-4xl w-full mx-auto">
      <div className="relative rounded-lg overflow-hidden">
        <RecipeDisplay recipe={recipes[pointer]} openInDesktopButtonVisible />
        {isErrorRecipes && (
          <div>
            <ErrorMessage message={recipesError?.error} />
          </div>
        )}
        {(isFetching || isFetchingNextPage) && (
          <div className="absolute flex inset-0 bg-black/50">
            <p className="m-auto text-2xl font-bold text-white">Loading...</p>
          </div>
        )}
      </div>
      <div className="my-2 mx-auto px-6">
        <button
          className="bg-blue-600 hover:bg-blue-700 transition-colors duration-200 disabled:bg-gray-400 text-white font-bold py-2 px-4 rounded disabled:cursor-not-allowed"
          onClick={handlePrev}
          disabled={pointer === 0}
        >
          Previous
        </button>
        <button
          className="bg-blue-600 hover:bg-blue-700 transition-colors duration-200 text-white font-bold py-2 px-4 rounded ml-2"
          onClick={handleNext}
        >
          Next
        </button>
      </div>
    </div>
  );
};
