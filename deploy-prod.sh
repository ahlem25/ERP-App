#!/bin/bash

# Script pour déployer l'application ERP en production
# Usage: ./deploy-prod.sh [action]
# Actions: build, up, down, logs, restart, status

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

# Fonction pour builder tous les services
build_all() {
    log_info "Building all services for production..."
    
    # Vérifier les images Docker requises
    log_info "Vérification des images Docker requises..."
    if ! ./check-images.sh; then
        log_error "Échec de la vérification des images Docker"
        exit 1
    fi
    
    # Build Maven project
    log_info "Building Maven project..."
    mvn clean package -DskipTests
    
    if [ $? -eq 0 ]; then
        log_success "Maven build completed successfully"
    else
        log_error "Maven build failed"
        exit 1
    fi
    
    # Build Docker images for production
    log_info "Building Docker images for production..."
    docker-compose -f docker-compose.prod.yml build
    
    if [ $? -eq 0 ]; then
        log_success "Docker images built successfully for production"
    else
        log_error "Docker build failed"
        exit 1
    fi
}

# Fonction pour démarrer tous les services
start_all() {
    log_info "Starting all services in production mode..."
    docker-compose -f docker-compose.prod.yml up -d
    
    if [ $? -eq 0 ]; then
        log_success "All services started successfully in production mode"
        log_info "Services are running on the following ports:"
        log_info "- Keycloak: http://localhost:8092"
        log_info "- MySQL: localhost:3306"
        log_info "- Service Discovery: http://localhost:8013"
        log_info "- Config Service: http://localhost:8012"
        log_info "- API Gateway: http://localhost:8014"
        log_info "- User Service: http://localhost:8055"
        log_info "- Product Service: http://localhost:8051"
        log_info "- Inventory Service: http://localhost:8059"
        log_info "- Supplier Service: http://localhost:8052"
        log_info "- Order Service: http://localhost:8053"
        log_info "- Client Service: http://localhost:8058"
        log_info "- Payment Service: http://localhost:8069"
        log_info "- Billing Service: http://localhost:8057"
        log_info "- Sales Service: http://localhost:8089"
        log_info "- Dashboard Service: http://localhost:8065"
        log_info "- Scheduler Service: http://localhost:8066"
    else
        log_error "Failed to start services"
        exit 1
    fi
}

# Fonction pour arrêter tous les services
stop_all() {
    log_info "Stopping all production services..."
    docker-compose -f docker-compose.prod.yml down
    
    if [ $? -eq 0 ]; then
        log_success "All production services stopped successfully"
    else
        log_error "Failed to stop services"
        exit 1
    fi
}

# Fonction pour afficher les logs
show_logs() {
    local service_name=$1
    if [ -n "$service_name" ]; then
        log_info "Showing logs for production service: $service_name"
        docker-compose -f docker-compose.prod.yml logs -f $service_name
    else
        log_info "Showing logs for all production services"
        docker-compose -f docker-compose.prod.yml logs -f
    fi
}

# Fonction pour redémarrer un service
restart_service() {
    local service_name=$1
    log_info "Restarting production service: $service_name"
    docker-compose -f docker-compose.prod.yml restart $service_name
    
    if [ $? -eq 0 ]; then
        log_success "Production service $service_name restarted successfully"
    else
        log_error "Failed to restart service $service_name"
        exit 1
    fi
}

# Fonction pour afficher le statut des services
show_status() {
    log_info "Production services status:"
    docker-compose -f docker-compose.prod.yml ps
}

# Fonction pour vérifier les images
check_images() {
    log_info "Vérification des images Docker requises..."
    if ./check-images.sh; then
        log_success "Toutes les images sont disponibles"
    else
        log_error "Certaines images sont manquantes"
        exit 1
    fi
}

# Fonction pour afficher l'aide
show_help() {
    echo "Usage: $0 [action] [service_name]"
    echo ""
    echo "Actions:"
    echo "  build     - Build all services for production"
    echo "  up        - Start all services in production mode"
    echo "  down      - Stop all production services"
    echo "  logs      - Show logs for all services or a specific service"
    echo "  restart   - Restart a specific service"
    echo "  status    - Show status of all services"
    echo "  check     - Check if required Docker images exist"
    echo "  help      - Show this help message"
    echo ""
    echo "Service names:"
    echo "  erp-service-discovery"
    echo "  erp-config"
    echo "  erp-api-gateway"
    echo "  erp-user-service"
    echo "  erp-product-service"
    echo "  erp-inventory-service"
    echo "  erp-supplier-service"
    echo "  erp-order-service"
    echo "  erp-client-service"
    echo "  erp-payment-service"
    echo "  erp-billing-service"
    echo "  erp-sales-service"
    echo "  erp-dashboard-service"
    echo "  erp-scheduler-service"
    echo ""
    echo "Examples:"
    echo "  $0 check                   # Check if required Docker images exist"
    echo "  $0 build                   # Build all services for production"
    echo "  $0 up                      # Start all services in production mode"
    echo "  $0 logs erp-user-service   # Show logs for user service"
    echo "  $0 restart erp-user-service # Restart user service"
    echo "  $0 status                  # Show status of all services"
    echo "  $0 down                    # Stop all production services"
}

# Vérifier si Docker est en cours d'exécution
if ! docker info > /dev/null 2>&1; then
    log_error "Docker is not running. Please start Docker and try again."
    exit 1
fi

# Vérifier si Docker Compose est disponible
if ! command -v docker-compose &> /dev/null; then
    log_error "Docker Compose is not installed. Please install Docker Compose and try again."
    exit 1
fi

# Traitement des arguments
case $# in
    0)
        show_help
        ;;
    1)
        case $1 in
            build)
                build_all
                ;;
            up)
                start_all
                ;;
            down)
                stop_all
                ;;
            logs)
                show_logs
                ;;
            status)
                show_status
                ;;
            check)
                check_images
                ;;
            help)
                show_help
                ;;
            *)
                log_error "Unknown action: $1"
                show_help
                exit 1
                ;;
        esac
        ;;
    2)
        service_name=$1
        action=$2
        
        case $action in
            logs)
                show_logs $service_name
                ;;
            restart)
                restart_service $service_name
                ;;
            *)
                log_error "Unknown action: $action"
                show_help
                exit 1
                ;;
        esac
        ;;
    *)
        log_error "Too many arguments"
        show_help
        exit 1
        ;;
esac
