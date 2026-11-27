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

                        sshpass -e ssh \
                            -o StrictHostKeyChecking=no \
                            "$SERVER_USER@$DEPLOY_SERVER" bash -s <<EOF

                            set -e

                            IMAGE="docker.io/${IMAGE_NAME}:${IMAGE_TAG}"

                            echo "Pulling image: \$IMAGE"
                            docker pull "\$IMAGE"

                            echo "Stopping old container..."
                            docker stop java-app || true

                            echo "Removing old container..."
                            docker rm java-app || true

                            echo "Starting new container..."
                            docker run -d \
                                --name java-app \
                                --restart unless-stopped \
                                -p 8082:8080 \
                                "\$IMAGE"

                            echo "Verifying deployment..."
                            test "\$(docker inspect -f '{{.State.Running}}' java-app)" = true

                            echo "Deployment completed successfully!"

                            docker ps --filter name=java-app

EOF
                    '''
                }
            }
        }
    }

    post {

        success {
            echo 'Application Successfully Deployed'
        }

        failure {
            echo 'Application Deployment Failed'
        }

        always {
            cleanWs()
        }
    }
}
