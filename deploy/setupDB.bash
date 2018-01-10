#!/bin/bash
export DERBY_INSTALL=/home/ubuntu/db-derby-10.14.1.0-bin
export CLASSPATH=$DERBY_INSTALL/lib/derby.jar:$DERBY_INSTALL/lib/derbytools.jar:.

java org.apache.derby.tools.ij import.sql