#/bin/bash
case "$1" in
"openjdk_gc")
array=(3 4 5 6 7)
;;
*)
array=(2 3 4 5 6)
;;
esac

for i in  "${array[@]}" 
do
  echo "command sudo bash runBenchmark.sh $1 $i"
  sudo bash runBenchmark.sh $1 $i
done 

