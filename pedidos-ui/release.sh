#!/bin/bash
if [ "$#" -ne 1 ]; then
	echo "Faltan argumentos"
	echo "Uso: ./release.sh <num_version>. Ej: ./release.sh 1.1"
	exit 1;
fi

git checkout -b release-ui-$1
./less.sh
git add web
git add -f vendor/
git add -f web/bundles/
git add -f var/SymfonyRequirements.php var/bootstrap.php.cache 
git add -f app/config/parameters.yml
git commit -m "Generado release release-ui-$1"
