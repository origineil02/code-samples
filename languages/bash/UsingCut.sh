#Print the lines of a file starting at column 13

cut -c 13-  $1

#Print specific, non contiguous columns

cut -c2,7 $1

#Print the first four columns of each line

cut -c 1-4  $1

#Print the first 3 "fields" (default of tab delimited) of each line

 cut -f1-3 $1

#Using a custom delim : -d option

cut -d ' ' -f1-3