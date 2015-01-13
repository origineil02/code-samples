#input: 5+50*3/20 + (19*2)/7 output: 17.929

read -e x

value=$(echo "$x" | bc -l)

echo $(printf "%.3f" "$value")