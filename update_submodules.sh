#!/usr/bin/env sh

# abort on error
set -e

if [ $# -lt 1 ]; then
	echo "Error: Se deve indicar una rama"
	exit 1
fi

echo $(pwd | rev | cut -d '/' -f 1 | rev)
git checkout $1
echo

git restore --staged .

submodules=$(cat .gitmodules | grep path | sed -e "s/.*= //g")

for submodule in $submodules; do
	echo "$submodule"
	cd $submodule
	git checkout $1
	cd ..
	git add $submodule
	echo
done

git commit -m "update submodules"
git push
