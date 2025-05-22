import React, { useState } from "react";
import { useRecipeSubmit } from "../../../hooks/recipe/useRecipeSubmit";
import { RecipeForm, RecipeInput } from "../../../types/recipesTypes";
import { useSearchParams } from "react-router-dom";
import { useRecipeUpdate } from "../../../hooks/recipe/useRecipeUpdate";

export const SubmitRecipe: React.FC = () => {
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id") || undefined;
  const isEdit = !!id;

  const [imageUploading, setImageUploading] = useState(false);
  const [formData, setFormData] = useState<RecipeForm>({
    title: "",
    ingredients: "",
    instructions: "",
  });

  const submitMutation = useRecipeSubmit();
  const updateMutation = useRecipeUpdate();
  const { isPending, isError, error } = isEdit ? updateMutation : submitMutation;

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files || !e.target.files[0]) return;

    setImageUploading(true);
    const file = e.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      const dataUrl = reader.result as string;
      const img = new Image();
      img.src = dataUrl;
      img.onload = () => {
        setFormData((prev) => ({
          ...prev,
          image: {
            contentType: file.type,
            data: dataUrl.split(",")[1],
            metadata: {
              width: img.naturalWidth,
              height: img.naturalHeight,
            },
          },
        }));
        setImageUploading(false);
      };
    };
    reader.onerror = () => {
      console.error("Failed to read file");
      setImageUploading(false);
    };
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((f) => ({ ...f, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    const reqBase: RecipeInput = {
      title: formData.title,
      ingredients: formData.ingredients.split(",").map((ingredient) => ingredient.trim()),
      instructions: formData.instructions,
      ...(formData.image && { image: formData.image }),
    };

    if (isEdit) {
      updateMutation.mutate({
        recipeId: id!,
        recipe: reqBase,
      });
    } else {
      submitMutation.mutate(reqBase);
    }
  };

  return (
    <div className="max-w-2xl mt-6 w-full mx-auto p-6 border dark:border-neutral-700 bg-[#f4f4f4] dark:bg-[#292929] text-gray-200 rounded-lg shadow-md">
      <h1 className="text-2xl font-bold mb-4">Submit a Recipe</h1>
      <form onSubmit={handleSubmit} className="space-y-5">
        <div>
          <label htmlFor="title" className="block text-sm font-medium mb-1">
            Title
          </label>
          <input
            id="title"
            name="title"
            type="text"
            required
            value={formData.title}
            onChange={handleChange}
            className="mt-1 block w-full px-3 py-2 bg-neutral-900 text-gray-200 border border-gray-600 rounded text-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
          />
        </div>

        <div>
          <label htmlFor="ingredients" className="block text-sm font-medium mb-1">
            Ingredients (comma-separated)
          </label>
          <textarea
            id="ingredients"
            name="ingredients"
            rows={3}
            required
            value={formData.ingredients}
            onChange={handleChange}
            className="mt-1 block w-full px-3 py-2 bg-neutral-900 text-gray-200 border border-gray-600 rounded text-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
          />
        </div>

        <div>
          <label htmlFor="instructions" className="block text-sm font-medium mb-1">
            Instructions
          </label>
          <textarea
            id="instructions"
            name="instructions"
            rows={6}
            required
            value={formData.instructions}
            onChange={handleChange}
            className="mt-1 block w-full px-3 py-2 bg-neutral-900 text-gray-200 border border-gray-600 rounded text-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
          />
        </div>

        <div>
          <label htmlFor="image" className="block text-sm font-medium mb-1">
            Image
          </label>
          <input
            id="image"
            name="image"
            type="file"
            accept="image/*"
            onChange={handleFileChange}
            className="mt-1 block w-full px-3 py-2 bg-neutral-900 text-gray-200 border border-gray-600 rounded text-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 file:mr-4 file:py-2 file:px-4 file:rounded file:border-0 file:bg-blue-600 file:text-gray-200 file:font-semibold file:hover:bg-blue-700 "
          />
          {imageUploading && <p className="mt-1 text-sm text-gray-400">Uploading image…</p>}
        </div>

        {isError && <p className="text-sm text-red-500">{error.error.message || "Something went wrong."}</p>}

        <button
          type="submit"
          disabled={isPending || imageUploading}
          className="w-full inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-gray-200 bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 "
        >
          {isPending ? "Submitting…" : "Submit Recipe"}
        </button>
      </form>
    </div>
  );
};
