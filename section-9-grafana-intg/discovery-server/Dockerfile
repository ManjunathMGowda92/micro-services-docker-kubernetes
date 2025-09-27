# Add base image for JDK
FROM amazoncorretto:21.0.8-al2023-headless

# Specify the maintainer of image
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a working directory
WORKDIR '/app'

# COPY the jar from target folder to app folder
COPY target/*.jar discovery-server.jar

# Provide the entry point for execution
ENTRYPOINT ["java", "-jar", "discovery-server.jar"]