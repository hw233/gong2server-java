#!/bin/bash
#svn rm `svn st | grep '^\!' | awk '{print $2;}'`
#svn add `svn st | grep '^\?' | awk '{print $2;}'`

svn status | grep "^\?" | sed -e 's/? *//' | sed -e 's/ /\\ /g' | xargs svn add
svn status | grep "^\!" | sed -e 's/! *//' | sed -e 's/ /\\ /g' | xargs svn remove
svn ci -m "#misc auto commit!!!"
