
pipeline {
    agent any

    tools {
        maven 'maven-3.10.0'
    }

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
                echo 'Packaging application into JAR...'
                sh 'mvn package'
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar'
            echo 'Build completed successfully!'
        }
    }
}
