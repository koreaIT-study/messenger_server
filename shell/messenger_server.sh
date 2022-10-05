#!/bin/sh

#SERVER_HOME='/home/ec2-user/deploy/messenger_server'
if [ "${SERVER_HOME:-}" = "" ];
    then SERVER_HOME="$(dirname "$(cd "$(dirname "$0")"; "pwd")")";
fi
SERVER_LIBS="${SERVER_HOME}/libs"
SERVER_CONF="${SERVER_HOME}/conf"
SERVER_LOGS="${SERVER_HOME}/logs"
echo "SERVER_HOME dir : $SERVER_HOME"

JAR_FILE=`find $SERVER_HOME -name messenger_server-*.jar`
echo "server JAR file : $JAR_FILE"

print_usage() {
  echo "messenger_server usage:"
  echo "    ./messenger_server.sh [option arguments] [start|stop|restart]"
  echo "    --help, -h, ?)"
  echo "         : print this message"
  echo "    --config, -c {config file location})"
  echo "         : application.yml config file location"
  echo "    --rest.port, -r {port number})"
  echo "         : WAS port for execute REST API"
  echo "    start|stop|restart) server start | stop | restart"
  exit 0
}

MAX_WAIT_CNT=3
CURRENT_WAIT_CNT=0
wait_graceful_shutdown() {
  echo "Process $1 health check..."
  PID=`ps -ef | grep $1 | grep spring | grep -v grep | awk '{print $2}'`
  if [[ -z $PID ]]; then
    echo "Process $1 shutdown complete!"
  else
    echo "wait 5 seconds..."
    sleep 5
    if [[ $CURRENT_WAIT_CNT -lt $MAX_WAIT_CNT ]]; then
      CURRENT_WAIT_CNT=$CURRENT_WAIT_CNT+1
      wait_graceful_shutdown $PID
    else
      echo "failed graceful shutdown!!!"
      echo "execute force kill process $PID"
      kill -15 $PID
    fi
  fi
}

stop_server() {
  PROC=`ps aux | grep messenger_server- | grep spring`
  if [[ $PROC == *"messenger_server-"* ]]; then
    echo "Process is running."
    PID=`ps -ef | grep messenger_server- | grep spring | grep -v grep | awk '{print $2}'`
    kill -9 $PID
    wait_graceful_shutdown $PID
  else
    echo "Process is not running."
  fi
}

JVM_OPTS="-Xms4096m -Xmx4096m -server -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+DisableExplicitGC \
          -verbose:gc -XX:+PrintGCDetails -XX:-PrintGCTimeStamps -Xloggc:$SERVER_LOGS/messenger_server-gc.log \
          -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$SERVER_LOGS -XX:+UseGCOverheadLimit"
SERVER_OPTS="-DSERVER.home=$SERVER_HOME"
APP_ARGS=""

start_server() {
  SERVER_OPTS="$SERVER_OPTS -Dspring.config.location=optional:file:$SERVER_CONF/"
  echo "server options : $JVM_OPTS $SERVER_OPTS"
  echo "application arguments : $APP_ARGS"

  # execute server
  nohup java -jar $JAR_FILE 1> /dev/null 2>&1 & 
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
