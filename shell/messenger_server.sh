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
  MYSQL=`docker ps -aq -f 'NAME=mysql'`
  if [[ -z $MYSQL ]]; then
    echo "MYSQL STARTING..."
	docker run --name mysql -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 -v /home/mshmsh0814/storage/mysql:/var/lib/mysql --network server-net mysql –character-set-server=utf8 –collation-server=utf8_unicode_ci
	
	echo "MYSQL STARTED"
  else
    echo "MYSQL is already running"
  fi
  docker run -it --name messenger-server -d -p 12000:12000 -v /home/mshmsh0814/storage/logs:/tmp/logs --network server-net shmin7777/messenger-server
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
