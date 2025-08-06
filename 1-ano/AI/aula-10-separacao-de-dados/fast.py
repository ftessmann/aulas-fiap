import uvicorn
from fastapi import FastAPI

app = FastAPI()

@app.get("/")
def read_root():
    return {"Hello": "World"}, "Hello World", ["Olá", "Mundo"]


if __name__ == "__main__":
    uvicorn.run(app)