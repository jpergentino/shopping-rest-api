FROM openjdk:8

RUN mkdir /tmp/app

COPY ./*.jar /tmp/app

WORKDIR /tmp/app

CMD ["java", "-jar", "shopping-1.0-SNAPSHOT.jar"]

EXPOSE 8080