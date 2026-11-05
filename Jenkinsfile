pipeline {

    agent any

    stages {

        stage('Build') {
            steps {
                echo 'Building Java application...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running application tests...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging Java application...'
                sh 'mvn package -DskipTests'
            }
        }
    }
}
