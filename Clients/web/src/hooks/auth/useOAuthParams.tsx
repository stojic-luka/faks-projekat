import { useLocation } from "react-router-dom";
import { OAuthParams } from "../../types/authTypes";

export const useOAuthParams = (): OAuthParams | undefined => {
  const { search } = useLocation();
  const q = new URLSearchParams(search);

  if (!q.has("client_id") || !q.has("redirect_uri") || !q.has("code_challenge") || !q.has("code_challenge_method")) {
    return undefined;
  }

  return {
    client_id: q.get("client_id")!,
    redirect_uri: q.get("redirect_uri")!,
    code_challenge: q.get("code_challenge")!,
    code_challenge_method: q.get("code_challenge_method")!,
    scope: q.get("scope") ?? undefined,
    state: q.get("state") ?? undefined,
  };
};
