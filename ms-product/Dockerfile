FROM openjdk:17
VOLUME /tmp
EXPOSE 8081
ADD ./target/ms-product-0.0.1-SNAPSHOT.jar ms-product.jar
ENTRYPOINT ["java", "-jar", "/ms-product.jar"]