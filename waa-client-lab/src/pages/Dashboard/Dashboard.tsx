import { FormikContext, useFormik } from "formik";
import { FC, useCallback, useEffect, useRef, useState } from "react";
import toast from "react-hot-toast";

import Button from "@/components/Button";
import Loading from "@/components/Loading";

import { addPostAPI, deletePostAPI, getPostsAPI } from "@/services/posts";

import PostDetailModal from "./components/PostDetailModal";
import PostFormModal from "./components/PostFormModal";
import Posts from "./components/Posts";
import { initialPostForm } from "./fixtures";
import { useDashboard } from "./hooks/useDashboard";
import type { IPost, PostForm } from "./types";
import { PostFormValidationSchema } from "./validations";

const DashboardPage: FC = () => {
  const [posts, setPosts] = useState<IPost[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const { selectedPost, setSelectedPost } = useDashboard();

  const postFormModalRef = useRef<HTMLDialogElement | null>(null);
  const postDetailModalRef = useRef<HTMLDialogElement | null>(null);

  const handleLoad = useCallback(async () => {
    const { success, data, message } = await getPostsAPI();

    setIsLoading(false);

    if (!success) {
      toast.error(message as string);
      return;
    }

    const transformedPosts = data?.map((item) => ({
      ...item,
      author: item.author.name,
    })) as IPost[];

    setPosts(transformedPosts);
  }, []);

  useEffect(() => {
    handleLoad();
  }, [handleLoad]);

  const handleSubmit = async (values: PostForm) => {
    const { success, message } = await addPostAPI(values);

    if (!success) {
      toast.error(message as string);
      return;
    }

    toast.success(message as string);

    handleClosePostFormModal();
    handleLoad();
  };

  const formikBag = useFormik<PostForm>({
    initialValues: initialPostForm,
    validationSchema: PostFormValidationSchema,
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    onSubmit: handleSubmit,
  });

  const handleSelectPost = (postId: number) => {
    setSelectedPost(postId);
    postDetailModalRef.current?.showModal();
  };

  const handleShowPostFormModal = useCallback(() => {
    formikBag.resetForm();
    postFormModalRef.current?.showModal();
  }, [formikBag]);

  const handleClosePostFormModal = useCallback(() => {
    postFormModalRef.current?.close();
  }, []);

  const handleClosePostDetailModal = useCallback(() => {
    postDetailModalRef.current?.close();
  }, []);

  const handleDeletePost = useCallback(async () => {
    const { status } = await deletePostAPI(selectedPost as number);

    if (status !== 204) {
      toast.error("Something went wrong!");
      return;
    }

    toast.success("Successfully deleted the post!");

    handleClosePostDetailModal();
    handleLoad();
  }, [handleClosePostDetailModal, handleLoad, selectedPost]);

  return (
    <section>
      <div className="flex justify-end">
        <Button onClick={handleShowPostFormModal}>Add Post</Button>
      </div>

      <FormikContext.Provider value={formikBag}>
        <PostFormModal
          ref={postFormModalRef}
          handleClose={handleClosePostFormModal}
        />
      </FormikContext.Provider>

      {selectedPost && (
        <PostDetailModal
          ref={postDetailModalRef}
          selectedPost={selectedPost as number}
          handleClose={handleClosePostDetailModal}
          handleDeletePost={handleDeletePost}
        />
      )}

      {isLoading ? (
        <div className="flex justify-center mt-10">
          <Loading fullscreen={false} />
        </div>
      ) : (
        <div className="mt-8 flex flex-col gap-6">
          <Posts items={posts} onSelectPost={handleSelectPost} />
        </div>
      )}
    </section>
  );
};

export default DashboardPage;
