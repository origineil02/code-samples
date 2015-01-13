read a
read b
read c

if [[ $a = $b || $b = $c ]]; then

  if [[ $a = $b && $b = $c ]]; then
    echo EQUILATERAL
  else           
    echo ISOSCELES
  fi
else
 echo SCALENE
fi