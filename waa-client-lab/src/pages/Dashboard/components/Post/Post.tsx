import { FC } from "react";
import type { PostProps } from "./types";

const Post: FC<PostProps> = (props) => {
  const { details, onSelectPost } = props;
  const { id, title, author } = details;

  const handleSelectPost = () => onSelectPost(id);

  return (
    <div
      onClick={handleSelectPost}
      className="flex flex-col gap-3 border px-4 py-2 shadow-md hover:cursor-pointer hover:bg-gray-50 transition-all duration-200 ease-in-out"
    >
      <p>
        <span className="font-bold">ID: </span>
        {id}
      </p>
      <p>
        <span className="font-bold">Title: </span>
        {title}
      </p>
      <p>
        <span className="font-bold">Author: </span>
        {author}
      </p>
    </div>
  );
};

export default Post;
