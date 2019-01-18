#!/bin/bash
java -Dserver.port=8080 -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=5005,suspend=n -jar demo.jar --spring.profiles.active=prod