#!/bin/bash

set -e

cd server/target
gunzip -c clase09-ejer01-server-1.0-SNAPSHOT-bin.tar.gz | tar xopf -
chmod +x clase09-ejer01-server-1.0-SNAPSHOT/run-node.sh