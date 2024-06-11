import { RootState } from "../store";
import { initialAuthenticationState } from "./data";

const email = (state: RootState) =>
  state.authentication.email || initialAuthenticationState.email;

const accessToken = (state: RootState) =>
  state.authentication.accessToken || initialAuthenticationState.accessToken;

const refreshToken = (state: RootState) =>
  state.authentication.refreshToken || initialAuthenticationState.refreshToken;

const selectors = { email, accessToken, refreshToken };

export default selectors;
