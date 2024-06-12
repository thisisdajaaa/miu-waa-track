import type { UserDetailResponse } from "./user";

export type CommentDetailResponse = {
  id: number;
  postId: number;
  content: string;
  author: UserDetailResponse;
  createdAt?: string;
  createdBy?: string;
};
