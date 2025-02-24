from openai import AsyncOpenAI

client = AsyncOpenAI(
    base_url="http://localhost:7869/v1/",
    api_key="ollama",
)
