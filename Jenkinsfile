pipeline {
    agent any

    environment {
        COMPOSE_FILE = "docker-compose.yml"
        GIT_URL = 'https://github.com/mohamednaffeti/bank-account-ms-enset-bdcc-app.git'
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
                        // Compile Discovery Service (ex: avec Maven)
                        sh 'mvn clean install -DskipTests'
                    }
                }
            }
        }

        stage('Build Config Service') {
            steps {
                script {
                    echo "Building Config Service..."
                    dir('config-service') {
                        // Compile Config Service (ex: avec Maven)
                        sh 'mvn clean install -DskipTests'
                    }
                }
            }
        }

        stage('Build Customer Service') {
            steps {
                script {
                    echo "Building Customer Service..."
                    dir('customer-service') {
                        // Compile Customer Service (ex: avec Maven)
                        sh 'mvn clean install -DskipTests'
                    }
                }
            }
        }

        stage('Build Account Service') {
            steps {
                script {
                    echo "Building Account Service..."
                    dir('account-service') {
                        // Compile Account Service (ex: avec Maven)
                        sh 'mvn clean install -DskipTests'
                    }
                }
            }
        }

        stage('Build Gateway Service') {
            steps {
                script {
                    echo "Building Gateway Service..."
                    dir('gateway-service') {
                        // Compile Gateway Service (ex: avec Maven)
                        sh 'mvn clean install -DskipTests'
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
            cleanWs()
        }
    }
}
