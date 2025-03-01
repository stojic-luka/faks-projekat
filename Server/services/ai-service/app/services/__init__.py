import os
from dotenv import load_dotenv
from openai import AsyncOpenAI

load_dotenv()

client = AsyncOpenAI(
    base_url=os.getenv("OLLAMA_API_URL"),
    api_key="ollama",
)
