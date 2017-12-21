#!/bin/shs

echo PWD: $PWD
svn up
call mvn compile
call mvn -Djetty.port=9090 jetty:run