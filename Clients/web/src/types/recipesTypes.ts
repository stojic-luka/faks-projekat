import { Pagination } from "./requestTypes";

interface ImageMetadata {
  width: number;
  height: number;
}

interface RecipeImage {
  contentType: string;
  data: string;
  metadata: ImageMetadata;
}

interface RecipeBase {
  title: string;
  ingredients: string[];
  instructions: string;
  image: RecipeImage;
}

export type RecipeInput = Omit<RecipeBase, "image"> & Partial<Pick<RecipeBase, "image">>;
export type Recipe = RecipeBase & { id: string };

export interface RecipeForm {
  title: string;
  ingredients: string;
  instructions: string;
  image?: RecipeImage;
}

export interface RecipeFilterArguments extends Pagination {
  ingredients?: string[];
}
