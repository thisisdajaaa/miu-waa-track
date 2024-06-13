import { FormikContext } from "formik";
import { forwardRef, useRef } from "react";

import Button from "@/components/Button";
import FormInput from "@/components/Formik/FormInput";
import FormTextArea from "@/components/Formik/FormTextArea";
import Modal from "@/components/Modal";

import type { PostFormModalProps } from "./types";

const PostFormModal = forwardRef<HTMLDialogElement, PostFormModalProps>(
  (props, ref) => {
    const { handleClose } = props;
    const formRef = useRef<HTMLFormElement>(null);

    const handleSubmit = () => {
      if (formRef.current) formRef.current.requestSubmit();
    };

    return (
      <Modal
        ref={ref}
        handleClose={handleClose}
        bodyClassname="w-11/12 max-w-7xl"
      >
        <h2 className="mb-6 text-lg font-bold uppercase">Add Post</h2>

        <FormikContext.Consumer>
          {(formik) => (
            <form ref={formRef} onSubmit={formik.handleSubmit}>
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
                    onClick={handleSubmit}
                    className="w-3/12"
                    isLoading={formik.isSubmitting}
                  >
                    Save
                  </Button>
                </div>
              </div>
            </form>
          )}
        </FormikContext.Consumer>
      </Modal>
    );
  }
);

export default PostFormModal;
