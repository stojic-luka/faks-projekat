import { ApiResponseData, ApiResponseError } from "./responseTypes";
import { User } from "./userTypes";

type WithUser = { user: User };
type WithToken = { token: string };
export type AuthResponse = WithUser & WithToken;
export type UserResponse = WithUser;
export type RefreshResponse = WithToken;

export interface AuthCredentials {
  username: string;
  password: string;
}

export type AuthResponsee = WithToken;

export type OnSuccess<T> = (data: ApiResponseData<T>) => void;
export type OnError = (error: ApiResponseError) => void;
