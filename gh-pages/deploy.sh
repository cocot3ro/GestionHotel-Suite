#!/usr/bin/env sh

# abort on errors
set -e

if [ ! -d node_modules ]; then
	yarn install
fi

# build
npm run build

# navigate into the build output directory
cd src/.vuepress

if [ -d deploy ]; then
	rm -rf docs
fi

mkdir -p deploy

cd deploy

git init -b main
git remote add origin git@github.com:cocot3ro/GestionHotel-Suite.git
git pull origin main

mkdir -p docs

rm -rf docs/*

cp -r ../dist/* docs

git add .
git commit -m 'deploy vue site'

git push -u origin main

cd ..

rm -rf deploy
