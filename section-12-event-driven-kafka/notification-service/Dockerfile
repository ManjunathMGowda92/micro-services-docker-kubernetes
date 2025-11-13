FROM amazoncorretto:21-alpine-jdk AS builder

LABEL org.fourstack.image.authors="manjunathcs052@gmail.com"

WORKDIR '/app'

COPY target/*.jar notification-service.jar

RUN java -Djarmode=tools -jar notification-service.jar extract --layers --launcher

FROM amazoncorretto:21-alpine-jdk

RUN apk add --no-cache curl

WORKDIR '/app'

COPY --from=builder app/notification-service/dependencies/ ./
COPY --from=builder app/notification-service/spring-boot-loader/ ./
COPY --from=builder app/notification-service/snapshot-dependencies/ ./
COPY --from=builder app/notification-service/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]