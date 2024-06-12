import { onParseResponse } from "@/utils/helpers";

import type { ApiResponse } from "@/types/server/config";
import type { PostDetailResponse, PostFormRequest } from "@/types/server/post";

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

export const addPostAPI = async (
  data: PostFormRequest
): Promise<ApiResponse<PostDetailResponse>> => {
  const response = await onParseResponse<PostDetailResponse>({
    method: "post",
    url: `/posts`,
    data,
  });

  return response;
};

export const deletePostAPI = async (
  id: number
): Promise<ApiResponse<unknown>> => {
  const response = await onParseResponse<unknown>({
    method: "delete",
    url: `/posts/${id}`,
  });

  return response;
};
