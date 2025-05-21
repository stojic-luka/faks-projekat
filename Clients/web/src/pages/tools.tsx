import { motion } from "framer-motion";
import { RandomRecipe } from "../components/tools";
import { useAuth } from "../contexts/useAuth";

const Tools = () => {
  const { isAdmin } = useAuth();

  return (
    <>
      <div className="flex flex-col w-full h-full pt-2 dark:bg-[#212121]">
        <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} transition={{ duration: 0.5 }}>
          <RandomRecipe />
          {/* <div className="p-4">
            <FavoriteRecipes />
          </div> */}
          {isAdmin && <div className="p-4"></div>}
        </motion.div>
      </div>
    </>
  );
};

export default Tools;
