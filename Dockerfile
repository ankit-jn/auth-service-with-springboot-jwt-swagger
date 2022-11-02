FROM openjdk:8
ARG JAR_FILE=target/auth-service-1.0.0.jar
ADD ${JAR_FILE} /home/auth-service-1.0.0.jar
WORKDIR /home
EXPOSE 8080
CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseG1GC", "-XX:+UseStringDeduplication", "-XX:MaxRAMFraction=2", "-jar", "auth-service-1.0.0.jar"]
