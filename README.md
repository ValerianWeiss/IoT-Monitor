# IoT-Monitor
This is an example application for my studies. It represents a web platform where you can add endpoints to your account. After you’ve added an endpoint the application is able to receive and display the data, the endpoint is sending to the api. A example HTTP request to add a data point could look like this:

*Url: <$Api-gateway-url>/data  
HTTP method: POST  
Request headers: Authorization: "Bearer eyJ0eXAiOiJsdaa1QiLCasdzI1NiJ9.eyJpasfYXQiOjEfYXQiOjE1M1MzMyM..."*

```json
{
  "endpointName": "Endpoint",
  "sensorName": "Sensor",
  "datapoint": {
      "value": 12.21,
      "time": 1533675296
  }
}
```

![Diagram of project structure](https://github.com/ValerianWeiss/IoT-Monitor/blob/auth/IoT-Monitor.PNG?raw=true)

On https://hub.docker.com/r/valerianhses/iot-monitor/ you can download the different parts of the system as docker images and run all the images locally. Make sure that you configure them the right way, when you start them. You can find a example configuration in the `docker-compose.yml`. For the database image you can just download the mysql image and configure it with the given configuration.

## Used Technologies
The project contains a Vue application (written in typescript) which talks over REST and STOMP websockets to several Spring Boot microservices. All data which has to be persisted is getting stored in a MySql database.
All different parts of the system are running in docker containers.

## How to build
### Required things
To build the project you have to install the following things.
* `java jdk` to build and run the Spring Boot application
* `gradle` to build the Spring Boot app (recommended version: 4.7).
* `npm` to build and run the Vue application.
* `nodejs` is used by `npm` to build the Vue app (recommended version: 8.11.1).
* `docker` to run the project in containers. (optional, you can run the application locally as well)
* `docker-compose` makes it easy to run the entire project locally and building the images. (optional, provides a fast way to build and start all containers)

### Build the project
In the root folder of the project you can find the `buildAll.sh` file. With this script you can build  the entire project. The script also executes all tests. 

> ##### Note:
> To pass all the tests, one microservice has to connect to the database and load the database model. That means you should have a Mysql > database running while the tests are getting executed, otherwise one test will fail.
> To skip the tests for the Spring projects you can run `gradle build -x test` 

You also can build every single part of the system manually as well. 

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
The Spring Boot microservice will be build and all tests are getting executed. The .jar file will be placed in the <$ProjectDir>/vue-backend-.../build/libs/ folder.


## How to run
To run the application locally without docker, you have to start the MySql database, the Spring Boot microservices and the Vue app.
You can start them by hand or just execute the script `runAll.sh` from the project directory to run the entire application.
This will start the vue-webapp development server on *localhost:8080* and all microservices.

To run everything in docker containers on your local machine, you have to build the entire project and then navigate to <$ProjectDirectory> and run:
```bash
docker-compose build
docker-compose up
```
Alternatively you can build the Docker files by hand and manage the containers on your own.
