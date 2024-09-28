#!/usr/bin/env sh

# abort on error
set -e

if [ $# -lt 1 ]; then
	echo "No branch specified"
	exit 1
fi

submodules=$(cat .gitmodules | grep path | sed -e "s/.*= //g")

for submodule in $submodules; do
	echo "$submodule"
	cd $submodule
	git checkout $1
	cd ..
	echo
done
