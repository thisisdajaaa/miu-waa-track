import type { UserDetailResponse } from "./user";

export type PostDetailResponse = {
  id: number;
  title: string;
  content: string;
  author: UserDetailResponse;
};
