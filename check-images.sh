#!/bin/bash

# Script pour vérifier et télécharger les images Docker nécessaires
# Usage: ./check-images.sh

set -e

# Couleurs pour les logs
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Fonction pour afficher les logs colorés
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Fonction pour vérifier si une image existe
check_image_exists() {
    local image="$1"
    if docker image inspect "$image" > /dev/null 2>&1; then
        return 0
    else
        return 1
    fi
}

# Fonction pour télécharger une image
pull_image() {
    local image="$1"
    log_info "Téléchargement de l'image: $image"
    if docker pull "$image"; then
        log_success "Image $image téléchargée avec succès"
        return 0
    else
        log_error "Échec du téléchargement de l'image: $image"
        return 1
    fi
}

# Fonction pour vérifier et télécharger une image
check_and_pull_image() {
    local image="$1"
    local description="$2"
    
    log_info "Vérification de l'image: $image ($description)"
    
    if check_image_exists "$image"; then
        log_success "Image $image existe déjà"
    else
        log_warning "Image $image n'existe pas, téléchargement en cours..."
        if pull_image "$image"; then
            log_success "Image $image prête"
        else
            log_error "Impossible de télécharger $image"
            return 1
        fi
    fi
}

# Images requises pour le projet
IMAGES=(
    "eclipse-temurin:17-jre|Java 17 Runtime Environment"
    "maven:3.9.6-eclipse-temurin-17|Maven avec Eclipse Temurin 17"
    "mysql:8.0|Base de données MySQL"
    "quay.io/keycloak/keycloak:24.0.3|Serveur Keycloak"
)

# Vérifier si Docker est en cours d'exécution
if ! docker info > /dev/null 2>&1; then
    log_error "Docker n'est pas en cours d'exécution. Veuillez démarrer Docker et réessayer."
    exit 1
fi

log_info "Vérification des images Docker requises..."

# Vérifier et télécharger chaque image
failed_images=()
for image_info in "${IMAGES[@]}"; do
    IFS='|' read -r image description <<< "$image_info"
    if ! check_and_pull_image "$image" "$description"; then
        failed_images+=("$image")
    fi
done

# Résumé
echo ""
if [ ${#failed_images[@]} -eq 0 ]; then
    log_success "Toutes les images requises sont disponibles !"
    log_info "Vous pouvez maintenant exécuter:"
    log_info "  ./deploy-prod.sh build"
    log_info "  ./deploy-prod.sh up"
else
    log_error "Échec du téléchargement des images suivantes:"
    for image in "${failed_images[@]}"; do
        log_error "  - $image"
    done
    log_info "Veuillez vérifier votre connexion internet et réessayer."
    exit 1
fi
