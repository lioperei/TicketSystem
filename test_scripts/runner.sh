#!/bin/bash

readarray arr < stuff.txt
printf "${arr[*]}" | java TicketSystem user_account.txt available_tickets.txt