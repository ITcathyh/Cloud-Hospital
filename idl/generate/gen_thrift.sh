#!/usr/bin/env bash

CURDIR=$(cd $(dirname $0); pwd)
rm -rf $CURDIR/../gen-java

function read_dir(){
    for file in `ls $1`
    do
        if [ -d $1"/"$file ]
        then
            read_dir $1"/"$file
        else
            echo $1$file
            thrift -gen java $1/$file
        fi
    done
}

read_dir "$CURDIR/../model/"
read_dir "$CURDIR/../service/"

#MSG_SRC="$CURDIR/../*.thrift $CURDIR/../model/*.thrift"
#for file in `ls $MSG_SRC`
#do
#    echo $file
#    thrift -gen java $file
#done

rm -rf $CURDIR/../../common/src/main/java/top/itcat/rpc/base
rm -rf $CURDIR/../../common/src/main/java/top/itcat/rpc/service
mv  $CURDIR/../gen-java/top/itcat/rpc/base $CURDIR/../../common/src/main/java/top/itcat/rpc/base
mv  $CURDIR/../gen-java/top/itcat/rpc/service $CURDIR/../../common/src/main/java/top/itcat/rpc/service