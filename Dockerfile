# Etapa de construção com Maven
FROM maven:3.9.8-amazoncorretto-21  AS build

# Definir o diretório de trabalho no contêiner para a fase de construção
WORKDIR /app

# Copiar o arquivo pom.xml e outros arquivos necessários para o Maven
COPY pom.xml .

# Baixar as dependências necessárias para o Maven (usando cache)
RUN mvn dependency:go-offline

# Copiar o código-fonte do projeto
COPY src ./src

# Construir o projeto usando Maven
RUN mvn clean package -Plocal -DprofileIdEnabled=true -DskipTests -B

# Etapa de execução com JDK 21
FROM openjdk:21-jdk-slim

# Criar o diretório de aplicativo
WORKDIR /app

# Copiar o arquivo JAR do seu microserviço para o contêiner
COPY --from=build /app/target/products-identifier-0.0.1-SNAPSHOT.jar /app/products-identifier-0.0.1-SNAPSHOT.jar

# Expor a porta que o seu microserviço está escutando
EXPOSE 8081

# Comando para iniciar o seu microserviço quando o contêiner for iniciado
CMD ["java", "-jar", "products-identifier-0.0.1-SNAPSHOT.jar"]