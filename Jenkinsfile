
pipeline {

    agent any

    parameters {
        string(
            name: 'VERSION',
            defaultValue: '1.0.0',
            description: 'Application Version'
        )
    }

    tools {
        maven 'Maven-3.9'
    }

    options {
        disableConcurrentBuilds()
    }

    environment {
        IMAGE_NAME = 'shubhamtalvekar/java-maven-app'
        IMAGE_TAG = "${BUILD_NUMBER}-${params.VERSION}"
        DEPLOY_SERVER = '192.168.13.151'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Shubham-Talvekar/java-maven-app.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh """
                    docker build \
                    -t ${IMAGE_NAME}:${IMAGE_TAG} \
                    -t ${IMAGE_NAME}:latest .
                """
            }
        }

        stage('Docker Hub Login') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {

                    sh '''
                        echo "$DOCKER_PASS" | docker login \
                        -u "$DOCKER_USER" \
                        --password-stdin
                    '''
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                sh """
                    docker push ${IMAGE_NAME}:${IMAGE_TAG}
                    docker push ${IMAGE_NAME}:latest
                """
            }
        }

        stage('Deploy') {

            steps {

                withCredentials([
                    usernamePassword(
                        credentialsId: 'server-credentials',
                        usernameVariable: 'SERVER_USER',
                        passwordVariable: 'SERVER_PASS'
                    )
                ]) {

                    sh '''
                        set -e

                        export SSHPASS="$SERVER_PASS"

                        echo "Copying Docker Compose file..."

                        sshpass -e scp \
                            -o StrictHostKeyChecking=no \
                            docker-compose.yml \
                            "$SERVER_USER@$DEPLOY_SERVER:/opt/shopkart/"

                        echo "Connecting to deployment server..."

                        sshpass -e ssh \
                            -o StrictHostKeyChecking=no \
                            "$SERVER_USER@$DEPLOY_SERVER" \
                            "bash -s -- '$IMAGE_TAG'" <<'REMOTE'

set -e

cd /opt/shopkart

export IMAGE_TAG="$1"

echo "Application image version: $IMAGE_TAG"

echo "Checking Docker Compose availability..."

if docker compose version >/dev/null 2>&1; then
    compose() {
        docker compose -p shopkart "$@"
    }
elif podman compose version >/dev/null 2>&1; then
    compose() {
        podman compose -p shopkart "$@"
    }
else
    echo "ERROR: Docker Compose is not available"
    exit 1
fi

echo "Validating Docker Compose configuration..."

compose config --quiet

echo "Pulling container images..."

compose pull

echo "Checking existing Java container..."

if docker inspect java-app >/dev/null 2>&1; then

    PROJECT=$(docker inspect -f '{{index .Config.Labels "com.docker.compose.project"}}' java-app)

    if [ "$PROJECT" != "shopkart" ]; then

        echo "Removing old standalone Java container..."

        docker stop java-app
        docker rm java-app

    fi

fi

echo "Starting Java application and MySQL database..."

compose up -d

echo "Checking container status..."

compose ps

echo "Verifying application and database connectivity..."

for i in $(seq 1 60); do

    if curl --noproxy '*' \
        --connect-timeout 3 \
        --max-time 5 \
        -fsS \
        http://127.0.0.1:8082/api/products >/dev/null; then

        echo "Application successfully deployed!"
        echo "Database connectivity verified!"
        echo "Website: http://192.168.13.151:8082/"

        exit 0

    fi

    echo "Waiting for application startup... Attempt $i/60"

    sleep 5

done

echo "ERROR: Application health verification failed"

compose logs --tail=50

exit 1

REMOTE
                    '''
                }
            }
        }
    }

    post {

        success {
            echo 'Application and Database Successfully Deployed'
        }

        failure {
            echo 'Application Deployment Failed'
        }

        always {
            sh 'docker logout || true'
            cleanWs()
        }
    }
}
