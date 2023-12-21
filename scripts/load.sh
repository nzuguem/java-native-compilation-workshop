#!/usr/bin/env bash

COMMAND=$1

$COMMAND &

export MY_PID=$!

while [ "$(curl -s -o /dev/null -L -w ''%{http_code}'' http://localhost:8080/hello/Perf)" != "200" ];
  do sleep 0.001;
done

echo "Simulating some load..."
hey -n 1000000 http://localhost:8080/hello/Perf

kill $MY_PID

# give perf some time to print its output
sleep 0.2
