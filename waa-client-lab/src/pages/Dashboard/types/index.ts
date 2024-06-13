import type { CommentDetailResponse } from "@/types/server/comment";

export interface IPost {
  id: number;
  title: string;
  author: string;
  content?: string;
  createdAt?: string;
  createdBy?: string;
  comments: CommentDetailResponse[];
}

export type PostForm = {
  title: string;
  content: string;
};

export type DashboardContextProps = {
  selectedPost: number | null;
  setSelectedPost: (id: number) => void;
};
