#!/bin/zsh

set -x

cd $(dirname $0)
echo working on $(pwd)...


value1="value1-"$(date +%S)
curl -sS 'http://localhost:8321/test?key1='$value1 &


value2="value2-"$(date +%M)
curl -sS "http://localhost:8321/test?key1=$value1&key1=$value2&key1=$value2" &


value3="value3-"$(date +%M%S)
curl -sS "http://localhost:8321/test?key1=$value1&key1=$value2&key1=$value2&key2=$value3" &
