#Replace all '(' and ')' with '[' and ']' respectively
tr "()" "[]"

#Using -d option to delete all lower case letters

tr -d [:lower:]

#Using -s option to replace repetitions of spaces with single occurence

tr -s [:space:]