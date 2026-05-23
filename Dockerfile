FROM debian:stable-slim

LABEL authors="jeferssonosorio"
LABEL description="Imagen de desarrollo con Node.js y OpenJDK"

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

# Java
RUN apt-get install -y openjdk-21-jdk
ENV JAVA_HOME=/usr/lib/jvm/default-java
ENV PATH="${JAVA_HOME}/bin:${PATH}"
# Node.js 22
RUN curl -fsSL https://deb.nodesource.com/setup_24.x | bash - && \
    apt-get install -y nodejs

WORKDIR /app

CMD ["tail", "-f", "/dev/null"]