#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR/../
ant -f build.xml -Ddeploy.opr=test -Ddeploy.dir=/gamejelly_gong2/deploy_gong2_game_login
