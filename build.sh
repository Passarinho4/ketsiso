#!/bin/bash

gradle clean
gradle jar

unzip  -o -q simulationlauncher/build/libs/simulationlauncher.jar -d ./simulationlauncher/build/libs/simulationlauncher

cp -r simulationlauncher/build/libs/simulationlauncher scripts/backend

cp simulationlaunchergui/index.html scripts/backend/

cd scripts/simulationlauncher

docker build -t ketsiso .

docker tag ketsiso passarinho/ketsiso

docker push passarinho/ketsiso

cd ..
cd ..
gradle clean
rm -rf scripts/backend/simulationlauncher
rm -f scripts/backend/index.html