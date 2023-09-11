# Shopping Microservices

The challenge is to implement a microservices-based system using Spring Boot. Each microservice is designed to be autonomous, focused on a single responsibility, and can be deployed and scaled independently of the others.

## Technologies Used

- Spring Boot
- Spring Cloud Gateway
- Spring Security
- Feign Client
- RabbitMQ
- JWT
- Swagger UI
- Docker

## Microservices

- **ms-auth**: Responsible for authenticating users and generating JWT tokens.
- **ms-user**: Manage user information such as registration, profile and user details.
- **ms-product**: Responsible for product management, including creating, querying and updating products.
- **ms-order**: Handles customer orders, payment processing and order history.

## Authentication and User Microservices

The project was developed with the intention of using good architecture practices, where a user microservice was created to manage users and become independent, so the authentication microservice makes a query by Feign Client in ms-user to verify if the user has a registration.

## Product Microservices

The Product Microservice performs the CRUD of products and saves them in the database.

## Order Microservices

The orders microservice registers orders and sends a message via RabbitMQ to the product microservice to check if the item is available, if so, it returns a message and then creates the order. To list the product details, it performs a search in the product microservice using Feign Client, and returns a list with the product details.

## Api-Gateway 

Aapi-gateway centralizes all endpoints to get access from one route only. It needs a valid token that can be generated in /auth/login to grant access.

## Run Application


To run the application you need to download or pull the repository in the main branch and open the command prompt in the project folder and type the command below. So it will upload all containers.

**docker-compose up -d --build**

### **Remember before running the containers, open windows services and disable the postgresql service to only docker**

To work completely you will only need to create the tables in the database and populate the tables. To do this you can open pgAdmin select each database take the code that is in the **create table.txt** file and paste it, do this for each databases in the file it is detailed which one to use in each database and in the branch tests there is a folder with images of how to do it

The postman collection is also available.

In the tests branch, a folder with the images of the requests is available


## Environment 

RabbitMQ user: guest
RabbitMQ password: guest

PostgreSQL user: postgre
PostgreSQL Password: postgre





