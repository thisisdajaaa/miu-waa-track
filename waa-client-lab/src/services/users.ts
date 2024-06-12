import { onParseResponse } from "@/utils/helpers";

import type { ApiResponse } from "@/types/server/config";
import type { UserDetailResponse } from "@/types/server/user";

export const getCurrentLoggedUserAPI = async (): Promise<
  ApiResponse<UserDetailResponse>
> => {
  const response = await onParseResponse<UserDetailResponse>({
    method: "get",
    url: "/users/me",
  });

  return response;
};
