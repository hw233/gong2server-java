#!/bin/bash

chmod +x *.sh

##########
stop () {
    echo "关闭gas..."
    ./run-gas.sh stop
    sleep 3
    echo "关闭dbs..."
    ./run-dbs.sh stop
};

start () {
    echo "开启dbs..."
    ./run-dbs.sh start
    sleep 3
    echo "开启gas..."
    ./run-gas.sh start
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