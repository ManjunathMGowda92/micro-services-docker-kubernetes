# Import the base image
FROM amazoncorretto:21.0.8-al2023-headless

# Label the authors
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a Working directory
WORKDIR '/app'

# Copy the jar from target folder to app folder
COPY target/*.jar api-gateway-server.jar

# Provide the entry point for execution
ENTRYPOINT ["java", "-jar", "api-gateway-server.jar"]