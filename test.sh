#!/bin/zsh

set -x

cd $(dirname $0)
echo working on $(pwd)...

url="http://localhost:8321/"

value1="value1-%E5%80%BC1-"$(date +%S)
curl -sS $url'get?key1='$value1 &

value2="value2-%E5%80%BC2-"$(date +%M)
curl -sS $url"get?key1=$value1&key1=$value2&key1=$value2" &

value3="value3-%E5%80%BC3-"$(date +%M%S)
curl -sS $url"get?key1=$value1&key1=$value2&key1=$value2&key2=$value3" &

curl -H "Content-Type:application/json" -X POST --data '{"name": "name名字", "ilist": [1,2,3,4], "slist":["1一","2二"], "dict": {"key": "value值", "ilist": [1,2,3,4]}}' $url"post" &
