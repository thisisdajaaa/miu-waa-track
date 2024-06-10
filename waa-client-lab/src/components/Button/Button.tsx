import type { ClassValue } from "clsx";
import { forwardRef } from "react";
import { ImSpinner2 } from "react-icons/im";
import type { ButtonProps } from "./types";
import { ButtonVariant } from "./config";
import clsxm from "@/utils/clsxm";

const Button = forwardRef<HTMLButtonElement, ButtonProps>((props, ref) => {
  const {
    children,
    className,
    disabled: isButtonDisabled,
    isLoading,
    variant = ButtonVariant.Primary,
    ...rest
  } = props;

  const disabled = isLoading || isButtonDisabled;

  const variants: ClassValue[] = [
    variant === ButtonVariant.Primary && [
      "bg-blue-500 text-white",
      "border-blue-600 border",
      "hover:bg-blue-600 hover:text-white",
      "active:bg-blue-500",
      "disabled:bg-blue-400 disabled:hover:bg-blue-400",
    ],
    variant === ButtonVariant.Secondary && [
      "bg-gray-500 text-white",
      "border-gray-600 border",
      "hover:bg-gray-600 hover:text-white",
      "active:bg-gray-500",
      "disabled:bg-gray-400 disabled:hover:bg-gray-400",
    ],
    variant === ButtonVariant.Danger && [
      "bg-red-500 text-white",
      "border-red-600 border",
      "hover:bg-red-600 hover:text-white",
      "active:bg-red-500",
      "disabled:bg-red-400 disabled:hover:bg-red-400",
    ],
  ];

  return (
    <button
      ref={ref}
      disabled={disabled}
      className={clsxm(
        "gap-[0.531rem] rounded-full px-[0.7rem] py-2 font-semibold sm:px-4 text-center",
        "focus-visible:ring-primary-500 focus:outline-none focus-visible:ring",
        "shadow-sm",
        "transition-all duration-75",
        variants,
        "disabled:cursor-not-allowed",
        isLoading &&
          "relative text-transparent transition-none hover:text-transparent disabled:cursor-wait",
        className
      )}
      {...rest}
    >
      {isLoading && (
        <div
          data-testid="loading-icon"
          className={clsxm(
            "absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 text-white"
          )}
        >
          <ImSpinner2 className="animate-spin" />
        </div>
      )}

      {children}
    </button>
  );
});

export default Button;
