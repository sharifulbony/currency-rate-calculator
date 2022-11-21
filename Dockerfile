FROM openjdk:11.0.10-slim
COPY build/libs/*.jar /app.jar
CMD ["java","-jar","/app.jar"]
EXPOSE 8080
