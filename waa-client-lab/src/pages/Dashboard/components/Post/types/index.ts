import type { IPost } from "../../../types";

export type PostProps = {
  details: IPost;
  onSelectPost: (postId: number) => void;
};
