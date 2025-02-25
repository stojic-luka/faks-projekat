interface BaseResponse {
  status: string;
  meta?: {
    timestamp?: string;
    apiVersion: string;
  };
}

export interface ApiResponseData<T> extends BaseResponse {
  data: T;
}

export interface ApiResponseError extends BaseResponse {
  error: {
    code: string;
    message: string;
  };
}

export type ApiResponse<T> = ApiResponseData<T> | ApiResponseError;
