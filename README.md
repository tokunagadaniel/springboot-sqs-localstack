# springboot-sqs-localstack
Spring boot application integrated with sqs and localstack

1. First one we need run docker-compose to create local dynamodb and tables.
   1. docker-compose up -d

2. Run application.
   1. maven install
   2. java -jar springboot-sqs-localstack

3. Go to /postman and import collection to tests.