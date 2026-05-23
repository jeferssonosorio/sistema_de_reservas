FROM debian:stable-slim

LABEL authors="jeferssonosorio"
LABEL description="Imagen de desarrollo con NoseJS"

# Paquetes básicos
RUN apt-get update && \
    apt-get install -y \
    curl \
    wget \
    git \
    vim \
    unzip \
    bash \
    ca-certificates

# Node.js 22
RUN curl -fsSL https://deb.nodesource.com/setup_24.x | bash - && \
    apt-get install -y nodejs

WORKDIR /app

CMD ["tail", "-f", "/dev/null"]