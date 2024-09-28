#!/usr/bin/env sh

# abort on error
set -e

echo $(pwd | rev | cut -d '/' -f 1 | rev)
git checkout main
echo

git restore --staged .

submodules=$(cat .gitmodules | grep path | sed -e "s/.*= //g")

for submodule in $submodules; do
	echo "$submodule"
	cd $submodule
	git checkout main
	cd ..
	git add $submodule
	echo
done

git status

git commit -m "update submodules"
git push
