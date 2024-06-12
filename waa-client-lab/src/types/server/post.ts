import type { CommentDetailResponse } from "./comment";
import type { UserDetailResponse } from "./user";

export type PostDetailResponse = {
  id: number;
  title: string;
  content: string;
  author: UserDetailResponse;
  comments: CommentDetailResponse[];
  createdAt: string;
  createdBy: string;
};

export type PostFormRequest = {
  title: string;
  content: string;
};
