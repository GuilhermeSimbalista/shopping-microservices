FROM openjdk:17
VOLUME /tmp
EXPOSE 8083
ADD ./target/ms-auth-0.0.1-SNAPSHOT.jar ms-auth.jar
ENTRYPOINT ["java", "-jar", "/ms-auth.jar"]