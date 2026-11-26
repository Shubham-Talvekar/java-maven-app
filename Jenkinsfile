pipeline {
    agent any

    parameters {

        choice(
            name: 'VERSION',
            choices: ['1.1.0', '1.2.0', '1.3.0'],
            description: 'Application Version'
        )

        booleanParam(
            name: 'executeTests',
            defaultValue: true,
            description: 'Run Unit Tests'
        )
    }

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }

    environment {
        IMAGE_NAME = 'shubhamtalvekar/java-maven-app'
        IMAGE_TAG  = "${BUILD_NUMBER}-${params.VERSION}"
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

                echo "Building version ${params.VERSION}"

                sh '''
                    mvn clean package -DskipTests
                '''
            }
        }

        stage('Test') {

            when {
                expression {
                    return params.executeTests
                }
            }

            steps {

                echo "Running Tests..."

                sh '''
                    mvn test
                '''
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar'
            }
        }

        stage('Build Docker Image') {
            steps {

                sh '''
                    docker build \
                    -t $IMAGE_NAME:$IMAGE_TAG \
                    .
                '''
            }
        }

        stage('Docker Hub Login') {

            steps {

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {

                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login \
                        -u "$DOCKER_USER" \
                        --password-stdin
                    '''
                }
            }
        }

        stage('Push Docker Image') {

            steps {

                sh '''
                    docker push $IMAGE_NAME:$IMAGE_TAG
                '''

                sh '''
                    docker tag \
                    $IMAGE_NAME:$IMAGE_TAG \
                    $IMAGE_NAME:latest
                '''

                sh '''
                    docker push $IMAGE_NAME:latest
                '''
            }
        }
    }

    post {

        success {
            echo 'Docker image pushed successfully to Docker Hub'
        }

        failure {
            echo 'Pipeline failed'
        }

        always {
            cleanWs()
        }
    }
}
