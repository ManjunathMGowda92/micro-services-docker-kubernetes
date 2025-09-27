# Specify the base image for Java.
FROM amazoncorretto:21.0.8-al2023-headless

# Label the authors information
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a working directory
WORKDIR '/app'

# Copy the jar from target folder to app directory
COPY target/*.jar loans-service.jar

# provide the entry point for execution
ENTRYPOINT ["java", "-jar", "loans-service.jar"]