#!/usr/bin/env bash

COMMAND=$1

perf stat $COMMAND &

export PERF_ID=$!

while [ "$(curl -s -o /dev/null -L -w ''%{http_code}'' http://localhost:8080/hello/Perf)" != "200" ];
  do sleep 0.001;
done

export MY_PID="$(pgrep -P $PERF_ID)"
kill $MY_PID