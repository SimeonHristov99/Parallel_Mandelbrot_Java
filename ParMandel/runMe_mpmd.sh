#!/bin/bash

_CMD_JAVA=`which java`;

$_CMD_JAVA -cp ./out/production/ multithread/mpmd/Master $1 $2 $3
