FROM continuumio/miniconda3
WORKDIR /app
COPY environment.yml ./
RUN conda env create -f environment.yml
SHELL ["conda", "run", "-n", "ai_service_env", "/bin/bash", "-c"]
COPY . .
EXPOSE 5000
CMD ["conda", "run", "--no-capture-output", "-n", "ai_service_env", "uvicorn", "main:app", "--host", "0.0.0.0", "--port", "5000"]   