import uvicorn
from app import create_app  # type: ignore

app = create_app()

if __name__ == "__main__":
    uvicorn.run("main:app", host="localhost", port=5000, reload=True)
