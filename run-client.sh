#!/bin/bash

set -e

cd client/target

gunzip -c clase09-ejer01-client-1.0-SNAPSHOT-bin.tar.gz | tar xopf -
chmod +x clase09-ejer01-client-1.0-SNAPSHOT/run-client.sh

cd clase09-ejer01-client-1.0-SNAPSHOT/
./run-client.sh
