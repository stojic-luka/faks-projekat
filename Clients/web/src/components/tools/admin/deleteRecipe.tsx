import React, { useState } from "react";
import { useRecipeDelete } from "../../../hooks/recipe/useRecipeDelete";

export const DeleteRecipe: React.FC = () => {
  const [recipeId, setRecipeId] = useState("");
  const { mutate, isPending, isError, error } = useRecipeDelete();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRecipeId(e.target.value);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!recipeId.trim()) return;
    mutate(recipeId);
  };

  return (
    <div className="max-w-2xl mt-6 w-full mx-auto p-6 border dark:border-neutral-700 bg-[#f4f4f4] dark:bg-[#292929] text-gray-200 rounded-lg shadow-md">
      <h1 className="text-2xl font-bold mb-4">Delete a Recipe</h1>
      <form onSubmit={handleSubmit} className="space-y-5">
        <div>
          <label htmlFor="recipeId" className="block text-sm font-medium mb-1">
            Recipe ID
          </label>
          <input
            id="recipeId"
            name="recipeId"
            type="text"
            required
            value={recipeId}
            onChange={handleChange}
            placeholder="Enter recipe ID to delete"
            className="mt-1 block w-full px-3 py-2 bg-neutral-900 text-gray-200 border border-gray-600 rounded text-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
          />
        </div>

        {isError && <p className="text-sm text-red-500">{error.error.message || "Something went wrong."}</p>}

        <button
          type="submit"
          disabled={isPending}
          className="w-full inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-gray-200 bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 disabled:opacity-50"
        >
          {isPending ? "Deleting…" : "Delete Recipe"}
        </button>
      </form>
    </div>
  );
};
