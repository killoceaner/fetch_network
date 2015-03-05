#!/bin/bash

SITE='openhub'

find ./target/classes -name "*.properties"|xargs rm -f
find ./target/classes -name "*.xml"|xargs rm -f
find ./target/classes -name "*.dic"|xargs rm -f
find ./target/classes/spring |xargs rm -f -r
find ./target/classes/sites |xargs rm -f -r

tmp='./bin/resources'
tmp='./sites':$tmp
tmp='./target/classes':$tmp
tmp='./target/fetchnetworks-1.0-SNAPSHOT-jar-with-dependencies-without-resources/*':$tmp

CLASSPATH=$tmp:$CLASSPATH


echo $CLASSPATH
JVM_ARGS="-Xmn98m -Xmx128m -Xms128m -XX:NewRatio=4 -XX:SurvivorRatio=4 -XX:MaxTenuringThreshold=2"
#-XX:MinPermSize=128m"
#echo JVM_ARGS=$JVM_ARGS
#ulimit -n 400000
#echo "" > nohup.out

java $JVM_ARGS -classpath $CLASSPATH net.trustie.webmagic.one.ListHtmlCrawler $SITE >>log/${SITE}_list.log 2>&1 &
java $JVM_ARGS -classpath $CLASSPATH net.trustie.webmagic.one.UrlExtractor $SITE >>log/${SITE}_url.log 2>&1 &
java $JVM_ARGS -classpath $CLASSPATH net.trustie.webmagic.one.DetailHtmlCrawler $SITE >>log/${SITE}_detail.log 2>&1 &
#java $JVM_ARGS -classpath $CLASSPATH net.trustie.webmagic.one.ErrorPageCrawler $SITE >>log/${SITE}_error_process.log 2>&1 &