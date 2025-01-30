# Short Link Generator
----------------------------------------------------
# api (backend)

## Features
* Generating short links
* Link redirection
* User registration and login with JWT authentication
* Password encryption using BCrypt
* Role-based authorization with Spring Security
* Customized access denied handling
* Logout mechanism
* Refresh token

## Technologies
* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* Flyway
* Swagger

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+
--------------------------------------------------------
To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/IvannikovS/short-link-generator.git`
* Navigate to the project directory: cd short-link-generator\api
* Add database "dsr" to postgres
* Provide your database data in the configuration file 
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run

-> The application will be available at http://localhost:8080.

---------------------
# client (frontend)

## Prerequisites
* Node.js
* npm (Node Package Manager)

To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/IvannikovS/short-link-generator.git`
* Navigate to the project directory: cd short-link-generator\client
* Build the project: npm install --legacy-peer-deps
* Run the project: npm start

-> The application will be available at http://localhost:3000.
