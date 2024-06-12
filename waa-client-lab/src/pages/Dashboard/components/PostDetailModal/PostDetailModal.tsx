import { forwardRef, useCallback, useEffect, useState } from "react";
import { FaThumbsUp, FaCommentAlt, FaShareSquare } from "react-icons/fa";
import mockAvatar from "@/assets/images/mock-avatar.jpg";
import { getPostAPI } from "@/services/posts";
import toast from "react-hot-toast";
import Modal from "@/components/Modal";
import Input from "@/components/Input";
import Button from "@/components/Button";
import type { IPost } from "../../types";
import type { PostDetailModalProps } from "./types";

const PostDetailModal = forwardRef<HTMLDialogElement, PostDetailModalProps>(
  (props, ref) => {
    const { selectedPost, handleClose } = props;

    const [details, setDetails] = useState<IPost | null>(null);
    const [newComment, setNewComment] = useState("");
    const [allComments, setAllComments] = useState<
      { author: string; text: string }[]
    >([
      {
        author: "asd",
        text: "teasdasdasdasdasdas",
      },
      {
        author: "asd",
        text: "teasdasdasdasdasdas",
      },
      {
        author: "asd",
        text: "teasdasdasdasdasdas",
      },
    ]);

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
      //   setAllComments(data?.comments || []);
    }, [selectedPost]);

    useEffect(() => {
      handleLoad();
    }, [handleLoad]);

    const handleAddComment = () => {
      if (newComment.trim()) {
        setAllComments([
          ...allComments,
          { author: "Current User", text: newComment },
        ]);
        setNewComment("");
      }
    };

    return (
      <Modal
        ref={ref}
        handleClose={handleClose}
        bodyClassname="w-11/12 max-w-3xl"
      >
        <div className="flex flex-col gap-3 px-4 py-2 transition-all duration-200 ease-in-out">
          <div className="flex items-center gap-3">
            <img
              src={mockAvatar}
              alt={`${details?.author}'s avatar`}
              className="w-12 h-12 rounded-full"
            />
            <div>
              <p className="font-bold text-lg">{details?.author}</p>
              <p className="text-sm text-gray-500">
                {new Date().toLocaleString()}
              </p>
            </div>
          </div>
          <h3 className="font-semibold">{details?.title}</h3>
          <p>{details?.content}</p>
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
          <div className="mt-4">
            {allComments.map((comment, index) => (
              <div key={index} className="flex items-start gap-3 mt-3">
                <img
                  src={mockAvatar}
                  alt={`${comment.author}'s avatar`}
                  className="w-8 h-8 rounded-full"
                />
                <div className="flex flex-col bg-gray-100 p-3 rounded-lg shadow-sm">
                  <p className="font-bold">{comment.author}</p>
                  <p>{comment.text}</p>
                </div>
              </div>
            ))}
          </div>
          <div className="mt-4 flex items-center gap-3">
            <img
              src={mockAvatar}
              alt="Your avatar"
              className="w-8 h-8 rounded-full"
            />
            <Input
              type="text"
              placeholder="Write a comment..."
              inputClassname="rounded-full"
              value={newComment}
              onChange={(e) => setNewComment(e.target.value)}
            />
            <Button variant="secondary" onClick={handleAddComment}>
              Post
            </Button>
          </div>
        </div>
      </Modal>
    );
  }
);

export default PostDetailModal;
