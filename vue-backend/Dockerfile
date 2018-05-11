FROM openjdk:8
WORKDIR /app
EXPOSE 8080 3306
COPY ./build/libs/vue-backend-0.0.1.jar .
CMD ["java", "-jar", "./vue-backend-0.0.1.jar"]