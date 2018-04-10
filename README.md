# Support Services

This Gradle Project is based on Spring Boot and Netfilx OSS. It contains the following components

* Config Server (Port: 8888)
* Discovery Server (Port: 8761)
* Edge Server (Port: 8765)
* Hystrix + Turbine Server (Port: 7979)
* Auth Server (Port: 9090)

This Project also contains the following DB Docker Containers

* Mysql (Port: 3306)
* Mongo
* Kafka

This Project also contains the following CI/CD Docker Containers

* Jenkins (Port: 18080)
* Nexus (Port: 18081)
* GitLab (Port: 8082)
* Sonar (Port: 9000)

This Project also contains the following Containers to support enterprise level logging
* Elasticsearch (Port: 9200)
* Logstash (Port: 5000)
* Kibana (Port: 5601)

To import the project, open the config folder from Eclipse/ IntelliJ

To compile the code and run, execute ./build-all.sh from the config folder

To run the docker containers run docker-compose scale <service-name>=1
