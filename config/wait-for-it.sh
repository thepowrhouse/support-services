#!/usr/bin/env sh
host="$1"
count=0
until [ $(curl -v --silent $host 2>&1 | grep UP | wc -l) -gt 0 -o $count -gt 30000 ]
do
  count=$(expr $count + 5)
  sleep 5
done