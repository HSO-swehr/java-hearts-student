#!/bin/bash

echo "Running unit tests ..."
./gradlew test || exit 1
echo "Done"
echo
echo

echo "Running local game ..."
./gradlew runLocalGame --console=plain --args="AI AI AI AI" || exit 1
echo "Done"
echo
echo

echo "Running network game ..."
python3 scripts/check-network.py || exit 1
echo "Done"
