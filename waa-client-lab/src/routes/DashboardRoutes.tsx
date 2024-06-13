import { FC, lazy, Suspense } from "react";
import { Route, Routes } from "react-router-dom";

import Loading from "@/components/Loading";
import { DashboardProvider } from "@/pages/Dashboard/contexts/DashboardProvider";

const Dashboard = lazy(() => import("@/pages/Dashboard"));

const DashboardRoutes: FC = () => {
  return (
    <Suspense fallback={<Loading />}>
      <Routes>
        <Route
          path="/"
          element={
            <DashboardProvider>
              <Dashboard />
            </DashboardProvider>
          }
        />
      </Routes>
    </Suspense>
  );
};

export default DashboardRoutes;
