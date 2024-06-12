import { FC, PropsWithChildren, useCallback, useEffect } from "react";
import toast from "react-hot-toast";

import { useAppDispatch, useIsLoggedIn } from "@/hooks";

import { actions } from "@/redux/authentication";

import { getCurrentLoggedUserAPI } from "@/services/users";

import { UserDetailResponse } from "@/types/server/user";

import Header from "../Header";

const Layout: FC<PropsWithChildren> = (props) => {
  const { children } = props;

  const dispatch = useAppDispatch();

  const isLoggedIn = useIsLoggedIn();

  const handleLoad = useCallback(async () => {
    const { success, data, message } = await getCurrentLoggedUserAPI();

    if (!success) {
      toast.error(message as string);
      return;
    }

    dispatch(actions.callSetUserDetails(data as UserDetailResponse));
  }, [dispatch]);

  useEffect(() => {
    if (isLoggedIn) handleLoad();
  }, [handleLoad, isLoggedIn]);

  if (!isLoggedIn)
    return (
      <div
        className="relative flex min-h-screen items-center justify-center py-12 px-4"
        data-testid="layout-unauthenticated"
      >
        {children}
      </div>
    );

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
