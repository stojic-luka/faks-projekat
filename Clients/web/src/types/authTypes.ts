import { ApiResponseData, ApiResponseError } from "./responseTypes";
import { User } from "./userTypes";

type WithUser = { user: User };
type WithToken = { token: string };
export type AuthResponse = WithUser & WithToken;
// eslint-disable-next-line @typescript-eslint/no-empty-object-type
export interface UserResponse extends User {}
// eslint-disable-next-line @typescript-eslint/no-empty-object-type
export interface RefreshResponse extends WithToken {}

export type OnSuccess<T> = (data: ApiResponseData<T>) => void;
export type OnError = (error: ApiResponseError) => void;

export interface AuthCredentials {
  username: string;
  password: string;
}

export interface OAuthParams {
  client_id: string;
  redirect_uri: string;
  code_challenge: string;
  code_challenge_method: string;
  scope?: string;
  state?: string;
}

export interface AuthData {
  credentials: AuthCredentials;
  params?: OAuthParams;
}
