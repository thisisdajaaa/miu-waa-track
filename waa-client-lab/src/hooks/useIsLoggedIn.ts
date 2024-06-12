import { selectors } from "@/redux/authentication";

import useAppSelector from "./useAppSelector";

const useIsLoggedIn = (): boolean => {
  const accessToken = useAppSelector(selectors.accessToken);

  return !!accessToken;
};

export default useIsLoggedIn;
