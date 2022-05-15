# Start Zipkin
To run zipkin server follow quick quide here

Docker - `docker-compose up -d zipkin` from root.

or

Download and run (https://zipkin.io/pages/quickstart)
```
curl -sSL https://zipkin.io/quickstart.sh | bash -s

java -jar zipkin.jar
```
# Services

* Eureka - port 8761
* Gateway - port 8080
* Portal-service - port 8081
* Customer-service - port 8082
* Address-service - port 8083

