# Specify the base image for Java.
FROM amazoncorretto:21-alpine-jdk AS builder

# Label the authors information
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a working directory
WORKDIR '/app'

# Copy the jar from target folder to app directory
COPY target/*.jar loans-service.jar

# Extract the Spring Boot fat jar as layers
RUN java -Djarmode=tools -jar loans-service.jar extract --layers --launcher

FROM amazoncorretto:21-alpine-jdk

# Add CURL to the image as alpine doesn't support
RUN apk add --no-cache curl

WORKDIR '/app'

# Copy the layers from previous container to present container
COPY --from=builder app/loans-service/dependencies/ ./
COPY --from=builder app/loans-service/spring-boot-loader/ ./
COPY --from=builder app/loans-service/snapshot-dependencies/ ./
COPY --from=builder app/loans-service/application/ ./

# provide the entry point for execution
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]