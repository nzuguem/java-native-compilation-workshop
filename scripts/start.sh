#!/usr/bin/env bash

COMMAND=$1

$COMMAND &

export MY_PID=$!

while [ "$(curl -s -o /dev/null -L -w ''%{http_code}'' http://localhost:8080/hello/Perf)" != "200" ];
  do sleep 0.001;
done

kill $MY_PID