#!/bin/bash

VERSION=$1
CLIENT=$2

# Checkout
git clone https://github.com/destroflyer/roblocks.git
if [ -n "$VERSION" ]; then
  git checkout "$VERSION"
fi

# Build
mvn clean install

# Deploy
rm -rf "${CLIENT}"*
mv assets "${CLIENT}"
mv target/libs "${CLIENT}"
mv target/roblocks-1.0.0.jar "${CLIENT}Roblocks.jar"
curl -X POST https://destrostudios.com:8080/apps/2/updateFiles