#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR/../


ant -f build.xml -Ddeploy.opr=ios -Ddeploy.dir=/gamejelly_gong2/repo-server/deploy_gong2_game


echo "--------build finished---------"



