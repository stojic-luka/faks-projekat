import { Link } from "react-router-dom";

const NotFound = () => {
  return (
    <div className="flex-1 flex flex-col items-center justify-center bg-gray-100 dark:bg-[#212121]">
      <h1 className="text-9xl font-extrabold text-gray-700 dark:text-gray-400">404</h1>
      <p className="mt-4 text-xl text-gray-800 dark:text-gray-300">Page Not Found</p>
      <Link to="/" className="mt-8 px-4 py-2 bg-blue-600 text-white rounded hover:bg-indigo-500 transition">
        Go Home
      </Link>
    </div>
  );
};

export default NotFound;
