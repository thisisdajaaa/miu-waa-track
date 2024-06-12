import { forwardRef } from "react";
import { PostFormModalProps } from "./types";
import { PostForm } from "../../types";
import Modal from "@/components/Modal";
import FormInput from "@/components/Formik/FormInput";
import Button from "@/components/Button";
import { useFormikContext } from "formik";
import FormTextArea from "@/components/Formik/FormTextArea";

const PostFormModal = forwardRef<HTMLDialogElement, PostFormModalProps>(
  (props, ref) => {
    const { handleClose } = props;

    const { submitForm, isSubmitting } = useFormikContext<PostForm>();

    return (
      <Modal
        ref={ref}
        handleClose={handleClose}
        bodyClassname="w-11/12 max-w-7xl"
      >
        <h2 className="mb-6 text-lg font-bold uppercase">Add Post</h2>

        <div className="grid gap-7">
          <FormInput
            name="title"
            type="text"
            label="Title"
            placeholder="Enter Title"
            isRequired
          />

          <FormTextArea
            name="content"
            label="Description"
            isRequired
            placeholder="Enter Description"
          />

          <div className="mt-14 flex justify-end gap-6">
            <Button
              type="button"
              variant="danger"
              onClick={handleClose}
              className="w-3/12"
            >
              Cancel
            </Button>

            <Button
              type="button"
              onClick={submitForm}
              className="w-3/12"
              isLoading={isSubmitting}
            >
              Save
            </Button>
          </div>
        </div>
      </Modal>
    );
  }
);

export default PostFormModal;
