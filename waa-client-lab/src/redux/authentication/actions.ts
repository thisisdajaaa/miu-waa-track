import { AppDispatch, AppThunk } from "../store";
import { authenticationActions } from "./slices";

const { setEmail, setAccessToken, setRefreshToken, setResetAuthentication } =
  authenticationActions;

const callSetEmail =
  (email: string): AppThunk =>
  (dispatch: AppDispatch) => {
    dispatch(setEmail(email));
  };

const callSetAccessToken =
  (accessToken: string): AppThunk =>
  (dispatch: AppDispatch) => {
    dispatch(setAccessToken(accessToken));
  };

const callSetRefreshToken =
  (refreshToken: string): AppThunk =>
  (dispatch: AppDispatch) => {
    dispatch(setRefreshToken(refreshToken));
  };

const callSetResetAuthentication = (): AppThunk => (dispatch: AppDispatch) => {
  dispatch(setResetAuthentication());
};

const actions = {
  callSetEmail,
  callSetAccessToken,
  callSetRefreshToken,
  callSetResetAuthentication,
};

export default actions;
