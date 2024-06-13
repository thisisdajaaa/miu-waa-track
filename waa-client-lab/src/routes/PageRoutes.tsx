import { FC } from "react";
import { Navigate, Route, Routes } from "react-router-dom";

import AuthenticationRoutes from "./AuthenticationRoutes";
import DashboardRoutes from "./DashboardRoutes";

const PageRoutes: FC = () => {
  return (
    <Routes>
      <Route path="/*" element={<DashboardRoutes />} />
      <Route path="/auth/*" element={<AuthenticationRoutes />} />
      <Route path="*" element={<Navigate to="/" />} />
    </Routes>
  );
};

export default PageRoutes;
