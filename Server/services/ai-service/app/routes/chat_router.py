from fastapi import APIRouter, HTTPException, Request
from fastapi.responses import JSONResponse, StreamingResponse
from app.utils.is_valid_base64 import is_valid_base64  # type: ignore
from app.services.chatbot import get_chatbot_response, get_streamed_chatbot_response

from app.types.chat_request import ChatRequest

# "/chat"
router = APIRouter()


@router.post("/", response_class=JSONResponse)
async def chat(request: ChatRequest):
    if request.imageb64 and not is_valid_base64(request.imageb64):
        raise HTTPException(status_code=400, detail="Image base64 is invalid")

    if request.streamed:
        return StreamingResponse(
            content=get_streamed_chatbot_response(
                message=request.message,
                image_b64=request.imageb64,
            ),
            media_type="application/stream+json",
            status_code=200,
        )
    else:
        return JSONResponse(
            content=await get_chatbot_response(
                message=request.message,
                image_b64=request.imageb64,
            ),
            media_type="application/json",
            status_code=200,
        )
