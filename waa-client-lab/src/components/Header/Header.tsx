import { FC } from "react";

const Header: FC = () => {
  return (
    <nav className="fixed z-10 flex h-[4.5rem] w-full justify-between bg-white px-6 shadow-md">
      <div className="flex items-center">
        <h1 className="ml-3 text-[1.2rem] sm:text-[1.25rem]">CS545</h1>
      </div>
    </nav>
  );
};

export default Header;
