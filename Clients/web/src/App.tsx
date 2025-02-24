import { BrowserRouter as Router, Routes, Route, Outlet, Navigate } from "react-router-dom";
import { Navbar } from "./components/shared";
import { useAuth } from "./contexts/useAuth";
import { lazy, Suspense } from "react";

const HomePageLazy = lazy(() => import("./pages/home"));
const SearchPageLazy = lazy(() => import("./pages/search"));
const ToolsPageLazy = lazy(() => import("./pages/tools"));
const SignInPageLazy = lazy(() => import("./pages/signIn"));

const Layout = () => {
  return (
    <>
      <Navbar />
      <div className="flex h-[calc(100%-48px)] w-full">
        <Outlet />
      </div>
    </>
  );
};

const App = () => {
  const { isAuthenticated } = useAuth();

  return (
    <>
      <div className="flex flex-col h-full w-full">
        <Suspense fallback={<div>Loading...</div>}>
          <Router>
            <Routes>
              <Route path="/signin" element={isAuthenticated ? <Navigate to="/" replace /> : <SignInPageLazy />} />
              <Route element={isAuthenticated ? <Outlet /> : <SignInPageLazy />}>
                <Route element={<Layout />}>
                  <Route path="/" element={<HomePageLazy />} />
                  <Route path="/search" element={<SearchPageLazy />} />
                  <Route path="/tools" element={<ToolsPageLazy />} />
                </Route>
              </Route>
            </Routes>
          </Router>
        </Suspense>
      </div>
    </>
  );
};

export default App;
