#!/bin/bash
# creating variables builds numbers and release versions
HOTFIX_NUMBER=$(cat ./app/version.properties | head -n 1 | tail -n 1 | sed -r 's/^[^0-9]*([0-9]+).*/\1/')
BUILD_NUMBER=$(cat ./app/version.properties | head -n 2 | tail -n 1 | sed -r 's/^[^0-9]*([0-9]+).*/\1/')
MINOR_VERSION=$(cat ./app/version.properties | head -n 3 | tail -n 1 | sed -r 's/^[^0-9]*([0-9]+).*/\1/')
MAJOR_VERSION=$(cat ./app/version.properties | head -n 4 | tail -n 1 | sed -r 's/^[^0-9]*([0-9]+).*/\1/')

#Add var in github env
#echo "{environment_variable_name}={value}" >> $GITHUB_ENV
echo "VERSION_NAME=$MAJOR_VERSION.$MINOR_VERSION.$BUILD_NUMBER.$HOTFIX_NUMBER" >> $GITHUB_ENV