export interface ChatDataParams {
  message: string;
  image: string;
}

export interface ChatRequestBody {
  model: string;
  prompt: string;
  imageb64: string;
}
