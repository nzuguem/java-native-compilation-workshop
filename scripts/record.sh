#!/usr/bin/env bash

COMMAND=$1

$COMMAND &

export MY_PID=$!

psrecord $MY_PID --plot plot-$(date +%s).png --include-children &

while [ "$(curl -s -o /dev/null -L -w ''%{http_code}'' http://localhost:8080/hello/Perf)" != "200" ];
  do sleep 0.001;
done

echo "Simulating some load..."
for i in $(seq 20);
do
  curl -s -o /dev/null -L -w "." http://localhost:8080/hello/Perf
  sleep 0.5
done

kill $MY_PID
