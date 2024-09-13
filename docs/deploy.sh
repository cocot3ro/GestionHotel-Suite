#!/usr/bin/env sh

# abort on errors
set -e

# build
npm run build

# navigate into the build output directory
cd src/.vuepress

mkdir -p docs

cp -r dist/* docs/

git init -b main
git add docs
git commit -m 'deploy vue site'

git push -f git@github.com:cocot3ro/GestionHotel-Suite.git main

cd -
