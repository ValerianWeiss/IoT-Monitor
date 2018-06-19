#!/usr/bin/env bash

scriptDir=$(pwd)
cd "./vue-backend-auth"
gradle build 

cd $scriptDir

cd "./vue-backend-resources"
gradle build

cd $scriptDir

cd "./vue-backend-websockets"
gradle build

cd $scriptDir

cd "./vue-webapp"
npm install
npm run build
