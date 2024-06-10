import { ComponentPropsWithRef } from "react";
import { ButtonVariant } from "../config";

export type ButtonProps = {
  isLoading?: boolean;
  variant?: keyof typeof ButtonVariant;
} & ComponentPropsWithRef<"button">;
