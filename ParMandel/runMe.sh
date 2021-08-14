#!/bin/bash

_CMD_JAVA=`which java`;

$_CMD_JAVA -cp ./out/production/ miltithread/TaskRunner $1 $2
