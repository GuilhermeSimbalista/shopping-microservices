FROM openjdk:17
VOLUME /tmp
EXPOSE 8082
ADD ./target/ms-order-0.0.1-SNAPSHOT.jar ms-order.jar
ENTRYPOINT ["java", "-jar", "/ms-order.jar"]