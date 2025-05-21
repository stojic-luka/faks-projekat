import { useCallback, useEffect, useRef, useState } from "react";
import { useFetchFavoriteRecipes } from "../../hooks/recipe";
import { Recipe } from "../../types/recipesTypes";
import { RecipeCard, RecipeModal } from "../recipeDisplay";
import { ErrorMessage } from "../shared";

export const FavoriteRecipes = () => {
  const [selectedRecipe, setSelectedRecipe] = useState<Recipe | undefined>(undefined);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const {
    data: favoriteRecipes,
    isError: isRecipesError,
    error: recipesError,
    fetchNextPage,
    hasNextPage,
    isFetching,
    isFetchingNextPage,
  } = useFetchFavoriteRecipes();

  const observerRef = useRef<IntersectionObserver | null>(null);
  const sentinelRef = useCallback(
    (node: Element | null) => {
      if (isFetching || isFetchingNextPage) return;

      if (observerRef.current) observerRef.current.disconnect();

      observerRef.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasNextPage) {
          fetchNextPage();
        }
      });

      if (node) observerRef.current.observe(node);
    },
    [fetchNextPage, hasNextPage, isFetching, isFetchingNextPage]
  );

  useEffect(() => {
    setIsModalOpen(!!selectedRecipe);
  }, [selectedRecipe]);

  return (
    <>
      <RecipeModal recipe={selectedRecipe} isOpen={isModalOpen} onClose={() => setSelectedRecipe(undefined)} />
      <div>
        <h1>Favorite Recipes</h1>
        <div className="recipes-list">
          {favoriteRecipes?.pages.map((recipe) =>
            recipe.data.map((recipe, recipeIndex) => <RecipeCard key={recipeIndex} recipe={recipe} onClick={() => setSelectedRecipe(recipe)} />)
          )}
        </div>
        {(isFetching || isFetchingNextPage) && <p>Loading...</p>}
        {isRecipesError && <ErrorMessage message={recipesError?.error} />}
        {hasNextPage && <div ref={sentinelRef} />}
        {!hasNextPage && (
          <div className="my-6 w-full text-center">
            <p>No more recipes</p>
          </div>
        )}
      </div>
    </>
  );
};
