#!/bin/bash

# Checkout
git clone https://github.com/destroflyer/roblocks.git
if [ -n "$1" ]; then
  git checkout $1
fi

# Build
mvn clean install

# Deploy
rm -rf ${2}*
mv assets ${2}
mv target/libs ${2}
mv target/birds-1.0.0.jar ${2}Roblocks.jar
curl https://destrostudios.com:8080/apps/2/updateFiles