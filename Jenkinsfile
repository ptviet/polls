pipeline {
    agent {
        docker {
            image 'maven:3.5.4-jdk-8-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Initialize') {
            steps {
                sh '''
                   echo "PATH = ${PATH}"
                   echo "M2_HOME = ${M2_HOME}"
                   mvn --version
                   '''
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
          steps {
          sh 'mvn test'
          }
          post {
            always {
              junit 'target/surefire-reports/*.xml'
                }
          }
        }
        stage('Deliver') {
          steps {
            sh 'bash ./deliver.sh'
          }
        }
    }
}