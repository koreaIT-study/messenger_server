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

  docker start zookeeper
  docker start kafka
  docker run -it --name messenger-server -d -p 12000:12000 -v /home/mshmsh0814/storage/logs:/tmp/logs -v /home/mshmsh0814/deploy/data_files/profiles:/profile -v /home/mshmsh0814/deploy/data_files/msg:/msg --network server-net shmin7777/messenger-server
	 
}

# input argument check & execute
while [ $# -gt 0 ]
do
    case "$1" in
        start) start_server;;
        stop) stop_server;;
        restart) stop_server
                 start_server;;
        *) echo "check arguments plz!!!"
    esac
    shift
done
