import * as Yup from "yup";

export const PostFormValidationSchema = Yup.object().shape({
  title: Yup.string().label("Title").required().min(4).max(50),
  content: Yup.string().label("Description").required().min(4).max(255),
});
