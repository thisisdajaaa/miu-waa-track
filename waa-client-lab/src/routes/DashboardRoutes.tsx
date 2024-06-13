import { FC } from "react";
import { Route, Routes } from "react-router-dom";

import ProtectedRoute from "@/components/ProtectedRoute";
import Dashboard from "@/pages/Dashboard";
import { DashboardProvider } from "@/pages/Dashboard/contexts/DashboardProvider";

const DashboardRoutes: FC = () => {
  return (
    <Routes>
      <Route
        path="/"
        element={
          <ProtectedRoute
            element={
              <DashboardProvider>
                <Dashboard />
              </DashboardProvider>
            }
          />
        }
      />
    </Routes>
  );
};

export default DashboardRoutes;
