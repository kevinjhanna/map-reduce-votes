#!/bin/bash

# set -e

#http://mywiki.wooledge.org/BashFAQ/035

# Usage info
show_help() {
cat << EOF
Usage: ${0##*/} [-hv] [-f OUTFILE] [FILE]...
Do stuff with FILE and write the result to standard output. With no FILE
or when FILE is -, read standard input.
    -h          display this help and exit
    -f OUTFILE  write the result to OUTFILE instead of standard output.
    -v          verbose mode. Can be used multiple times for increased
                verbosity.
EOF
}

# Reset all variables that might be set

 addresses=
 query=
 inPath=
 outPath=
 name=
 pass=

 while :; do
     case $1 in
         -h|-\?|--help)   # Call a "show_help" function to display a synopsis, then exit.
             show_help
             exit
             ;;
          -Daddresses)       # Takes an option argument, ensuring it has been specified.
             if [ -n "$2" ]; then
                 addresses=$2
                 shift
             else
                 printf 'ERROR: "-Dname" requires a non-empty name.\n' >&2
                 exit 1
             fi
             ;;
         -Dquery)       # Takes an option argument, ensuring it has been specified.
             if [ -n "$2" ]; then
                 query=$2
                 shift
             else
                 printf 'ERROR: "-Dname" requires a non-empty name.\n' >&2
                 exit 1
             fi
             ;;
          -DinPath)       # Takes an option argument, ensuring it has been specified.
             if [ -n "$2" ]; then
                 inPath=$2
                 shift
             else
                 printf 'ERROR: "-Dname" requires a non-empty name.\n' >&2
                 exit 1
             fi
             ;;
          -DoutPath)       # Takes an option argument, ensuring it has been specified.
             if [ -n "$2" ]; then
                 outPath=$2
                 shift
             else
                 printf 'ERROR: "-Dname" requires a non-empty name.\n' >&2
                 exit 1
             fi
             ;;
         -Dname)       # Takes an option argument, ensuring it has been specified.
             if [ -n "$2" ]; then
                 name=$2
                 shift
             else
                 printf 'ERROR: "-Dname" requires a non-empty name.\n' >&2
                 exit 1
             fi
             ;;
         -Dpass)       # Takes an option argument, ensuring it has been specified.
             if [ -n "$2" ]; then
                 pass=$2
                 shift
             else
                 printf 'ERROR: "-Dname" requires a non-empty name.\n' >&2
                 exit 1
             fi
             ;;
         -?*)
             printf 'WARN: Unknown option (ignored): %s\n' "$1" >&2
             ;;
         *)               # Default case: If no more options then break out of the loop.
             break
     esac

     shift
 done

 # if --file was provided, open it for writing, else duplicate stdout
 # if [ -n "$name" ]; then
 #     exec 3> "$file"
 # else
 #     exec 3>&1
 # fi

 # Rest of the program here.
 # If there are input files (for example) that follow the options, they
 # will remain in the "$@" positional parameters.
cd client/target

gunzip -c clase09-ejer01-client-1.0-SNAPSHOT-bin.tar.gz | tar xopf -
chmod +x clase09-ejer01-client-1.0-SNAPSHOT/run-client.sh

java -cp 'clase09-ejer01-client-1.0-SNAPSHOT/lib/jars/*' -Daddresses=''${addresses}'' -Dquery=''${query}'' -DinPath=''${inPath}'' -DoutPath=''${outPath}'' -Dname=''${name}'' -Dpass=''${pass}'' "ar.edu.itba.pod.hz.client.VotacionClient" $*

# java -Daddresses=10.6.0.1;10.6.0.2 -Dquery=3 -DinPath=censo.csv -DoutPath=output.txt -Dn=100 client.MyClient

#java -cp 'clase09-ejer01-client-1.0-SNAPSHOT/lib/jars/*' -Daddresses '127.0.0.1:127.0.0.1' -Dquery 2 -DinPath 'files/dataset-1000.csv' -DoutPath 'out.txt' -Dname '52034-53080' -Dpass dev-pass "ar.edu.itba.pod.hz.client.VotacionClient" $*

