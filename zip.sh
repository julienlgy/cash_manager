#!/bin/bash

if [ -z ${1+x} ]; then
    OUTPUT="cashmanager_2019_14.zip"
else
    OUTPUT=$(printf $1.zip)
fi
printf "ZIPPING ENTIRE GIT INTO $OUTPUT\n"
zip -r $OUTPUT . -x *.git*