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

5. Use Swagger UI to test API
   
   http://localhost:8080/swagger-ui/index.html
   
#### Create a new transaction  
![e94d565898907c48e13809692ffab7f](https://github.com/user-attachments/assets/7a58fd71-d002-4f32-b5a3-87c603245303)
![8da5a02433c6e2cc02a75e242009800](https://github.com/user-attachments/assets/ddfdf809-0ba2-48c9-bc45-6b92caa9b2a0)

#### Create a duplicate transaction
![1736643475891](https://github.com/user-attachments/assets/c3a39d58-047d-4134-8e35-0709933d12f3)
![318695f46913c87dcc8bc9e7ab077f1](https://github.com/user-attachments/assets/315b5cb8-bedb-47bf-abb9-2dfef1d592a2)

#### Modify a transaction
![1736644128849](https://github.com/user-attachments/assets/367b70da-df9d-4b4f-b344-de6cae632c4d)
![39aa98942bc73b467eb257ae4de9b38](https://github.com/user-attachments/assets/c635f455-278e-44b1-919f-acb52b1ccc87)

#### Modify a non-exist transaction
![ec1c411615a5141ead53854ca0c73f9](https://github.com/user-attachments/assets/1ad90983-d47e-4941-a9fa-a18b6bb4a1b6)
![61967ec54c0e42feb0d14f281087664](https://github.com/user-attachments/assets/80b01683-113d-4ddf-be54-6147a4460994)

#### Get all transactions
![image](https://github.com/user-attachments/assets/56657b57-75b3-4a92-94dc-da327ca65b86)
![3dccdee02e23c1c77e1d21d90899eac](https://github.com/user-attachments/assets/37088cd3-df96-4ae2-ab6e-23511813f1cd)

#### Get all transactions after creating another 2 transactions
![c6fe584c2c04e02e9f9311e31a4b2bf](https://github.com/user-attachments/assets/7d6e4108-364a-49f0-979c-c06ad924f115)




