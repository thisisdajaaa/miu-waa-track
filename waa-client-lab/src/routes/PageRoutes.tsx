import { FC, lazy, Suspense } from "react";
import { Navigate, Route, Routes } from "react-router-dom";

import Loading from "@/components/Loading";

const AuthenticationRoutes = lazy(() => import("./AuthenticationRoutes"));
const DashboardRoutes = lazy(() => import("./DashboardRoutes"));
const NotFoundPage = lazy(() => import("../pages/NotFound"));

const PageRoutes: FC = () => {
  return (
    <Suspense fallback={<Loading />}>
      <Routes>
        <Route path="/" element={<Navigate to="/dashboard" />} />
        <Route path="/dashboard/*" element={<DashboardRoutes />} />
        <Route path="/auth/*" element={<AuthenticationRoutes />} />
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </Suspense>
  );
};

export default PageRoutes;
