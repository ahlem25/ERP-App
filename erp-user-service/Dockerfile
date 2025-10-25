FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier le POM et télécharger les dépendances
COPY pom.xml .
COPY erp-common/pom.xml erp-common/
COPY erp-user-service/pom.xml erp-user-service/

# Copier le code source
COPY erp-common/src erp-common/src
COPY erp-user-service/src erp-user-service/src

# Compiler l'application
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests -f erp-user-service/pom.xml

# Exposer le port
EXPOSE 8055

# Commande de démarrage
CMD ["java", "-jar", "erp-user-service/target/erp-user-service-1.0.0-SNAPSHOT.jar"]
