FROM openjdk:17
ADD /target/hometask-security-0.0.1-SNAPSHOT.jar hometask-security-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "hometask-security-0.0.1-SNAPSHOT.jar"]