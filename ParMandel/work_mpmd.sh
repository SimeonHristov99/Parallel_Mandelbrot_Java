#!/bin/bash

cs=(4 8)
gs=(1 5 10 16 32 64 128)
ps=(1 2 4 8)
for c in "${cs[@]}"; do
  for g in "${gs[@]}"; do
    for p in "${ps[@]}"; do
      ./runMe_mpmd.sh $p $g $c
      sleep 5
      ./runMe_mpmd.sh $p $g $c
      sleep 5
      ./runMe_mpmd.sh $p $g $c
      sleep 5
    done
  done
done
