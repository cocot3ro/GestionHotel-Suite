#!/usr/bin/env sh

# abort on errors
set -e

cd gh-pages

if [ ! -d node_modules ]; then
	pnpm install
fi

./gradlew run
pnpm docs:build

cd docs/.vuepress/dist

git init
git add .
git commit -m "deploy"
git branch -M gh-pages
git remote add origin git@github.com:cocot3ro/GestionHotel-Suite.git
git push -f -u origin gh-pages
