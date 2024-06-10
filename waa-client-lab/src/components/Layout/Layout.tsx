import { FC, PropsWithChildren } from "react";
import Header from "../Header";

const Layout: FC<PropsWithChildren> = (props) => {
  const { children } = props;

  return (
    <div className="flex min-h-screen flex-col">
      <Header />

      <div className="flex flex-1 flex-col md:flex-row">
        <main className="flex-1 overflow-x-auto transition-all duration-200 ease-in-out">
          <div className="m-auto flex max-w-screen-2xl flex-col py-[6rem] px-9">
            {children}
          </div>
        </main>
      </div>
    </div>
  );
};

export default Layout;
