#!/usr/bin/env bash

scriptDir=$(pwd)

echo %scriptDir%

cd "./vue-backend-registry/build/libs"
terminal "java -jar vue-backend-registry-0.0.1.jar"

cd $scriptDir

cd "./vue-backend-auth/build/libs"
terminal "java -jar vue-backend-auth-0.0.1.jar"

cd $scriptDir

cd "./vue-backend-resources/build/libs"
terminal "java -jar vue-backend-recources-0.0.1.jar"

cd $scriptDir

cd "./vue-backend-websockets/build/libs"
terminal "java -jar vue-backend-websockets-0.0.1.jar"

cd $scriptDir

cd "./vue-backend-gateway/build/libs"
terminal "java -jar vue-backend-gateway-0.0.1.jar"

cd $scriptDir

cd "./vue-webapp"
terminal "npm run dev"

cd $scriptDir