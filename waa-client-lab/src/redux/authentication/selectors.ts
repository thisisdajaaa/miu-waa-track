import { RootState } from "../store";
import { initialAuthenticationState } from "./data";

const userDetails = (state: RootState) =>
  state.authentication.userDetails || initialAuthenticationState.userDetails;

const accessToken = (state: RootState) =>
  state.authentication.accessToken || initialAuthenticationState.accessToken;

const refreshToken = (state: RootState) =>
  state.authentication.refreshToken || initialAuthenticationState.refreshToken;

const selectors = { userDetails, accessToken, refreshToken };

export default selectors;
