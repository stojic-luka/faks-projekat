import { BrowserRouter as Router, Routes, Route, Outlet, Navigate } from "react-router-dom";
import { Navbar } from "./components/shared";
import { useAuth } from "./contexts/useAuth";
import { lazy, Suspense } from "react";

const HomePageLazy = lazy(() => import("./pages/home"));
const SearchPageLazy = lazy(() => import("./pages/search"));
const ToolsPageLazy = lazy(() => import("./pages/tools"));
const AdminPageLazy = lazy(() => import("./pages/admin"));
const SignInPageLazy = lazy(() => import("./pages/signIn"));
const NotFoundPageLazy = lazy(() => import("./pages/notFound"));

const Layout = () => {
  return (
    <>
      <Navbar />
      <div className="flex min-h-[calc(100%-48px)] w-full">
        <Outlet />
      </div>
    </>
  );
};

const App = () => {
  const { isAdmin, isAuthenticated } = useAuth();

  return (
    <div className="flex flex-col h-full w-full dark:bg-[#212121]">
      <Suspense fallback={<div>Loading...</div>}>
        <Router>
          <Routes>
            <Route path="/signin" element={!isAuthenticated ? <SignInPageLazy /> : <Navigate to="/" replace />} />
            <Route element={isAuthenticated ? <Layout /> : <Navigate to="/signin" replace />}>
              <Route index element={<HomePageLazy />} />
              <Route path="search" element={<SearchPageLazy />} />
              <Route path="tools" element={<ToolsPageLazy />} />
              <Route path="admin" element={isAdmin ? <AdminPageLazy /> : <Navigate to={"/404"} replace />} />
              <Route path="404" element={<NotFoundPageLazy />} />
              <Route path="*" element={<Navigate to={"/404"} replace />} />
            </Route>
            <Route path="*" element={<Navigate to={"/signin"} replace />} />
          </Routes>
        </Router>
      </Suspense>
    </div>
  );
};

export default App;
