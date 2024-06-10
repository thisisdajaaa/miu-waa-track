import { FC } from "react";
import type { PostDetailProps } from "./types";
import Button from "@/components/Button";
import { ButtonVariant } from "@/components/Button/config";

const PostDetail: FC<PostDetailProps> = (props) => {
  const { author, title, content } = props;

  return (
    <div className="flex flex-col gap-3 border px-4 py-2 shadow-md hover:cursor-pointer hover:bg-gray-50 transition-all duration-200 ease-in-out">
      <h3 className="text-center underline font-semibold">{title}</h3>

      <p className="text-center">{author}</p>

      <p>{content}</p>

      <div className="flex items-center justify-center gap-6">
        <Button variant={ButtonVariant.Secondary}>Edit</Button>
        <Button variant={ButtonVariant.Danger}>Delete</Button>
      </div>
    </div>
  );
};

export default PostDetail;
