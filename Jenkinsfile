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
        DEPLOY_SERVER = '192.168.13.151'
        DEPLOY_PATH   = '/opt/myapp'
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

        stage('Deploy') {

            steps {

                withCredentials([
                    usernamePassword(
                        credentialsId: 'server-credentials',
                        usernameVariable: 'DEPLOY_USER',
                        passwordVariable: 'DEPLOY_PASSWORD'
                    )
                ]) {

                    sh '''
                    echo "Deploying to ${DEPLOY_SERVER}"

                    JAR_FILE=$(ls target/*.jar | head -1)

                    sshpass -p "$DEPLOY_PASSWORD" scp \
                    -o StrictHostKeyChecking=no \
                    "$JAR_FILE" \
                    ${DEPLOY_USER}@${DEPLOY_SERVER}:${DEPLOY_PATH}/

                    sshpass -p "$DEPLOY_PASSWORD" ssh \
                    -o StrictHostKeyChecking=no \
                    ${DEPLOY_USER}@${DEPLOY_SERVER} "
                        echo 'Deployment completed'
                        ls -ltr ${DEPLOY_PATH}
                    "
                    '''
                }
            }
        }
    }

    post {

        success {
            echo 'Pipeline completed successfully'
        }

        failure {
            echo 'Pipeline failed'
        }

        always {
            cleanWs()
        }
    }
}
