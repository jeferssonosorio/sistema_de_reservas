FROM debian:stable-slim

LABEL authors="jeferssonosorio"
LABEL description="Imagen de desarrollo con OpenJDK y Maven"

# Paquetes básicos
RUN apt-get update && \
    apt-get install -y \
    curl \
    wget \
    git \
    vim \
    unzip \
    bash \
    ca-certificates \
    openjdk-25-jdk \
    maven


ENV JAVA_HOME=/usr/lib/jvm/java-25-openjdk-arm64
ENV PATH="${JAVA_HOME}/bin:${PATH}"

WORKDIR /app

CMD ["mvn", "spring-boot:run"]

