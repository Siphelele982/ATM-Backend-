# ATM Backend

A backend system for an ATM application built with Java and Spring Boot.

1.Features
- Create and manage accounts
- View transaction history
- Transfer funds between accounts
- Handle errors with custom exceptions

2.Tech Stack.
- Java 17+
- Spring Boot
- Maven
- JPA/Hibernate

3.How to Run

 Clone the repo:
   ```bash
   git clone https://github.com/Siphelele982/ATM-Backend-.git
   cd ATM-Backend-

Configure your database in:
src/main/resources/application.properties
(add your own DB username, password, and connection url)


Build and run:
mvn clean install
mvn spring-boot:run

API Endpoints:
GET /api/accounts = get all accounts
GET /api/accounts/{accNum}/transactions = get all transaction history
GET/api/accounts/{accNum} = get account by account number
POST /api/accounts/transfer = Transfer funds
POST / api/accounts/deposit = Deposit  funds
POST /api/accounts/withdraw = withdraw funds
