#!/bin/bash

################################################################################
#                                                                              #
#  Script run NLP tools webservice (BLLIP, GENIA, Ontologies annotator)        #
#  Copyright (C) 2014 Mai-Vu Tran <vutranmai@gmail.com>                        #
#                                                                              #
################################################################################

# template
Date="24/05/2014"
Version="0.0.1"
Author="Mai-Vu Tran (vutranmai@gmail.com)"

### Functions ###

### Main ###

# cp -rf ../backend/phenominer/phenominer-dataprocessing/models/ .
cp ../backend/phenominer/phenominer-dataprocessing/target/phenominer-dataprocessing-jar-with-dependencies.jar .

JAVA_DIR="../tools/jdk1.7.0_60"

OS=`uname`

APP_HOME="."

LIB="$APP_HOME/lib" ;

CLASSPATH="$JAVA_DIR/lib/tools.jar"

CLASSPATH=${CLASSPATH}:$LIB/*:phenominer-dataprocessing-jar-with-dependencies.jar;

JAVACMD="$JAVA_DIR/bin/java"

CLASS='org.nii.phenominer.processing.app.NLPToolsWebService'
JAVA_OPTS="-server -XX:+UseParallelGC -Xshare:auto -Xms1g -Xmx4g"
exec $JAVACMD $JAVA_OPTS -cp $CLASSPATH $CLASS "$@"

exit 0
