#!/bin/bash
cp ../*.class .

for command in input/*; do
  for testcase in $command/*; do
    output="${testcase/input/output}"
    readarray arg < $testcase/input.txt
    echo "$testcase running"
    printf "${arg[*]}" | java TicketSystem $testcase/user_account.txt $testcase/available_tickets.txt > $output/output.txt
    echo "$testcase ran"
  done
done

rm *.class