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

OS=`uname`

APP_HOME="."

LIB="$APP_HOME/lib";

CLASSPATH="$JAVA_DIR/lib/tools.jar"

CLASSPATH=${CLASSPATH}:$LIB/*;

CLASS='org.nii.phenominer.processing.app.NLPToolsWebService'
JAVA_OPTS="-server -XX:+UseParallelGC -Xshare:auto -Xms1g -Xmx4g"
exec java $JAVA_OPTS -cp $CLASSPATH $CLASS "$@"

exit 0
