import { motion } from "framer-motion";
import { SearchRecipes } from "../components/search";

const Search = () => {
  return (
    <>
      <div className="flex justify-center h-auto w-full dark:bg-[#212121]">
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          transition={{ duration: 0.5 }}
          className="w-full max-w-[768px] h-full flex flex-col px-4 py-2"
        >
          <SearchRecipes />
        </motion.div>
      </div>
    </>
  );
};

export default Search;
