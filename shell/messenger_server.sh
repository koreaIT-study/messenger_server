#!/bin/sh

stop_server() {
  PROC=`docker ps -aq  -f 'NAME=messenger-server'`
  if [[ -n $PROC ]]; then
    echo "Process is running."
    docker stop $PROC
    docker rm $PROC
    echo "Process stoped"
  else
    echo "Process is not running."
  fi
}

start_server() {
  echo "container start~~"
  docker run --name messenger-server -d -p 12000:12000 shmin7777/messenger-server
}

# input argument check & execute
while [ $# -gt 0 ]
do
    case "$1" in
        --help|-h|?) print_usage;;
        --config|-c) shift
                   SERVER_CONF=$1;;
        --rest.port|-r) shift
                   APP_ARGS="$APP_ARGS --rest.port=$1";;
        start) start_server;;
        stop) stop_server;;
        restart) stop_server
                 start_server;;
        *) echo "check arguments plz!!!"
                print_usage;;
    esac
    shift
done
