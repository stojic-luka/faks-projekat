import { Recipe } from "../../types/recipesTypes";
import { Modal } from "../shared";
import RecipeDisplay from "./recipeDisplay";

interface Props {
  recipe: Recipe | undefined;
  isOpen: boolean;
  onClose: () => void;
}
const RecipeModal = ({ recipe, isOpen, onClose }: Props) => {
  return (
    <Modal open={isOpen} onClose={onClose} className="max-w-4xl w-full">
      <RecipeDisplay recipe={recipe} openInDesktopButtonVisible />
    </Modal>
  );
};

export default RecipeModal;
