FROM openjdk:11
ADD target/adr-waybill.jar app.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "app.jar"]
