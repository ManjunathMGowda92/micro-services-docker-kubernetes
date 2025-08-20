# Import the base image for JDK.
FROM eclipse-temurin:21.0.8_9-jre-ubi10-minimal

# Label the maintainer
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a working Directory
WORKDIR '/app'

# Copy the jar from target folder to app folder
COPY target/cards-2.0.0.jar .

# Provide the entry point for execution
ENTRYPOINT ["java", "-jar", "cards-2.0.0.jar"]