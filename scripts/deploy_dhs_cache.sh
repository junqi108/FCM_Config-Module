#!/usr/bin/env bash

###################################################################
# Constants
###################################################################

DHS_CACHE_DVERSION="$1"
DHS_CACHE_DURL=repo
DHS_CACHE_DFILE=dhs-cache-${DHS_CACHE_DVERSION}.jar
DHS_CACHE_DGROUPID=cache.dhs
DHS_CACHE_DARTIFACT_ID=dhs-cache

###################################################################
# Functions
###################################################################

usage() {
    echo """
    ###############################################################
    Help:

    * Description:
        - Add the DHS cache package to a local Maven repository.
    * Usage:
        - $0 [DHS_CACHE_DVERSION]  
    * Parameters:
        - DHS_CACHE_DVERSION: The package version of the DHS cache.
            - Mandatory.

    ###############################################################
    """    
}

help() {
    if [ "$1" = "-h" ] || [ "$1" = "-help" ] || [ -z "$1" ] ; then
        usage
        exit 0
    fi
}

###################################################################
# Main
###################################################################

help "${1}"

mvn deploy:deploy-file -Dfile=$DHS_CACHE_DFILE -Durl=file:${DHS_CACHE_DURL} -DgroupId=$DHS_CACHE_DGROUPID -DartifactId=$DHS_CACHE_DARTIFACT_ID -Dpackaging=jar -Dversion=$DHS_CACHE_DVERSION 
