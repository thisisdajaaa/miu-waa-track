import { ChangeEvent, FC, useMemo, useState } from "react";
import Posts from "./components/Posts";
import { mockPostList } from "./fixtures";
import type { IPost } from "./types";
import PostDetail from "./components/PostDetail";
import Input from "@/components/Input";
import Button from "@/components/Button";

const DashboardPage: FC = () => {
  const [posts, setPosts] = useState<IPost[]>(mockPostList);
  const [title, setTitle] = useState<string>(mockPostList[0]?.title || "");
  const [selectedPost, setSelectedPost] = useState<number | null>(null);

  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) =>
    setTitle(event.target.value);

  const handleUpdate = () => {
    if (!!posts.length && Array.isArray(posts) && !!title) {
      setPosts((prev) => {
        return prev.map((item, index) => {
          if (index === 0) {
            return {
              ...item,
              title,
            };
          }

          return item;
        });
      });
    }
  };

  const handleSelectPost = (postId: number) => setSelectedPost(postId);

  const memoizedPost = useMemo(
    () => posts.find(({ id }) => id === selectedPost) as IPost,
    [posts, selectedPost]
  );

  return (
    <section>
      <h2 className="font-bold text-lg">Dashboard</h2>

      <div className="mt-8 flex flex-col gap-14">
        <Posts items={posts} onSelectPost={handleSelectPost} />

        <div className="flex flex-col sm:flex-row sm:items-center gap-4 w-2/4">
          <Input
            placeholder="Enter Title"
            value={title}
            onChange={handleTitleChange}
          />
          <Button className="sm:w-64" onClick={handleUpdate}>
            Change Title
          </Button>
        </div>

        {selectedPost && <PostDetail {...memoizedPost} />}
      </div>
    </section>
  );
};

export default DashboardPage;
