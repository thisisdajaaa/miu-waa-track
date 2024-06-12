import { forwardRef, useCallback, useEffect, useState } from "react";
import toast from "react-hot-toast";
import { FaCommentAlt, FaShareSquare, FaThumbsUp } from "react-icons/fa";

import { useAppSelector } from "@/hooks";

import Button from "@/components/Button";
import Input from "@/components/Input";
import Modal from "@/components/Modal";

import { selectors } from "@/redux/authentication";

import { getPostAPI } from "@/services/posts";

import { CommentDetailResponse } from "@/types/server/comment";

import mockAvatar from "@/assets/images/mock-avatar.jpg";

import type { PostDetailModalProps } from "./types";
import type { IPost } from "../../types";

const PostDetailModal = forwardRef<HTMLDialogElement, PostDetailModalProps>(
  (props, ref) => {
    const { selectedPost, handleClose, handleDeletePost } = props;

    const [details, setDetails] = useState<IPost | null>(null);
    const [newComment, setNewComment] = useState("");
    const [allComments, setAllComments] = useState<CommentDetailResponse[]>([]);

    const userDetails = useAppSelector(selectors.userDetails);

    const handleLoad = useCallback(async () => {
      const { success, data, message } = await getPostAPI(selectedPost);

      if (!success) {
        toast.error(message as string);
        return;
      }

      const formattedPost: IPost = {
        id: data?.id as number,
        title: data?.title || "",
        content: data?.content || "",
        author: data?.author?.name || "",
        createdAt: data?.createdAt || "",
        createdBy: data?.createdBy || "",
        comments: data?.comments || [],
      };

      setDetails(formattedPost);
      setAllComments(data?.comments || []);
    }, [selectedPost]);

    useEffect(() => {
      handleLoad();
    }, [handleLoad]);

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
                {new Date(details?.createdAt as string).toLocaleString()}
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

            {userDetails.name === details?.createdBy && (
              <>
                <Button variant="ghost" className="text-primary">
                  <FaCommentAlt className="mr-2" /> Edit
                </Button>
                <Button
                  variant="ghost"
                  className="text-red-500"
                  onClick={() => handleDeletePost()}
                >
                  <FaCommentAlt className="mr-2" /> Delete
                </Button>
              </>
            )}
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
                  <p className="font-bold">{comment.createdBy}</p>
                  <p>{comment.content}</p>
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
            <Button variant="secondary">Post</Button>
          </div>
        </div>
      </Modal>
    );
  }
);

export default PostDetailModal;
