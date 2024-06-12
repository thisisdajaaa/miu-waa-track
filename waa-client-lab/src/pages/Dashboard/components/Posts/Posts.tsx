import { FC } from "react";
import Post from "../Post";
import type { PostsProps } from "./types";

const Posts: FC<PostsProps> = (props) => {
  const { items, onSelectPost } = props;

  return (
    <div className="flex flex-col gap-6">
      {items.map((item) => (
        <Post key={item.id} details={item} onSelectPost={onSelectPost} />
      ))}
    </div>
  );
};

export default Posts;
