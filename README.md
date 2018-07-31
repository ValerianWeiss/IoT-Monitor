# IoT-Monitor
This is an example application for my studies. The project contains a Vue application which talks to a Spring Boot microservice. The microservice serves as an interface for a MySql database for the Vue app. The frontend communicates REST and STOMP websockets with the microservice.
The Vue application integrates Vuex and Vue Router and is written in Typescript.

The goal of the project is, to create a distributed system in which each part of the system is running in its own Docker container.

## How to build and run

### Required things
To build the project you have to install the following things.
* `java jdk` to build and run the Spring Boot application
* `gradle` to build the Spring Boot app (recommended version: 4.6).
* `npm` to build and run the Vue application.
* `nodejs` is used by `npm` to build the Vue app (recommended version: 8.11.1).
* `docker` to run the project in containers. (optional, you can run the application locally as well)
* `docker-compose` makes it easy to run the entire project locally and building the images. (optional, provides a fast way to build and start all containers)

### Build the project

> ##### Note:
> In the root folder of the project you can find a `buildAll.sh`. With this script you can build the entiere project

To build the Vue application, navigate into the vue-webapp directory and run
```bash
npm install
npm run build
```
This will build the entire web content with webpack. The bundled js files will be placed under <$ProjectDir>/vue-webapp/dist/

To build a Spring Boot microservice, navigate into a vue-backend-... directory. If you run now:
```bash
gradle build
```
The Spring Boot mircoservice will be build and all tests are getting executed. The .jar file will be placed in the <$ProjectDir>/vue-backend-.../build/libs/ folder.

> ##### Note:
> The database must already be running that the application passes all tests. You can just build the application without running any tests with:
> ```bash
> gradle build -x test
> ```

To run the application locally now, you have to start the MySql database, the Spring Boot microservices and the Vue app.
You can start them by hand or just execute the script `runAll.sh` from the project directory to run the entire application.
This will start the vue-webapp development server on *localhost:8080* and all microservices.

To run everything in docker containers on your local machine you have to build the entiere project and then navigate to <$ProjectDirectory> and run:
```bash
docker-compose up
```
Alternatively your can build the Docker files by hand and manage the containers on your own:
```bash
docker-compose build
```
To build all images.

