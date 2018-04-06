# Support Services

This Gradle Project is based on Spring Boot and Netfilx OSS. It contains the following components

* Config Server
* Discovery Server
* Edge Server
* Turbine Server
* Hystrix
* Auth Server 

This Project also contains the following DB Docker Containers

* Mysql
* Mongo
* Kafka

This Project also contains the following CI/CD Docker Containers

* Jenkins
* Nexus
* GitLab
* Sonar (TBD)

To import the project, open the config folder from Eclipse/ IntelliJ

To compile the code and run, execute ./build-all.sh from the config folder

To run the docker containers run docker-compose scale <service-name>=1
