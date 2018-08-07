FROM tomcat
WORKDIR /usr/local/tomcat/webapps
RUN [ "mkdir", "./ROOT/dist" ]
RUN [ "mkdir", "./ROOT/src" ]
COPY ./src/main.css ./ROOT/src
COPY ./dist ./ROOT/dist
COPY ./index.html ./ROOT