#!/bin/bash

echo "Compiling all LESS files to CSS"

find src/PedidosBundle/Resources/less -name '*.less' -exec lessc {} \; > src/PedidosBundle/Resources/public/css/main.css

echo "Compile OK"

echo "Running assets install"

bin/console assets:install web

echo "assets install OK"
