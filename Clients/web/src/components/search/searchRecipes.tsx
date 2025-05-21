import { useCallback, useEffect, useRef, useState } from "react";
import { useFetchRecipes } from "../../hooks/recipe/useFetchRecipes";
import { ErrorMessage } from "../../components/shared";
import { useQueryClient } from "@tanstack/react-query";
import { useSearchParams } from "react-router-dom";
import { RecipeList } from "../recipeDisplay";

const SearchRecipes = () => {
  const [searchText, setSearchText] = useState("");
  const ingredientsRef = useRef<string[]>([]);
  const [isFirstRender, setIsFirstRender] = useState(true);
  const [searchParams, setSearchParams] = useSearchParams();
  const queryClient = useQueryClient();

  const {
    data: recipes,
    isError: isSearchError,
    error: searchError,
    fetchNextPage,
    hasNextPage,
    isFetching,
    isFetchingNextPage,
  } = useFetchRecipes(ingredientsRef.current);

  const handleUpdateIngredients = useCallback((searchText: string) => {
    ingredientsRef.current = searchText
      .trim()
      .split(/[\s,]+/)
      .map((ingredient) => ingredient.trim())
      .filter(Boolean);
  }, []);

  useEffect(() => {
    const trimmed = searchText.trim();
    if (!trimmed) {
      ingredientsRef.current = [];
      setSearchParams({});
      return;
    }
    const updateIngredients = () => {
      handleUpdateIngredients(trimmed);
      setSearchParams({ q: trimmed });
    };
    if (isFirstRender) {
      updateIngredients();
      setIsFirstRender(false);
    } else {
      const handler = setTimeout(updateIngredients, 500);
      return () => clearTimeout(handler);
    }
  }, [searchText, handleUpdateIngredients, setSearchParams, isFirstRender]);

  useEffect(() => {
    if (isFirstRender) {
      const query = searchParams.get("q");
      if (query) setSearchText(query);
      else setIsFirstRender(false);
    }
  }, [searchParams, handleUpdateIngredients, isFirstRender]);

  const handleOnKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      handleUpdateIngredients(searchText.trim());
      queryClient.resetQueries({ queryKey: ["fetch-recipes-ingredients"] });
    }
    const allowedKeys = /^[a-zA-Z0-9,]*$/;
    if (!allowedKeys.test(e.key) && e.key !== "Backspace" && e.key !== " ") e.preventDefault();
  };

  return (
    <>
      <div className="dark:bg-[#292929] p-2 rounded-lg my-2">
        <input
          type="text"
          placeholder="Type ingredients..."
          className="w-full bg-transparent text-white"
          onChange={(e) => setSearchText(e.target.value)}
          onKeyDown={handleOnKeyDown}
          value={searchText}
        />
      </div>
      <div className="flex flex-col max-h-full overflow-y-auto scrollbar-redesign dark:scrollbar-redesign-dark max-w-full">
        <RecipeList recipes={recipes} fetchNextPage={fetchNextPage} hasNextPage={hasNextPage} isFetching={isFetching || isFetchingNextPage} />
        {isSearchError && <ErrorMessage message={searchError?.error} />}
        {!hasNextPage && !(isFetching || isFetchingNextPage) && ingredientsRef.current.length > 0 && (
          <div className="my-6 w-full text-center text-neutral-200 text-xl">
            <p>No more recipes</p>
          </div>
        )}
      </div>
    </>
  );
};

export default SearchRecipes;
