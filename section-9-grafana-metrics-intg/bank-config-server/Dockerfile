# Add base image for Java
FROM amazoncorretto:21-alpine-jdk AS builder

# Specify the author details
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a working directory, so that files will not get conflicted.
WORKDIR '/app'

# Copy the jar from target folder to app folder
COPY target/*.jar bank-config-server.jar

# Extract the Spring Boot fat jar as layers
RUN java -Djarmode=tools -jar bank-config-server.jar extract --layers --launcher

FROM amazoncorretto:21-alpine-jdk

RUN apk add --no-cache curl

WORKDIR '/app'

# Copy the layers from previous container to present container
COPY --from=builder app/bank-config-server/dependencies/ ./
COPY --from=builder app/bank-config-server/spring-boot-loader/ ./
COPY --from=builder app/bank-config-server/snapshot-dependencies/ ./
COPY --from=builder app/bank-config-server/application/ ./

# provide the entry point for the execution with JarLauncher
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]