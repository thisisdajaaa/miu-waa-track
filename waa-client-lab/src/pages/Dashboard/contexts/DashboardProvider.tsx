import { FC, PropsWithChildren, useState } from "react";

import { DashboardContext } from "./DashboardContext";

export const DashboardProvider: FC<PropsWithChildren> = (props) => {
  const { children } = props;

  const [selectedPost, setSelectedPost] = useState<number | null>(null);

  return (
    <DashboardContext.Provider value={{ selectedPost, setSelectedPost }}>
      {children}
    </DashboardContext.Provider>
  );
};
