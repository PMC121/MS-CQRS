FROM openjdk:8
EXPOSE 8082
ADD target/spring-gateway-server.jar spring-gateway-server.jar
ENTRYPOINT ["java","-jar","/spring-gateway-server.jar"]