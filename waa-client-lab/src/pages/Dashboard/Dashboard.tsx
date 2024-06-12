import { FC, useState, useCallback, useEffect, useRef } from "react";
import Posts from "./components/Posts";
import type { IPost, PostForm } from "./types";
import { getPostsAPI } from "@/services/posts";
import toast from "react-hot-toast";
import Loading from "@/components/Loading";
import Button from "@/components/Button";
import PostFormModal from "./components/PostFormModal";
import PostDetailModal from "./components/PostDetailModal";
import { FormikContext, useFormik } from "formik";
import { initialPostForm } from "./fixtures";

const DashboardPage: FC = () => {
  const [posts, setPosts] = useState<IPost[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [selectedPost, setSelectedPost] = useState<number | null>(null);

  const postFormModalRef = useRef<HTMLDialogElement | null>(null);
  const postDetailModalRef = useRef<HTMLDialogElement | null>(null);

  const handleLoad = useCallback(async () => {
    const { success, data, formattedError } = await getPostsAPI();

    setIsLoading(false);

    if (!success) {
      toast.error(formattedError as string);
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
    console.log(values);
  };

  const formikBag = useFormik<PostForm>({
    initialValues: initialPostForm,
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
    postFormModalRef.current?.showModal();
  }, []);

  const handleClosePostFormModal = useCallback(() => {
    postFormModalRef.current?.close();
  }, []);

  const handleClosePostDetailModal = useCallback(() => {
    postDetailModalRef.current?.close();
  }, []);

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
