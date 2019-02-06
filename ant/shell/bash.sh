sed -i '/username/s/".*"/"appsadmin"/' D:/apache-tomcat-7.0.37/webapps/Hub/META-INF/context.xml
sed -i '/password/s/".*"/"inf0tree#99"/' D:/apache-tomcat-7.0.37/webapps/Hub/META-INF/context.xml
sed -i '/url/s/".*"/"jdbc:mysql:\/\/preproddb.clictest.com:3306\/clictest"/' D:/apache-tomcat-7.0.37/webapps/Hub/META-INF/context.xml



#!/bin/sh
MY_MESSAGE="Hello World"
echo $MY_MESSAGE