#!/bin/sh

APP_NAME=gong_game_dbs
APP_HOME=/gamejelly_gong2/deploy_gong2_game
APP_USER=root
APP_PID=$APP_HOME/$APP_NAME.pid

JAVA_HOME=/usr/java/jdk1.7.0_67
TMP_DIR=/var/tmp
LOG_DIR=$APP_HOME/logs

##########
if [ ! -d $LOG_DIR ]; then 
    mkdir $LOG_DIR
fi

##########
DAEMON=/usr/local/bin/start-stop-daemon

CP=`for i in $APP_HOME/lib/*.jar ; do echo -n :$i ; done`

# Use -XX:+ExplicitGCInvokesConcurrent instead of -XX:+DisableExplicitGC because use DirectByteBuffer
JAVA_OPSTS="-Djava.awt.headless=true -Djava.io.tmpdir=$TMP_DIR \
-server -Xms512m -Xmx1024m -XX:MaxPermSize=64m -XX:NewRatio=3 \
-XX:+UseConcMarkSweepGC -XX:+ExplicitGCInvokesConcurrent \
-verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$LOG_DIR/${APP_NAME}_gc.log"

#JAVA_OPSTS="$JAVA_OPSTS \
#-Dcom.sun.management.jmxremote.port=8101 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false \
#-Dsun.rmi.transport.tcp.responseTimeout=20000 -Dsun.rmi.dgc.client.gcInterval=18000000 -Dsun.rmi.dgc.server.gcInterval=18000000 \
#-Xdebug -Xrunjdwp:transport=dt_socket,address=8102,server=y,suspend=n"

APP_CMDS="-classpath \
$APP_HOME:$APP_HOME/classes:$APP_HOME/lib$CP \
$JAVA_OPSTS \
com.gamejelly.gong2.dbs.DbsApp"
DAEMON_OPTS="--name $APP_NAME \
--chdir $APP_HOME --chuid $APP_USER \
--pidfile $APP_PID --make-pidfile"

##########
start () {
      if [ -f $APP_PID ]; then
          return 1
      fi 
      $DAEMON --start --background $DAEMON_OPTS --exec $JAVA_HOME/jre/bin/java -- $APP_CMDS
};

stop () {
      $DAEMON --stop --quiet --pidfile $APP_PID --retry 15
      rm -f $APP_PID
};

##########
case "$1" in
    start)
        echo -n "Starting $APP_NAME Server: ";
        start;
        case "$?" in
            0) echo 'done.' ;;
            *) echo 'failed.' ;;
        esac
        ;;
    stop)
        echo -n "Stoping $APP_NAME Server: ";
        stop;
        echo 'done.';
        ;;
    restart)
        echo -n "Restarting $APP_NAME Server: ";
        stop;
        sleep 3s;
        start;
        echo 'done.';
        ;;
    *)
        echo "Usage $0 start|stop|restart"
        exit 1;
        ;;
esac;
