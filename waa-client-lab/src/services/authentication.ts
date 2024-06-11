import axios from "axios";

import { onParseResponse } from "@/utils/helpers";
import logger from "@/utils/logger";

import { store } from "@/redux/store";
import { actions } from "@/redux/authentication";
import type { LoginForm } from "@/pages/Login/types";
import type { ApiResponse } from "@/types/server/config";
import type { AuthenticationDetailResponse } from "@/types/server/authentication";

export const refreshToken = async (): Promise<string> => {
  try {
    const state = store.getState();
    const refreshToken = state.authentication.refreshToken;

    const response = await axios.post(
      `${import.meta.env.VITE_PUBLIC_BASE_URL}/auth/refresh-token`,
      {},
      {
        headers: {
          Authorization: `Bearer ${refreshToken}`,
        },
      }
    );

    const { accessToken, refreshToken: newRefreshToken } = response.data;
    store.dispatch(actions.callSetAccessToken(accessToken));
    store.dispatch(actions.callSetRefreshToken(newRefreshToken));

    return accessToken;
  } catch (error) {
    logger(error);
    throw error;
  }
};

export const loginAPI = async (
  values: LoginForm
): Promise<ApiResponse<AuthenticationDetailResponse>> => {
  const response = await onParseResponse<AuthenticationDetailResponse>({
    method: "post",
    url: "/auth/login",
    data: values,
  });

  return response;
};

export const logoutAPI = async (): Promise<ApiResponse<unknown>> => {
  const response = await onParseResponse<unknown>({
    method: "post",
    url: "/auth/logout",
    data: null,
  });

  return response;
};
