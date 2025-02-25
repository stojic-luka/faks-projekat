import { ApiResponseError } from "../../types/responseTypes";

export const ErrorMessage = ({ message }: { message: ApiResponseError["error"] | null }) => {
  return (
    <div className="w-full flex justify-center">
      {message && message.code && message.message ? (
        <p className="text-red-500">{`${message.code} ${message.message}`}</p>
      ) : (
        <p className="text-red-500">Unknown error happened!</p>
      )}
    </div>
  );
};
