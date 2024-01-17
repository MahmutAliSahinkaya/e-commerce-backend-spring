# E-Commerce-App Spring Boot Microservices Project

![E-Commerce-App_diagram.png](diagrams%2FE-Commerce-App_diagram.png)

## About the Project

This project represents the API layer of an E-Commerce application developed using Java, Spring Boot, Maven, and SQL technologies. The APIs cover various functionalities such as user operations, product management, order processing, payment transactions, shipping operations, and notification services.


### Project Structure

The project follows a microservices architecture, where each microservice operates independently with its own database. The microservices utilized in this project include:

- **Config Server**: Holds configuration information for microservices, retrieved from GitHub.
- **Eureka Server**: Acts as a discovery service for microservices to register and obtain information about others.
- **User Service**: Manages user operations and provides APIs for user addresses.
- **Product Service**: Handles product operations and category management.
- **Order Service**: Oversees order processing, including cart management.
- **Payment Service**: Manages payment transactions and verifications.
- **Shipping Service**: Controls shipping operations and shipment tracking.
- **Favourite Service**: Allows users to manage their favorite products.
- **Report Service**: Generates order reports within specified date ranges.
- **Notification Service**: Sends notifications to users about payment statuses.
- **API Gateway**: Directs requests to appropriate microservices and performs security checks.

Each service performs specific functions and contributes to the overall operation of the project.

### Docker Hub
[Docker Hub Link](https://hub.docker.com/search?q=mahmutali)

### Git Backend for Config Server
[Config Server Repository](https://github.com/MahmutAliSahinkaya/config-server-repo)

## Technologies and Tools Used
- Spring Boot & Spring Cloud
- Java 17
- Spring Security
- MySQL
- Redis
- Kafka
- Keycloak
- Grafana Loki (Read and Write)
- Promtail
- Minio
- Prometheus
- Grafana
- Tempo
- Nginx (Gateway)


### Prerequisites

- Java 17
- Maven
- Docker
- Docker Compose

## Setup

Follow these steps to set up and run the project.


### Downloading the Project

Clone the project from GitHub using the following command:

```shell
git clone https://github.com/MahmutAliSahinkaya/e-commerce-backend-spring.git
```
<b>Docker</b>

<b>1)</b> Install <b>Docker Desktop</b>. Follow the installation guides for Windows or Mac.

<b>2)</b> Build all <b>images</b> and push to <b>Docker Hub</b>.

Build and push Docker images for each microservice

The project is containerized with Docker and orchestrated with Docker Compose. Once Docker and Docker Compose are 
installed, you can bring up the project with the following command:

``` 
    1 ) eureka-server
        - mvn compile jib:dockerBuild
        - docker image push docker.io/mahmutali/eureka-server:v1
        
    2 ) config-server
     
        - mvn compile jib:dockerBuild
        - docker image push docker.io/mahmutali/config-server:v1
    
    3 ) api-gateway
     
        - mvn compile jib:dockerBuild
        - docker image push docker.io/mahmutali/api-gateway:v1
    
    4 ) user-service
     
        - mvn compile jib:dockerBuild
        - docker image push docker.io/mahmutali/user-service:v1
        
    5 ) product-service
     
        - mvn compile jib:dockerBuild
        - docker image push docker.io/mahmutali/product-service:v1
        
    6 ) order-service
     
        - mvn compile jib:dockerBuild
        - docker image push docker.io/mahmutali/order-service:v1
        
    7 ) payment-service
     
        - mvn compile jib:dockerBuild
        - docker image push docker.io/mahmutali/payment-service:v1
        
    8 ) shipping-service
     
        - mvn compile jib:dockerBuild
        - docker image push docker.io/mahmutali/shipping-service:v1

    9 ) favourite-service
     
        - mvn compile jib:dockerBuild
        - docker image push docker.io/mahmutali/favourite-service:v1

    10 ) report-service
     
        - mvn compile jib:dockerBuild
        - docker image push docker.io/mahmutali/report-service:v1

    11 ) notification-service
     
        - mvn compile jib:dockerBuild
        - docker image push docker.io/mahmutali/notification-service:v1
```
<b>3)</b> Run the project with Docker Compose:

```shell
docker-compose up
```
Add the '-d' parameter to run the services in the background.

<b>4 )</b> Send requests to any service using request collections under [E-CommerceApp API.postman_collection.json](E-CommerceApp%20API.postman_collection.json)


## Monitoring the Microservices
Check the status of all running Docker containers:

```shell
docker ps
```

## License
This project is licensed under the MIT License. For more information, please refer to the [LICENSE]([https://github.com/MahmutAliSahinkaya/e-commerce-backend-spring/blob/master/LICENSE)])

## Contributing
Contributions to the project are welcome. For more details, please refer to the CONTRIBUTING file.

## Contact
[LinkedIn](https://linkedin.com/in/mahmutalisahinkaya)

