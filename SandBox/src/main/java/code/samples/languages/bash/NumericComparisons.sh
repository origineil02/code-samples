read a
read b

if [ $a -eq $b ]; then
 echo "X is equal to Y"
else
 if [ $a -lt $b ]; then
  echo "X is lesser than Y" 
 else
  echo "X is greater than Y"
 fi
fi