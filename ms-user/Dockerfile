FROM openjdk:17
VOLUME /tmp
EXPOSE 8084
ADD ./target/ms-user-0.0.1-SNAPSHOT.jar ms-user.jar
ENTRYPOINT ["java", "-jar", "/ms-user.jar"]