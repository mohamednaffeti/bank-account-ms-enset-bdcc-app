pipeline {
    agent any

    environment {
        COMPOSE_FILE = "docker-compose.yml"
        GIT_URL = 'https://github.com/mohamednaffeti/bank-account-ms-enset-bdcc-app.git'
        MAVEN_HOME = tool name: 'Maven 3.9.9', type: 'Maven'  // Utilisation de Maven 3.9.9 configuré dans Jenkins
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo "Cloning the repository..."
                git url: "${GIT_URL}", branch: 'main' // Utilisation du dépôt Git
            }
        }

        stage('Build Discovery Service') {
            steps {
                script {
                    echo "Building Discovery Service..."
                    dir('discovery-service') {
                        // Compiler le service Discovery (avec Maven)
                        sh "'${MAVEN_HOME}/bin/mvn' clean install -DskipTests"
                    }
                }
            }
        }

        stage('Build Config Service') {
            steps {
                script {
                    echo "Building Config Service..."
                    dir('config-service') {
                        // Compiler le service Config (avec Maven)
                        sh "'${MAVEN_HOME}/bin/mvn' clean install -DskipTests"
                    }
                }
            }
        }

        stage('Build Customer Service') {
            steps {
                script {
                    echo "Building Customer Service..."
                    dir('customer-service') {
                        // Compiler le service Customer (avec Maven)
                        sh "'${MAVEN_HOME}/bin/mvn' clean install -DskipTests"
                    }
                }
            }
        }

        stage('Build Account Service') {
            steps {
                script {
                    echo "Building Account Service..."
                    dir('account-service') {
                        // Compiler le service Account (avec Maven)
                        sh "'${MAVEN_HOME}/bin/mvn' clean install -DskipTests"
                    }
                }
            }
        }

        stage('Build Gateway Service') {
            steps {
                script {
                    echo "Building Gateway Service..."
                    dir('gateway-service') {
                        // Compiler le service Gateway (avec Maven)
                        sh "'${MAVEN_HOME}/bin/mvn' clean install -DskipTests"
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    echo "Building Docker images for all services..."
                    // Construire les images Docker avec Docker Compose
                    sh 'docker-compose build'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    echo "Deploying services with Docker Compose..."
                    // Arrêter les conteneurs existants et relancer les services avec Docker Compose
                    sh """
                    docker-compose down
                    docker-compose up -d
                    """
                }
            }
        }
    }

    post {
        always {
            echo "Cleaning workspace..."
            cleanWs()  // Nettoyer le workspace à la fin du pipeline
        }
    }
}
