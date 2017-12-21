#!/bin/bash

chmod +x *.sh

##########
stop () {
    echo "关闭global app..."
    ./run-global-app.sh stop
    sleep 3
    echo "关闭global dbs..."
    ./run-global-dbs.sh stop
};

start () {
    echo "开启global dbs..."
    ./run-global-dbs.sh start
    sleep 3
    echo "开启global app..."
    ./run-global-app.sh start
};

##########
case "$1" in
    start)
        echo -n 'Starting Global App Server: ';
        start;
        echo 'done.';
        ;;
    stop)
        echo -n 'Stoping Global App Server: ';
        stop;
        echo 'done.';
        ;;
    restart)
        echo -n 'Restarting Global App Server: ';
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