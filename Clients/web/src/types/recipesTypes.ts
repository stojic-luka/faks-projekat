import { Pagination } from "./requestTypes";

export interface Recipe {
  id: string;
  title: string;
  ingredients: string[];
  instructions: string;
  image: string;
}

export interface RecipeFilterArguments extends Pagination {
  ingredients?: string[];
}
