#!/bin/bash
# replace build number in the file app/version.properties
CURRENT_BUILD_VERSION=$(cat ./app/version.properties | head -n 2 | tail -n 1 | sed -r 's/^[^0-9]*([0-9]+).*/\1/')
echo "Current GitHub run number: $GITHUB_RUN_NUMBER"
sed -i "s/BUILD_VERSION=$CURRENT_BUILD_VERSION/BUILD_VERSION=$GITHUB_RUN_NUMBER/g" ./app/version.properties