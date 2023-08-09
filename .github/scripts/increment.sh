#!/bin/bash
# creating variables builds numbers and release versions
BUILD_NUMBER=$(cat ./app/version.properties | head -n 1 | tail -n 1 | sed -r 's/^[^0-9]*([0-9]+).*/\1/')
BUILD_NUMBER=$((BUILD_NUMBER+1))
#replace build number in the file app/version.properties
REPLACE=$((BUILD_NUMBER-1))
sed -i "s/BUILD_VERSION=$REPLACE/BUILD_VERSION=$BUILD_NUMBER/g" ./app/version.properties