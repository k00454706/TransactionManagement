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
    The main class is org.hometask.transactionmanagement
4. Use API tool to test api

