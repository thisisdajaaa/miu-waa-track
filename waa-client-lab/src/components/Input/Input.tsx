import { FC } from "react";
import { InputProps } from "./types";
import clsxm from "@/utils/clsxm";

const Input: FC<InputProps> = (props) => {
  const {
    disabled = false,
    inputClassName,
    containerClassName,
    errorMessage,
    type,
    value,
    readOnly,
    ...rest
  } = props;

  return (
    <div className="relative block w-full">
      <div
        className={clsxm(
          "flex w-full flex-grow appearance-none items-center px-[0.875rem] py-2",
          "max-h-[2.5rem] rounded-[0.25rem] border text-black",
          "duration-150 focus-within:border-gray-400 focus-within:transition-all sm:text-sm",
          !!errorMessage && "border-red-400",
          disabled && "bg-disable",
          containerClassName
        )}
      >
        <input
          type={type}
          value={value}
          disabled={disabled}
          readOnly={readOnly}
          className={clsxm(
            "block w-full border-transparent placeholder-gray-500 text-base leading-[1.813rem] text-black focus:border-transparent focus:outline-none focus:ring-0",
            disabled && "bg-gray-300",
            readOnly && "cursor-default",
            inputClassName
          )}
          {...rest}
        />
      </div>
    </div>
  );
};

export default Input;
