import { memo, useCallback, useEffect, useRef, useState } from "react";
import { RecipeCard, RecipeModal } from "../recipeDisplay";
import { useFetchRecipes } from "../../hooks/recipe";
import { Recipe } from "../../types/recipesTypes";

interface RecipeListProps {
  recipes: ReturnType<typeof useFetchRecipes>["data"];
  fetchNextPage: ReturnType<typeof useFetchRecipes>["fetchNextPage"];
  hasNextPage: boolean;
  isFetching: boolean;
}
const RecipeList = memo(({ recipes, fetchNextPage, hasNextPage, isFetching }: RecipeListProps) => {
  const [selectedRecipe, setSelectedRecipe] = useState<Recipe | undefined>(undefined);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const observerRef = useRef<IntersectionObserver | null>(null);
  const sentinelRef = useCallback(
    (node: Element | null) => {
      if (isFetching) return;

      if (observerRef.current) observerRef.current.disconnect();

      observerRef.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasNextPage) {
          fetchNextPage();
        }
      });

      if (node) observerRef.current.observe(node);
    },
    [fetchNextPage, hasNextPage, isFetching]
  );

  useEffect(() => {
    setIsModalOpen(!!selectedRecipe);
  }, [selectedRecipe]);

  return (
    <>
      <RecipeModal recipe={selectedRecipe} isOpen={isModalOpen} onClose={() => setSelectedRecipe(undefined)} />
      <div className="">
        {recipes?.pages.map((page, pageIndex) =>
          page.data.map((recipe, recipeIndex) => (
            <RecipeCard key={`${pageIndex}-${recipeIndex}`} recipe={recipe} onClick={() => setSelectedRecipe(recipe)} />
          ))
        )}
        {hasNextPage && <div className="h-1" ref={sentinelRef} />}
        {isFetching && (
          <div className="my-6 w-full text-center text-neutral-200 text-xl">
            <p>Loading...</p>
          </div>
        )}
      </div>
    </>
  );
});
export default RecipeList;
