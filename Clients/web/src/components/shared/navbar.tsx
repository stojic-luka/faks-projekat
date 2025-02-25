import { useEffect, useRef } from "react";
import { NavLink } from "react-router-dom";

const NAVBAR_BUTTON_WIDTH = 64; // in px
const NAVBAR_BUTTON_GAP = 20; // in px

const Navbar = () => {
  const mainNavbarRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    mainNavbarRef.current?.style.setProperty("--nav-button-width", `${NAVBAR_BUTTON_WIDTH}px`);
    mainNavbarRef.current?.style.setProperty("--nav-button-gap", `${NAVBAR_BUTTON_GAP}px`);
  }, []);

  return (
    <div className="z-50 bg-neutral-900 w-full text-gray-200">
      <div className="flex max-w-[1024px] w-full mx-auto h-12">
        <div className="flex w-full flex-row justify-between my-auto">
          <div className="my-auto">
            <span className="font-bold text-lg">AugmentedCooking</span>
          </div>
          <nav ref={mainNavbarRef} id="main-navbar" className="relative my-auto">
            <ul className="relative flex text-sm my-auto rounded-xl gap-[var(--nav-button-gap)] [&>li]:z-[1]">
              <li className="flex h-6 w-[var(--nav-button-width)]">
                <NavLink to="/" className="m-auto" draggable="false">
                  Home
                </NavLink>
              </li>
              <li className="flex h-6 w-[var(--nav-button-width)]">
                <NavLink to="/search" className="m-auto" draggable="false">
                  Search
                </NavLink>
              </li>
              <li className="flex h-6 w-[var(--nav-button-width)]">
                <NavLink to="/tools" className="m-auto" draggable="false">
                  Tools
                </NavLink>
              </li>
            </ul>
            <div className="navbar-follow-line absolute bottom-0 h-6 w-[var(--nav-button-width)] bg-blue-600 transition-[left] rounded-lg ease-in-out duration-200" />
          </nav>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
