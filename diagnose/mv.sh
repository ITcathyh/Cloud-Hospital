#!/usr/bin/env bash

CURDIR=$(pwd)

mv -f $CURDIR/generator/src/main/java/top/itcat/service/impl/* $CURDIR/src/main/java/top/itcat/service/impl
mv -f $CURDIR/generator/src/main/java/top/itcat/service/* $CURDIR/src/main/java/top/itcat/service
mv -f $CURDIR/generator/src/main/java/top/itcat/mapper/* $CURDIR/src/main/java/top/itcat/mapper