import { InputHTMLAttributes } from "react";

export type InputProps = {
  inputClassName?: string;
  containerClassName?: string;
  errorMessage?: string;
} & InputHTMLAttributes<HTMLInputElement>;
