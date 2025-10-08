# Import the base image
FROM amazoncorretto:21-alpine-jdk AS builder

# Label the authors
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a Working directory
WORKDIR '/app'

# Copy the jar from target folder to app folder
COPY target/*.jar api-gateway-server.jar

# Extract the Spring Boot fat jar as layers
RUN java -Djarmode=tools -jar api-gateway-server.jar extract --layers --launcher

FROM amazoncorretto:21-alpine-jdk

# Add CURL to the image as alpine doesn't support
RUN apk add --no-cache curl

WORKDIR '/app'

# Copy the layers from previous container to present container
COPY --from=builder app/api-gateway-server/dependencies/ ./
COPY --from=builder app/api-gateway-server/spring-boot-loader/ ./
COPY --from=builder app/api-gateway-server/snapshot-dependencies/ ./
COPY --from=builder app/api-gateway-server/application/ ./

# Provide the entry point for execution
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]