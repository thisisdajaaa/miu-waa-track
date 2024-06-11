import { FC } from "react";
import { Navigate } from "react-router-dom";
import { ProtectedRouteProps } from "./types";
import { useIsLoggedIn } from "@/hooks";

const ProtectedRoute: FC<ProtectedRouteProps> = ({ element }) => {
  const isLoggedIn = useIsLoggedIn();

  if (isLoggedIn) return element;

  return <Navigate to="/login" />;
};

export default ProtectedRoute;
