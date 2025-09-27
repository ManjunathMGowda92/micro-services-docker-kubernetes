# Import the base image required for JDK. (OpenJDK is deprecated, so using amazon corretto image)
FROM amazoncorretto:21.0.8-al2023-headless

# We can specify the maintainer for the image which we are going to build (MAINTAINER is deprecated, so use LABEL)
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a working Directory, So that files will not get conflicted.
WORKDIR '/app'

# Copy the jar from target folder to app folder
COPY target/*.jar accounts-service.jar

# Provide the entry point for execution
ENTRYPOINT ["java", "-jar", "accounts-service.jar"]