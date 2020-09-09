#!/bin/bash

set -e

apt update ; apt install -y strace socat

echo "==== Iniciando Warsaw ===="
socat TCP4-LISTEN:31800,fork,reuseaddr TCP4:127.0.0.1:30800 &
socat TCP4-LISTEN:31900,fork,reuseaddr TCP4:127.0.0.1:30900 &
#strace -f -s 8000 -o /strace.warsaw /usr/local/bin/warsaw/core
#strace -f -s 8000 -o /strace.warsaw /usr/bin/warsaw start
/usr/bin/warsaw start
echo "==== Warsaw Iniciado, monitorando PID ===="
PID=`cat /var/run/core.pid`
while kill -0 $PID ; do sleep 1; done
