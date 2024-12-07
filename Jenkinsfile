pipeline {
    agent any

    environment {
        COMPOSE_FILE = "docker-compose.yml"
        GIT_URL = 'https://github.com/mohamednaffeti/bank-account-ms-enset-bdcc-app.git'
    }

    stages {
        stage('Setup Maven') {
            steps {
                script {
                    echo "Checking if Maven is installed..."
                    // Vérification et installation de Maven si nécessaire
                    def mavenCheck = sh(script: 'mvn -v || echo "MavenNotInstalled"', returnStdout: true).trim()
                    if (mavenCheck.contains("MavenNotInstalled")) {
                        echo "Maven is not installed. Installing Maven..."
                        sh '''
                        apt-get update
                        apt-get install -y maven
                        '''
                    } else {
                        echo "Maven is already installed."
                    }
                }
            }
        }

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
                        sh 'mvn clean install -DskipTests'
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    echo "Building Docker images for all services..."
                    sh 'docker-compose build'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    echo "Deploying services with Docker Compose..."
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
