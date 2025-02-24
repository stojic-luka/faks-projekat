interface Props {
  isActive: boolean;
  togglePanel: () => void;
}
const Overlay = ({ isActive, togglePanel }: Props) => {
  return (
    <>
      <div
        className={`absolute top-0 left-1/2 w-1/2 h-full overflow-hidden ${
          isActive ? "translate-x-0" : "-translate-x-full"
        } transition-transform duration-[600ms] ease-in-out z-10`}
      >
        <div
          className={`relative -left-full h-full w-[200%] transition-transform duration-[600ms] ease-in-out ${
            isActive ? "translate-x-0" : "translate-x-1/2"
          } bg-gradient-to-r from-purple-600 via-purple-800 to-purple-900 text-white`}
        >
          <div
            className={`absolute top-0 right-0 flex flex-col justify-center align-center px-20 text-center h-full w-1/2 ${
              isActive ? "translate-x-0" : "translate-x-[20%]"
            } transition-transform duration-[600ms] ease-in-out`}
          >
            <h1 className="text-white text-2xl font-bold">Hello, Friend!</h1>
            <p className="text-white text-sm">Enter your personal details and start your journey with us</p>
            <button onClick={togglePanel} className="mt-4 px-6 py-2 text-white uppercase bg-transparent border border-white rounded-full">
              Login
            </button>
          </div>
          <div
            className={`absolute top-0 flex flex-col justify-center align-center px-20 text-center h-full w-1/2 ${
              isActive ? "translate-x-[-20%]" : "translate-x-0"
            } transition-transform duration-[600ms] ease-in-out`}
          >
            <h1 className="text-white text-2xl font-bold">Welcome Back!</h1>
            <p className="text-white text-sm">To keep connected with us, please login with your personal info</p>
            <button onClick={togglePanel} className="mt-4 px-6 py-2 text-white uppercase bg-transparent border border-white rounded-full">
              Sign Up
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default Overlay;
