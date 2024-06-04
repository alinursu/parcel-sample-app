pipeline {
    agent any

    tools {
        jdk 'Java22'
    }

    environment {
        AWS_REGION = 'eu-central-1'
        ECR_REGISTRY = credentials("AWS_ECR_REGISTRY_${AWS_REGION}")
        ECR_REPOSITORY_DBMS = "${ECR_REGISTRY}/database_management_service_project"
        ECR_REPOSITORY_USER = "${ECR_REGISTRY}/user_service_project"
        ECR_REPOSITORY_PAYMENT = "${ECR_REGISTRY}/payment_service_project"
        ECR_REPOSITORY_DELIVERY = "${ECR_REGISTRY}/delivery_service_project"
        ECR_REPOSITORY_GATEWAY = "${ECR_REGISTRY}/gateway_app_project"
    }

    stages {
        stage('Build and Push Docker Images') {
            steps {
                script {
                    docker.withRegistry("https://${ECR_REGISTRY}", "ecr:${AWS_REGION}:aws-credentials") {
                        docker.build("database_management_service_project", "./database-management-service").push('latest')
                        docker.build("user_service_project", "./user-service").push('latest')
                        docker.build("payment_service_project", "./payment-service").push('latest')
                        docker.build("delivery_service_project", "./delivery-service").push('latest')
						docker.build("gateway_app_project", "./gateway-app").push('latest')
                    }
                }
            }
        }

        stage('Deploy to ECS') {
            steps {
                script {
                    sh '''
                    aws cloudformation deploy \
                      --template-file ecs-fargate.yaml \
                      --stack-name parcelapp-stack \
                      --capabilities CAPABILITY_NAMED_IAM
                    '''
                }
            }
        }
    }

    post {
        always {
            deleteDir()
        }
    }
}