#!/usr/bin/env sh

# abort on errors
set -e

# build
npm run build

# navigate into the build output directory
cd src/.vuepress

mkdir -p docs

cd docs

git init -b main
git remote add origin git@github.com:cocot3ro/GestionHotel-Suite.git
git pull

cp -r ../dist/* .

git add docs
git commit -m 'deploy vue site'

git push -u origin main

cd -
