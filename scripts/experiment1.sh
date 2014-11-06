#/bin/bash
array=(16 18 20 22 24)
for i in  "${array[@]}" 
do
  sudo bash runBenchmark.sh $1 $i
done 

