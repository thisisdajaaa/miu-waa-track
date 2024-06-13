import { FC } from "react";
import { Route, Routes } from "react-router-dom";

import Login from "@/pages/Login";

const AuthenticationRoutes: FC = () => {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
    </Routes>
  );
};

export default AuthenticationRoutes;
