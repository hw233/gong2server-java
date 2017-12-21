#!/bin/bash

APP_NAME=gong_game_dbs
APP_HOME=/gamejelly_gong2/deploy_gong2_game
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_71.jdk/Contents/Home
TMP_DIR=/var/tmp
LOG_DIR=$APP_HOME/logs

##########
if [ ! -d $LOG_DIR ]; then 
    mkdir $LOG_DIR
fi

##########
DAEMON=daemon

CP=`for i in $APP_HOME/lib/*.jar ; do echo -n :$i ; done`

# Use -XX:+ExplicitGCInvokesConcurrent instead of -XX:+DisableExplicitGC because use DirectByteBuffer
JAVA_OPSTS="-Djava.awt.headless=true -Djava.io.tmpdir=$TMP_DIR \
-server -Xms1024m -Xmx1024m -XX:MaxPermSize=256m -XX:NewRatio=3 \
-XX:+UseConcMarkSweepGC -XX:+ExplicitGCInvokesConcurrent \
-verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$LOG_DIR/${APP_NAME}_gc.log"

#JAVA_OPSTS="$JAVA_OPSTS \
#-Dcom.sun.management.jmxremote.port=8001 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false \
#-Dsun.rmi.transport.tcp.responseTimeout=20000 -Dsun.rmi.dgc.client.gcInterval=18000000 -Dsun.rmi.dgc.server.gcInterval=18000000 \
#-Xdebug -Xrunjdwp:transport=dt_socket,address=8002,server=y,suspend=n"

APP_CMDS="$JAVA_HOME/jre/bin/java \
-classpath \
$APP_HOME:$APP_HOME/classes:$APP_HOME/lib$CP \
$JAVA_OPSTS \
com.gamejelly.gong2.dbs.DbsApp"

DAEMON_OPTS="--name=$APP_NAME \
--chdir=$APP_HOME \
--respawn \
--pidfile=$APP_HOME/$APP_NAME.pid \
--output=$LOG_DIR/${APP_NAME}_app.log"

##########
start () {
    $DAEMON $DAEMON_OPTS -- $APP_CMDS
};

stop () {
    $DAEMON $DAEMON_OPTS --stop -- $APP_CMDS
};

##########
case "$1" in
    start)
        echo -n 'Starting App Server: ';
        start;
        echo 'done.';
        ;;
    stop)
        echo -n 'Stoping App Server: ';
        stop;
        echo 'done.';
        ;;
    restart)
        echo -n 'Restarting App Server: ';
        stop;
        sleep 3;
        start;
        echo 'done.';
        ;;
    *)
        echo "Usage $0 start|stop|restart"
        exit 1;
        ;;
esac;
