#!/bin/bash

# Script pour builder et déployer tous les services ERP
# Usage: ./build-and-deploy.sh [service_name] [action]
# Actions: build, up, down, logs, restart

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
    log_info "Building all services..."
    
    # Extraire la version Maven
    local version=$(get_maven_version)
    log_info "Using version: $version"
    
    # Build Maven project
    log_info "Building Maven project..."
    mvn clean package -DskipTests
    
    if [ $? -eq 0 ]; then
        log_success "Maven build completed successfully"
    else
        log_error "Maven build failed"
        exit 1
    fi
    
    # Build Docker images with version tags
    log_info "Building Docker images with version $version..."
    VERSION=$version docker-compose -f docker-compose.prod.yml --env-file docker-compose.env build
    
    if [ $? -eq 0 ]; then
        log_success "Docker images built successfully with version $version"
    else
        log_error "Docker build failed"
        exit 1
    fi
}

# Fonction pour builder un service spécifique
build_service() {
    local service_name=$1
    log_info "Building service: $service_name"
    
    # Extraire la version Maven
    local version=$(get_maven_version)
    log_info "Using version: $version"
    
    # Build Maven project
    mvn clean package -DskipTests -pl $service_name
    
    if [ $? -eq 0 ]; then
        log_success "Maven build completed for $service_name"
    else
        log_error "Maven build failed for $service_name"
        exit 1
    fi
    
    # Build Docker image for specific service with version tag
    VERSION=$version docker-compose -f docker-compose.prod.yml --env-file docker-compose.env build $service_name
    
    if [ $? -eq 0 ]; then
        log_success "Docker image built successfully for $service_name with version $version"
    else
        log_error "Docker build failed for $service_name"
        exit 1
    fi
}

# Fonction pour démarrer tous les services
start_all() {
    log_info "Starting all services..."
    docker-compose -f docker-compose.prod.yml --env-file docker-compose.env up -d
    
    if [ $? -eq 0 ]; then
        log_success "All services started successfully"
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
        log_info "- Sales Service: http://localhost:8060"
        log_info "- Dashboard Service: http://localhost:8065"
        log_info "- Scheduler Service: http://localhost:8066"
    else
        log_error "Failed to start services"
        exit 1
    fi
}

# Fonction pour démarrer un service spécifique
start_service() {
    local service_name=$1
    log_info "Starting service: $service_name"
    docker-compose -f docker-compose.prod.yml --env-file docker-compose.env up -d $service_name
    
    if [ $? -eq 0 ]; then
        log_success "Service $service_name started successfully"
    else
        log_error "Failed to start service $service_name"
        exit 1
    fi
}

# Fonction pour arrêter tous les services
stop_all() {
    log_info "Stopping all services..."
    docker-compose -f docker-compose.prod.yml --env-file docker-compose.env down
    
    if [ $? -eq 0 ]; then
        log_success "All services stopped successfully"
    else
        log_error "Failed to stop services"
        exit 1
    fi
}

# Fonction pour arrêter un service spécifique
stop_service() {
    local service_name=$1
    log_info "Stopping service: $service_name"
    docker-compose -f docker-compose.prod.yml --env-file docker-compose.env stop $service_name
    
    if [ $? -eq 0 ]; then
        log_success "Service $service_name stopped successfully"
    else
        log_error "Failed to stop service $service_name"
        exit 1
    fi
}

# Fonction pour afficher les logs
show_logs() {
    local service_name=$1
    if [ -n "$service_name" ]; then
        log_info "Showing logs for service: $service_name"
        docker-compose -f docker-compose.prod.yml --env-file docker-compose.env logs -f $service_name
    else
        log_info "Showing logs for all services"
        docker-compose -f docker-compose.prod.yml --env-file docker-compose.env logs -f
    fi
}

# Fonction pour redémarrer un service
restart_service() {
    local service_name=$1
    log_info "Restarting service: $service_name"
    docker-compose -f docker-compose.prod.yml --env-file docker-compose.env restart $service_name
    
    if [ $? -eq 0 ]; then
        log_success "Service $service_name restarted successfully"
    else
        log_error "Failed to restart service $service_name"
        exit 1
    fi
}

# Fonction pour extraire la version Maven
get_maven_version() {
    # Extraire la version depuis le POM parent
    local revision=$(mvn help:evaluate -Dexpression=revision -q -DforceStdout)
    local changelist=$(mvn help:evaluate -Dexpression=changelist -q -DforceStdout)
    local version="${revision}${changelist}"
    echo "$version"
}

# Fonction pour afficher l'aide
show_help() {
    echo "Usage: $0 [service_name] [action]"
    echo ""
    echo "Actions:"
    echo "  build     - Build all services or a specific service"
    echo "  up        - Start all services or a specific service"
    echo "  down      - Stop all services or a specific service"
    echo "  logs      - Show logs for all services or a specific service"
    echo "  restart   - Restart a specific service"
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
    echo "  $0 build                    # Build all services"
    echo "  $0 erp-user-service build  # Build only user service"
    echo "  $0 up                      # Start all services"
    echo "  $0 erp-user-service up     # Start only user service"
    echo "  $0 logs erp-user-service   # Show logs for user service"
    echo "  $0 down                    # Stop all services"
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
            build)
                build_service $service_name
                ;;
            up)
                start_service $service_name
                ;;
            down)
                stop_service $service_name
                ;;
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
