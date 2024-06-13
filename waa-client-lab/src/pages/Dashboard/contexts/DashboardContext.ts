import { createContext } from "react";

import type { DashboardContextProps } from "../types";

export const DashboardContext = createContext<
  DashboardContextProps | undefined
>(undefined);
