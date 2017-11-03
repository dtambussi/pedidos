#!/bin/bash

echo "Running assets install"
bin/console assets:install web
bin/console assetic:dump
echo "assets install OK"
