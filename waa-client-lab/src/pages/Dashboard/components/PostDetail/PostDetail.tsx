import { FC, useCallback, useEffect, useState } from "react";
import type { PostDetailProps } from "./types";
import Button from "@/components/Button";
import { getPostAPI } from "@/services/posts";
import toast from "react-hot-toast";
import type { IPost } from "../../types";

const PostDetail: FC<PostDetailProps> = (props) => {
  const { selectedPost } = props;

  const [details, setDetails] = useState<IPost | null>(null);

  const handleLoad = useCallback(async () => {
    const { success, data, formattedError } = await getPostAPI(selectedPost);

    if (!success) {
      toast.error(formattedError as string);
      return;
    }

    const formattedPost: IPost = {
      id: data?.id as number,
      title: data?.title || "",
      content: data?.content || "",
      author: data?.author?.name || "",
    };

    setDetails(formattedPost);
  }, [selectedPost]);

  useEffect(() => {
    handleLoad();
  }, [handleLoad]);

  return (
    <div className="flex flex-col gap-3 border px-4 py-2 shadow-md hover:bg-gray-50 transition-all duration-200 ease-in-out">
      <h3 className="text-center underline font-semibold">{details?.title}</h3>

      <p className="text-center">{details?.author}</p>

      <p>{details?.content}</p>

      <div className="flex items-center justify-center gap-6">
        <Button variant="secondary">Edit</Button>
        <Button variant="danger">Delete</Button>
      </div>
    </div>
  );
};

export default PostDetail;
