import { IPost } from "../../../types";

export type PostsProps = {
  items: IPost[];
  onSelectPost: (postId: number) => void;
};
