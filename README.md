# Vue-Application
This is an example application for my studies. The project contains a Vue application which talks to a Spring Boot microservice. The microservice serves as an interface for a MySql database for the Vue app. The frontend communicates REST and STOMP websockets with the microservice.
The Vue application integrates Vuex and Vue Router and is written in Typescript.

The goal of the project is, to create a distributed system in which each part of the system is running in its own Docker container.

## How to build and run

### Required things
To build the project you have to install the following things
* `java jdk` to build and run the
* `gradle` to build the Spring Boot app (I'm using version 4.6).
* `npm` to build and run the Vue application.
* `nodejs` is used by `npm` to build the Vue app (I'm using version 8.11.1).
* `docker` to run the project in containers. (optional, you can run the application locally as well)
* `docker-compose` makes it easy to run the entire project locally and building the images. (optional, provides a fast way to build and start all containers)

### Build the project
To build the Vue application, navigate into the vue-webapp directory and run
```bash
npm install
npm run build
```
This will build the entire web content with webpack. The bundled js files will be placed under <$ProjectDir>/vue-webapp/dist/

To build the Spring Boot microservice, navigate into the vue-backend directory. If you run now:
```bash
gradle build
```
The Spring Boot app will be build and all tests executed. The .jar will be in the <$ProjectDir>/vue-backend/build/libs/ folder.

> ##### Note:
> The database must already be running that all tests pass. You can just build the application without running any tests with:
> ```bash
> gradle build -x test
> ```

To run the application locally now, you have to start the MySql database, the Spring Boot app and the Vue app.
To start the Spring Boot app navigate to the vue-backend folder and run:
```bash
java -jar /build/libs/vue-backend-0.0.1.jar
```
To run the Vue application navigate into the vue-webapp folder and run:
```bash
npm run dev
```
This will start a development server on *localhost:8080*. The server will update your webapp when you save any file which belongs to it.

To run everything in docker containers on your local machine you have to build the Vue- and the Spring-app and then navigate to the <$ProjectDirectory> and run:
```bash
docker-compose up
```
Alternatively your can build the Docker files by hand, or run:
```bash
docker-compose build
```
To build all images.

