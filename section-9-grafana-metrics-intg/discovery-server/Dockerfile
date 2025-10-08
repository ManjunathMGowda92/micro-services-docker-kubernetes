# Add base image for JDK
FROM amazoncorretto:21-alpine-jdk AS builder

# Specify the maintainer of image
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a working directory
WORKDIR '/app'

# COPY the jar from target folder to app folder
COPY target/*.jar discovery-server.jar

#Extract the Spring Boot fat jar as layers
RUN java -Djarmode=tools -jar discovery-server.jar extract --layers --launcher

FROM amazoncorretto:21-alpine-jdk

# Add CURL to the image as alpine doesn't support
RUN apk add --no-cache curl

WORKDIR '/app'

# Copy the layers from previous container to present container
COPY --from=builder app/discovery-server/dependencies/ ./
COPY --from=builder app/discovery-server/spring-boot-loader/ ./
COPY --from=builder app/discovery-server/snapshot-dependencies/ ./
COPY --from=builder app/discovery-server/application/ ./

# Provide the entry point for execution
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]