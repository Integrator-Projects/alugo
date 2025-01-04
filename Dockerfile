# Usar imagem base do OpenJDK
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copiar apenas os arquivos de dependência primeiro
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Baixar dependências
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte e buildar
COPY src src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app .

CMD ["./mvnw", "spring-boot:run"]


