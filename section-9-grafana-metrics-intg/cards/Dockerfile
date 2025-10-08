# Import the base image for JDK.
FROM amazoncorretto:21-alpine-jdk AS builder

# Label the maintainer
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a working Directory
WORKDIR '/app'

# Copy the jar from target folder to app folder
COPY target/*.jar cards-service.jar

# Extract the Spring Boot fat jar as layers
RUN java -Djarmode=tools -jar cards-service.jar extract --layers --launcher

FROM amazoncorretto:21-alpine-jdk

# Add CURL to the image as alpine doesn't support
RUN apk add --no-cache curl

WORKDIR '/app'

# Copy the layers from previous container to current container
COPY --from=builder app/cards-service/dependencies/ ./
COPY --from=builder app/cards-service/spring-boot-loader/ ./
COPY --from=builder app/cards-service/snapshot-dependencies/ ./
COPY --from=builder app/cards-service/application/ ./

# Provide the entry point for execution
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]