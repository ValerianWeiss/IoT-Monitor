#!/usr/bin/env bash

scriptDir=$(pwd)
cd "./vue-backend-auth"
gradle build -x test

cd $scriptDir

cd "./vue-backend-resources"
gradle build -x test

cd $scriptDir

cd "./vue-backend-websockets"
gradle build -x test

cd $scriptDir

cd "./vue-backend-registry"
gradle build -x test

cd $scriptDir

cd "./vue-backend-gateway"
gradle build -x test

cd $scriptDir


