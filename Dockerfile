# Multi-stage build pour optimiser la taille de l'image
FROM maven:3.9.6-openjdk-17-slim AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier le pom.xml principal et les pom.xml des modules
COPY pom.xml .
COPY erp-common/pom.xml erp-common/
COPY utils-lib/pom.xml utils-lib/
COPY erp-api-gateway/pom.xml erp-api-gateway/
COPY erp-service-discovery/pom.xml erp-service-discovery/
COPY erp-config/pom.xml erp-config/
COPY erp-user-service/pom.xml erp-user-service/
COPY erp-product-service/pom.xml erp-product-service/
COPY erp-inventory-service/pom.xml erp-inventory-service/
COPY erp-supplier-service/pom.xml erp-supplier-service/
COPY erp-order-service/pom.xml erp-order-service/
COPY erp-client-service/pom.xml erp-client-service/
COPY erp-payment-service/pom.xml erp-payment-service/
COPY erp-billing-service/pom.xml erp-billing-service/
COPY erp-sales-service/pom.xml erp-sales-service/
COPY erp-dashboard-service/pom.xml erp-dashboard-service/
COPY erp-scheduler-service/pom.xml erp-scheduler-service/

# Télécharger les dépendances (cache layer)
RUN mvn dependency:go-offline -B

# Copier le code source
COPY . .

# Build l'application
RUN mvn clean package -DskipTests

# Stage de production
FROM eclipse-temurin:17-jre

# Installer les outils nécessaires
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Créer un utilisateur non-root
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Créer les répertoires nécessaires pour Git et donner les permissions
RUN mkdir -p /home/appuser/.config/jgit && \
    chown -R appuser:appuser /home/appuser

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR depuis le stage de build
# Le nom du JAR sera passé en argument
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# Changer la propriété du fichier
RUN chown appuser:appuser app.jar

# Changer vers l'utilisateur non-root
USER appuser

# Exposer le port (sera surchargé dans docker-compose)
EXPOSE 8080

# Variables d'environnement par défaut
ENV SPRING_PROFILES_ACTIVE=docker
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Point d'entrée
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
