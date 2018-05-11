FROM tomcat
WORKDIR /usr/local/tomcat/webapps
#TODO: Says directory is not existing
#RUN [ "rm", "-r", "./ROOT/*"]
RUN [ "mkdir", "./ROOT/dist" ]
COPY ./index.html ./ROOT
COPY ./dist ./ROOT/dist