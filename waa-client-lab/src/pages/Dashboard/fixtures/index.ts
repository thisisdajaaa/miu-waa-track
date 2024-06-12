import { IPost, PostForm } from "../types";

export const mockPostList: IPost[] = [
  {
    id: 111,
    title: "Gotta catch 'em all",
    author: "Ash Ketchum",
    content:
      "Lorem ipsum dolor sit amet consectetur adipisicing elit. A esse cumque quas voluptates architecto deleniti illum id, eius voluptatibus minima debitis amet doloribus, recusandae molestias? Quis, iste? Deleniti voluptas accusantium ullam nesciunt placeat delectus veritatis explicabo dolore nam laborum aspernatur magnam dolores veniam minima fugit, culpa.",
  },
  {
    id: 112,
    title: "Watch me cook Krabby Patty",
    author: "Spongebob Squarepants",
    content:
      "Lorem ipsum dolor sit amet consectetur adipisicing elit. A esse cumque quas voluptates architecto deleniti illum id, eius voluptatibus minima debitis amet doloribus, recusandae molestias? Quis, iste? Deleniti voluptas accusantium ullam nesciunt placeat delectus veritatis explicabo dolore nam laborum aspernatur magnam dolores veniam minima fugit, culpa.",
  },
  {
    id: 113,
    title: "I want to be a Hokage!",
    author: "Naruto Uzumaki",
    content:
      "Lorem ipsum dolor sit amet consectetur adipisicing elit. A esse cumque quas voluptates architecto deleniti illum id, eius voluptatibus minima debitis amet doloribus, recusandae molestias? Quis, iste? Deleniti voluptas accusantium ullam nesciunt placeat delectus veritatis explicabo dolore nam laborum aspernatur magnam dolores veniam minima fugit, culpa.",
  },
];

export const initialPostForm: PostForm = {
  title: "",
  content: "",
};
