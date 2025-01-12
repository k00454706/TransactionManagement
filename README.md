# Transaction Management System

A RESTful web service built with Spring Boot that provides transaction management capabilities.

## Overview

This project implements a transaction management system that allows users to create, modify, delete, and list transactions through RESTful APIs.

## Features

- Create new transactions
- Modify existing transactions
- Delete transactions by ID
- List all transactions
- Error handling for duplicate transactions and non-existent transactions

## Technologies

- Java
- Spring Boot
- Spring Web
- RESTful APIs

## API Endpoints

### Transaction Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/transaction` | Create a new transaction |
| PUT | `/api/v1/transaction/{id}` | Modify an existing transaction |
| DELETE | `/api/v1/transaction/{id}` | Delete a transaction |
| GET | `/api/v1/transaction/list` | List all transactions |

## API Response Codes

| Status Code | Description |
|-------------|-------------|
| 200 | Success |
| 400 | Bad Request - Invalid input parameters |
| 404 | Not Found - Transaction doesn't exist |
| 409 | Conflict - Duplicate transaction |
| 500 | Internal Server Error |

## Getting Started

### Prerequisites

- Java JDK 21
- Maven
- Your favorite IDE (IntelliJ IDEA, Eclipse, etc.)

### How to use

1. Clone the repository
2. Use IntelliJ IDEA open this project as a maven project
3. Start the project
   ![1736642354867](https://github.com/user-attachments/assets/7204d206-a9b2-4904-911b-cf701f3e92fe)

4. Use Swagger UI to test API
   
   http://localhost:8080/swagger-ui/index.html
   ![426c73bb619afe5bd08c5aaab2b74ab](https://github.com/user-attachments/assets/38d33cbf-7713-45c3-8205-8471eeb64150)

   
#### 4.1 Creating a new transaction  
![e94d565898907c48e13809692ffab7f](https://github.com/user-attachments/assets/7a58fd71-d002-4f32-b5a3-87c603245303)
![8da5a02433c6e2cc02a75e242009800](https://github.com/user-attachments/assets/ddfdf809-0ba2-48c9-bc45-6b92caa9b2a0)

#### 4.2 Creating a duplicate transaction
![1736643475891](https://github.com/user-attachments/assets/c3a39d58-047d-4134-8e35-0709933d12f3)
![318695f46913c87dcc8bc9e7ab077f1](https://github.com/user-attachments/assets/315b5cb8-bedb-47bf-abb9-2dfef1d592a2)

#### 4.3 Modifing a transaction
![1736644128849](https://github.com/user-attachments/assets/367b70da-df9d-4b4f-b344-de6cae632c4d)
![39aa98942bc73b467eb257ae4de9b38](https://github.com/user-attachments/assets/c635f455-278e-44b1-919f-acb52b1ccc87)

#### 4.4 Modifing a non-exist transaction
![ec1c411615a5141ead53854ca0c73f9](https://github.com/user-attachments/assets/1ad90983-d47e-4941-a9fa-a18b6bb4a1b6)
![61967ec54c0e42feb0d14f281087664](https://github.com/user-attachments/assets/80b01683-113d-4ddf-be54-6147a4460994)

#### 4.5 Getting all transactions
![image](https://github.com/user-attachments/assets/56657b57-75b3-4a92-94dc-da327ca65b86)
![3dccdee02e23c1c77e1d21d90899eac](https://github.com/user-attachments/assets/37088cd3-df96-4ae2-ab6e-23511813f1cd)

#### 4.6 Getting all transactions after creating another 2 transactions
![c6fe584c2c04e02e9f9311e31a4b2bf](https://github.com/user-attachments/assets/7d6e4108-364a-49f0-979c-c06ad924f115)

#### 4.7 Deleting a transaction
![eda075269eb627371d51ba181d29db3](https://github.com/user-attachments/assets/63fbd8e3-8dcb-44f4-9c98-78b86efc3043)
![1736645448152](https://github.com/user-attachments/assets/03f7667b-7094-4af2-82ad-0f78e9ff22a4)
![1736645540013](https://github.com/user-attachments/assets/e6f71968-5efb-4d67-92ce-2014c489f651)

#### 4.8 Deleting a non-exist transaction
![image](https://github.com/user-attachments/assets/3e04a684-065f-4e4e-a85d-7219ff07a308)
![25c82fbd5ddb91a1f9a6503386545b0](https://github.com/user-attachments/assets/83a542da-39a9-4197-a1fd-9cdc7fa57053)


#### 4.9 Deleting a invalid transaction
![9a1ed5eeb252d82f49c2df49e944f4c](https://github.com/user-attachments/assets/73b74acc-636d-48a3-a457-b9812b7251dc)
![image](https://github.com/user-attachments/assets/dc0a3494-d2f2-4272-932b-f44816b62786)

### Testing
#### TransactionControllerTest.class
This test suite validates the functionality of the Transaction Management API endpoints using Spring Boot's testing framework.All tests passed.
![1736647936373](https://github.com/user-attachments/assets/083bb9d4-88cc-4c8d-8d5b-6f47acbb5e3d)

#### TransactionApiLoadTest.java
This load test suite evaluates the performance and reliability of the Transaction Management API under concurrent load conditions using Spring Boot's testing framework.
##### Test Configuration
- **Test Environment**: Spring Boot Test with Random Port
- **Client**: TestRestTemplate for HTTP requests
- **Concurrency**: Multi-threaded execution using ExecutorService
##### Load Test Parameters
- Number of Threads: 100
- Requests per Thread: 10
- Total Requests: 1,000
- Timeout: 2 minutes
- Success Rate Threshold: 100%
##### Test Scenario: Transaction Creation
The test creates multiple transactions concurrently with the following characteristics:
- Random transaction numbers (prefixed with "TEST-")
- Fixed amount (100.0)
- Concurrent POST requests to `/api/v1/transaction`
##### Sample Output
![3fa0d22b6d71e5dc7c4779a564bccdd](https://github.com/user-attachments/assets/b12aedfe-c2fd-422f-b6b3-de7921aedf4f) 

#### TransactionRepositoryTest.java
This test suite validates the functionality of the Transaction Repository layer, ensuring proper data storage, retrieval, and manipulation operations.All tests passed.
![c0d456dafe07f49357b6aee7b7a466d](https://github.com/user-attachments/assets/4fdd9def-cf91-4eed-a38c-a801f792350b)

#### TransactionServiceImplTest.java
This test suite validates the business logic of the Transaction Service implementation using JUnit 5 and Mockito for mocking dependencies.

### External Libraires outside the standard JDK
##### 1. com.fasterxml.jackson.datatype.jackson-datatype-jsr310
Used to soleve a error in TransactionControllerTest.class. Related code is in line 48 and 49:
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESAMPS);
Without these two line code, there will be a error.
##### 2. org.apache.maven.plugins.maven-assembly-plugin
Used to package the progject
#### 3. org.springdoc.springdoc-openapi-starter-webmvc-ui
Used to surpport springdoc-openaip.So we can view and test APIs on browser
#### 4. org.springframework.boot.spring-boot-starter-test
Used to support testing


