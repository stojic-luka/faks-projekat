import { useCallback, useEffect, useRef, useState } from "react";
import { Recipe } from "../types/recipesTypes";
import RecipeCard from "../components/recipeDisplay/recipeCard";
import { useFetchRecipes } from "../hooks/recipe/useFetchRecipes";
import { ErrorMessage } from "../components/shared";
import { RecipeModal } from "../components/recipeDisplay";
import { useQueryClient } from "@tanstack/react-query";

const Search = () => {
  const [ingredients, setIngredients] = useState<string[]>([]);
  const [selectedRecipe, setSelectedRecipe] = useState<Recipe | undefined>(undefined);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const queryClient = useQueryClient();

  const {
    data: recipes,
    isError: isSearchError,
    error: searchError,
    fetchNextPage,
    hasNextPage,
    isFetching,
    isFetchingNextPage,
  } = useFetchRecipes(ingredients);

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

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const ingredients = e.target.value
      .split(",")
      .map((ingredient) => ingredient.trim())
      .filter(Boolean);
    setIngredients(ingredients);
  };

  const handleOnKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      queryClient.resetQueries({ queryKey: ["fetch-recipes-ingredients"] });
      fetchNextPage();
    }
    const allowedKeys = /^[a-zA-Z0-9,]*$/;
    if (!allowedKeys.test(e.key) && e.key !== "Backspace" && e.key !== " ") e.preventDefault();
  };

  useEffect(() => {
    setIsModalOpen(!!selectedRecipe);
  }, [selectedRecipe]);

  return (
    <>
      <RecipeModal recipe={selectedRecipe} isOpen={isModalOpen} onClose={() => setSelectedRecipe(undefined)} />
      <div className="flex justify-center h-full w-full dark:bg-[#212121] py-2">
        <div className="w-full max-w-[768px] h-full flex flex-col px-4">
          <div className="dark:bg-[#292929] p-2 rounded-lg my-2">
            <input
              type="text"
              placeholder="Type ingredients..."
              className="w-full bg-transparent text-white"
              onChange={handleOnChange}
              onKeyDown={handleOnKeyDown}
            />
          </div>
          <div className="min-h-full">
            {recipes?.pages.map((recipe, index) => (
              <RecipeCard key={index} recipe={recipe} onClick={() => setSelectedRecipe(recipe)} />
            ))}
          </div>
          {(isFetching || isFetchingNextPage) && <p>Loading...</p>}
          {isSearchError && <ErrorMessage message={searchError?.error} />}
          {hasNextPage && <div ref={sentinelRef} />}
          {!hasNextPage && (
            <div className="my-6 w-full text-center">
              <p>No more recipes</p>
            </div>
          )}
        </div>
      </div>
    </>
  );
};

export default Search;
