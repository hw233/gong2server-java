#!/bin/bash

APP_NAME=gong_game_gas
APP_HOME=/gamejelly_gong2/deploy_gong2_game
APP_USER=root
APP_PID=$APP_HOME/$APP_NAME.pid

JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_71.jdk/Contents/Home
TMP_DIR=/var/tmp
LOG_DIR=$APP_HOME/logs

##########
if [ ! -d $LOG_DIR ]; then 
    mkdir $LOG_DIR
fi


CP=`for i in $APP_HOME/lib/*.jar ; do echo -n :$i ; done`

# Use -XX:+ExplicitGCInvokesConcurrent instead of -XX:+DisableExplicitGC because use DirectByteBuffer
JAVA_OPSTS="-Djava.awt.headless=true -Djava.io.tmpdir=$TMP_DIR \
-server -Xms2048m -Xmx2048m -XX:MaxPermSize=256m -XX:NewRatio=3 \
-XX:+UseConcMarkSweepGC -XX:+ExplicitGCInvokesConcurrent \
-verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$LOG_DIR/${APP_NAME}_gc.log"

APP_CMDS="-classpath \
$APP_HOME:$APP_HOME/classes:$APP_HOME/lib$CP \
$JAVA_OPSTS \
com.hadoit.game.fsgame.tools.HefuDataTools"

$JAVA_HOME/jre/bin/java $APP_CMDS
