FROM openjdk:11
ADD target/adr-waybill-0.0.1.jar app.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "app.jar"]
