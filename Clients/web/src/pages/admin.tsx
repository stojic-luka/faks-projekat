import { motion } from "framer-motion";
import { DeleteRecipe, SubmitRecipe } from "../components/tools";

const Admin = () => {
  return (
    <div className="flex-1 flex flex-col items-center justify-center bg-gray-100 dark:bg-[#212121] overflow-h-scroll lg:overflow-hidden">
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        transition={{ duration: 0.5 }}
        className="w-full max-w-4xl flex flex-col px-4 pb-2"
      >
        <div className="flex flex-col lg:flex-row lg:gap-4">
          <SubmitRecipe />
          <DeleteRecipe />
        </div>
      </motion.div>
    </div>
  );
};

export default Admin;
