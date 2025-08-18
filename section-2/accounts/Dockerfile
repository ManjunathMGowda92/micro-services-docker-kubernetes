# Import the base image required for JDK. (OpenJDK is deprecated, so using amazon corretto image)
FROM amazoncorretto:21-alpine-jdk

# We can specify the maintainer for the image which we are going to build (MAINTAINER is deprecated, so use LABEL)
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a working Directory, So that files will not get conflicted.
WORKDIR '/app'

# Copy the jar from target folder to app folder
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-1.0.0.jar

# Provide the entry point for execution
ENTRYPOINT ["java", "-jar", "accounts-1.0.0.jar"]