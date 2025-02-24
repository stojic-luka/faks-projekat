import { FavoriteRecipes, RandomRecipe, SubmitRecipe } from "../components/tools";

const Tools = () => {
  return (
    <>
      <div className="flex flex-col w-full h-full">
        <div className="flex flex-row">
          <div className="w-1/2 p-4">
            <RandomRecipe />
          </div>
          <div className="w-1/2 p-4">
            <SubmitRecipe />
          </div>
        </div>
        <div className="p-4">
          <FavoriteRecipes />
        </div>
      </div>
    </>
  );
};

export default Tools;
