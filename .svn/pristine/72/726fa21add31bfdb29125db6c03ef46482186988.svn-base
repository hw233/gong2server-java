#!/bin/sh

echo PWD: $PWD
svn up
mvn compile
mvn -Djetty.port=9090 -Dorg.mortbay.util.URI.charset=ISO-8859-1 jetty:run