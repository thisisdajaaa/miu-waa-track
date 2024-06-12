import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { initialAuthenticationState, initialUserDetails } from "./data";
import { UserDetailResponse } from "@/types/server/user";

const authenticationSlice = createSlice({
  name: "authentication",
  initialState: initialAuthenticationState,
  reducers: {
    setUserDetails: (state, { payload }: PayloadAction<UserDetailResponse>) => {
      state.userDetails = payload;
    },
    setAccessToken: (state, { payload }: PayloadAction<string>) => {
      state.accessToken = payload;
    },
    setRefreshToken: (state, { payload }: PayloadAction<string>) => {
      state.refreshToken = payload;
    },
    setResetAuthentication: (state) => {
      state.userDetails = initialUserDetails;
      state.accessToken = "";
      state.refreshToken = "";
    },
  },
});

const { actions: authenticationActions, reducer: authenticationReducers } =
  authenticationSlice;

export { authenticationActions, authenticationReducers };
