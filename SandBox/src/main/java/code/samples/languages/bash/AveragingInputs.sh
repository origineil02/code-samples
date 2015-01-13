read n
total=0
sentinel=$n

while [ $sentinel -gt 0 ]; do

 read X 
 let total+=X
 let  sentinel-=1
 
done 

value=$(echo "$total/$n" | bc -l)
echo $(printf "%.3f" "$value")