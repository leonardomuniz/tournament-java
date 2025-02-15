FROM openjdk:21-jdk-slim

RUN mkdir /app

WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 8080
EXPOSE 5005 

CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app/app.jar"]
