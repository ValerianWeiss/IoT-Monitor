#!/usr/bin/env bash

scriptDir=$(pwd)


cd "./vue-backend-registry/build/libs"
java -jar vue-backend-registry-0.0.1.jar &

cd $scriptDir

cd "./vue-backend-auth/build/libs"
java -jar vue-backend-auth-0.0.1.jar &

cd $scriptDir

cd "./vue-backend-resources/build/libs"
java -jar vue-backend-recources-0.0.1.jar &

cd $scriptDir

cd "./vue-backend-websockets/build/libs"
java -jar vue-backend-websockets-0.0.1.jar &

cd $scriptDir

cd "./vue-webapp"
npm run dev
