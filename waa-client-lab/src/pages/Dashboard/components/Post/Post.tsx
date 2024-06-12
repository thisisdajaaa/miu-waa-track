import { FC } from "react";
import { FaThumbsUp, FaCommentAlt, FaShareSquare } from "react-icons/fa";
import mockAvatar from "@/assets/images/mock-avatar.jpg";
import type { PostProps } from "./types";
import Button from "@/components/Button";

const Post: FC<PostProps> = (props) => {
  const { details, onSelectPost } = props;
  const { id, title, author } = details;

  const handleSelectPost = () => onSelectPost(id);

  return (
    <div
      onClick={handleSelectPost}
      className="flex flex-col gap-4 border rounded-lg p-6 shadow-md hover:cursor-pointer hover:bg-gray-50 transition-all duration-200 ease-in-out"
    >
      <div className="flex items-center gap-3">
        <img
          src={mockAvatar}
          alt={`${author}'s avatar`}
          className="w-12 h-12 rounded-full"
        />
        <div>
          <p className="font-bold text-lg">{author}</p>
          <p className="text-sm text-gray-500">{new Date().toLocaleString()}</p>
        </div>
      </div>
      <div className="mt-4">
        <p className="font-bold text-xl">{title}</p>
      </div>
      <div className="flex justify-around mt-4">
        <Button variant="ghost">
          <FaThumbsUp className="mr-2" /> Like
        </Button>
        <Button variant="ghost">
          <FaCommentAlt className="mr-2" /> Comment
        </Button>
        <Button variant="ghost">
          <FaShareSquare className="mr-2" /> Share
        </Button>
      </div>
    </div>
  );
};

export default Post;
