#!/bin/bash

Game=/gamejelly_gong2/deploy_gong2_game
chmod +x $Game/*.sh
##########
stop () {
    echo "关闭gas..."
    $Game/run-linux-gas.sh stop
    sleep 10
    echo "关闭dbs..."
    $Game/run-linux-dbs.sh stop
};

start () {
    echo "开启dbs..."
    $Game/run-linux-dbs.sh start
    sleep 3
    echo "开启gas..."
    $Game/run-linux-gas.sh start
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

