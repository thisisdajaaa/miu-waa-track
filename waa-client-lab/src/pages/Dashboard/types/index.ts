export interface IPost {
  id: number;
  title: string;
  author: string;
  content?: string;
}

export type PostForm = {
  title: string;
  content: string;
};
