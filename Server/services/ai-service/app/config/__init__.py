from fastapi.middleware.cors import CORSMiddleware

origins = [
    "*"
    # "http://localhost:3000",  # Your frontend URL
    # "https://yourfrontenddomain.com",  # If deployed
]


def configure_cors(app):
    app.add_middleware(
        CORSMiddleware,
        allow_origins=origins,
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"],
    )
