import { FC } from "react";
import { Navigate } from "react-router-dom";

import { useIsLoggedIn } from "@/hooks";

import { ProtectedRouteProps } from "./types";

const ProtectedRoute: FC<ProtectedRouteProps> = ({ element }) => {
  const isLoggedIn = useIsLoggedIn();

  if (isLoggedIn) return element;

  return <Navigate to="/auth/login" />;
};

export default ProtectedRoute;
