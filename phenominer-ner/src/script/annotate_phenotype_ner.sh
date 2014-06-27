#!/bin/bash

################################################################################
#                                                                              #
#  Script run Phenotype NER demo                                               #
#  Copyright (C) 2014 Mai-Vu Tran <vutranmai@gmail.com>                        #
#                                                                              #
################################################################################

# template
Date="27/06/2014"
Version="0.0.1"
Author="Mai-Vu Tran (vutranmai@gmail.com)"

### Functions ###

### Main ###

JAVA_DIR="../../tools/jdk1.7.0_60"

OS=`uname`

APP_HOME="."

LIB="$APP_HOME/lib" ;

CLASSPATH="$JAVA_DIR/lib/tools.jar"

CLASSPATH=${CLASSPATH}:$LIB/*;

JAVACMD="$JAVA_DIR/bin/java"

CLASS='org.nii.phenominer.ner.app.NERMaxentRecognizer'
JAVA_OPTS="-server -XX:+UseParallelGC -Xshare:auto -Xms1g -Xmx4g"
exec $JAVACMD $JAVA_OPTS -cp $CLASSPATH $CLASS "$@"

exit 0
