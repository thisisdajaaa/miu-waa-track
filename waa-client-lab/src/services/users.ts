import { ApiResponse } from "@/types/server/config";
import { UserDetailResponse } from "@/types/server/user";
import { onParseResponse } from "@/utils/helpers";

export const getCurrentLoggedUserAPI = async (): Promise<
  ApiResponse<UserDetailResponse>
> => {
  const response = await onParseResponse<UserDetailResponse>({
    method: "get",
    url: "/users/me",
  });

  return response;
};
