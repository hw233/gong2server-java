#!/bin/bash

Game=/gamejelly_gong2/deploy_global
chmod +x $Game/*.sh
##########
stop () {
    echo "关闭gas..."
    $Game/global-linux-app.sh stop
    sleep 10
    echo "关闭dbs..."
    $Game/global-linux-dbs.sh stop
};

start () {
    echo "开启dbs..."
    $Game/global-linux-dbs.sh start
    sleep 5
    echo "开启gas..."
    $Game/global-linux-app.sh start
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
        sleep 3s;
        start;
        echo 'done.';
        ;;
    *)
        echo "Usage $0 start|stop|restart"
        exit 1;
        ;;
esac;

