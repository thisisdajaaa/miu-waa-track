import type { ApiResponse } from "@/types/server/config";
import { PostDetailResponse } from "@/types/server/post";
import { onParseResponse } from "@/utils/helpers";

export const getPostsAPI = async (): Promise<
  ApiResponse<PostDetailResponse[]>
> => {
  const response = await onParseResponse<PostDetailResponse[]>({
    method: "get",
    url: "/posts",
  });

  return response;
};

export const getPostAPI = async (
  id: number
): Promise<ApiResponse<PostDetailResponse>> => {
  const response = await onParseResponse<PostDetailResponse>({
    method: "get",
    url: `/posts/${id}`,
  });

  return response;
};
