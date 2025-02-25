import { useCallback } from "react";
import { useFetchRandomRecipe } from "../../hooks/recipe";
import { ErrorMessage } from "../shared";
import { RecipeDisplay } from "../recipeDisplay";

export const RandomRecipe = () => {
  const { mutate, data: recipe, isPending, isError, error } = useFetchRandomRecipe();

  const handleFetchRecipe = useCallback(() => {
    mutate();
  }, [mutate]);

  return (
    <div>
      <RecipeDisplay recipe={recipe?.data} />
      {isError && (
        <div>
          <ErrorMessage message={error?.error} />
        </div>
      )}
      {isPending && (
        <div>
          <p>Loading...</p>
        </div>
      )}
      <button onClick={handleFetchRecipe}>Fetch recipe</button>
    </div>
  );
};
