#!/bin/zsh

DIR=$1

for file in $DIR/*.svg; do
  name=$(basename $file .svg)
  inkscape $file --export-type=png --export-filename=$DIR/$name.png
done