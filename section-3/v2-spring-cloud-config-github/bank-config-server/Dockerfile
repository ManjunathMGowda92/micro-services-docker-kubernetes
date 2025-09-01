# Add base image for Java
FROM amazoncorretto:21.0.8

# Specify the author details
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a working directory, so that files will not get conflicted.
WORKDIR '/app'

# Copy the jar from target folder to app folder
COPY target/*.jar bank-config-server.jar

# provide the entry point for the execution
ENTRYPOINT ["java", "-jar", "bank-config-server.jar"]