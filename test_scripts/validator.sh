#!/bin/bash

for command in output/*; do
  for testcase in $command/*; do
    casename="${testcase/output/}"
    if [ "$(cat $testcase/output.txt)" == "$(cat $testcase/expected_output.txt)" ] ;then
      echo "$casename passed"
    else
      echo "$casename failed"
    fi
  done
done