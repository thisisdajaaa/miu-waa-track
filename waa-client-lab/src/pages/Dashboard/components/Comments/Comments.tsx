import { FC } from "react";

import mockAvatar from "@/assets/images/mock-avatar.jpg";

import { CommentsProps } from "./types";

const Comments: FC<CommentsProps> = (props) => {
  const { items } = props;

  return (
    <div className="mt-4">
      {items.map((comment, index) => (
        <div key={index} className="flex items-start gap-3 mt-3">
          <img
            src={mockAvatar}
            alt={`${comment.author}'s avatar`}
            className="w-8 h-8 rounded-full"
          />
          <div className="flex flex-col bg-gray-100 p-3 rounded-lg shadow-sm">
            <p className="font-bold">{comment.createdBy}</p>
            <p>{comment.content}</p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default Comments;
