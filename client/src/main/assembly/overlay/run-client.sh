#!/bin/bash

java -cp 'lib/jars/*'  -Dname='52034-53080' -Dquery=1 -DoutPath='out.txt' -Dpass=dev-pass  -Daddresses='127.0.0.1:127.0.0.1' -DinPath='files/dataset-1000.csv'  "ar.edu.itba.pod.hz.client.VotacionClient" $*

