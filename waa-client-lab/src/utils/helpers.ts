import { baseInstance } from "@/config/instance";
import { AxiosError, AxiosRequestConfig } from "axios";
import logger from "./logger";
import type { ApiResponse } from "@/types/server/config";

export const onParseResponse = async <T>(args: AxiosRequestConfig) => {
  let formattedResponse: ApiResponse<T>;

  try {
    const { data } = await baseInstance({ ...args });

    formattedResponse = data;
  } catch (error) {
    const axiosError = error as AxiosError;
    const data = axiosError.response?.data as ApiResponse<T>;

    logger(axiosError);

    const formattedData = {
      ...data,
      formattedError: Object.values(data?.errors as object).join(""),
    };

    formattedResponse = formattedData;
  }

  return formattedResponse;
};
