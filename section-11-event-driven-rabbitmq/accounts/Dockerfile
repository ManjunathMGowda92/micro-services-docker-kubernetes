# Import the base image required for JDK. (OpenJDK is deprecated, so using amazon corretto image)
FROM amazoncorretto:21-alpine-jdk AS builder

# We can specify the maintainer for the image which we are going to build (MAINTAINER is deprecated, so use LABEL)
LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

# Create a working Directory, So that files will not get conflicted.
WORKDIR '/app'

# Copy the jar from target folder to app folder
COPY target/*.jar accounts-service.jar

# Extract the Spring Boot fat jar as layers
RUN java -Djarmode=tools -jar accounts-service.jar extract --layers --launcher

FROM amazoncorretto:21-alpine-jdk

# Add CURL to the image as alpine doesn't support
RUN apk add --no-cache curl

WORKDIR '/app'

# Copy the layers from previous container to present container
COPY --from=builder app/accounts-service/dependencies/ ./
COPY --from=builder app/accounts-service/spring-boot-loader/ ./
COPY --from=builder app/accounts-service/snapshot-dependencies/ ./
COPY --from=builder app/accounts-service/application/ ./

# Provide the entry point for execution with JarLauncher
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]