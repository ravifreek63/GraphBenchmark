#!/bin/bash
cd /home/tandon/Projects/GraphBenchmark/src;
javac *.java
sudo echo 1 > /proc/sys/vm/drop_caches;
sudo echo 2 > /proc/sys/vm/drop_caches;
sudo echo 3 > /proc/sys/vm/drop_caches;
case "$1" in
"openjdk_gc")
sudo bash /home/tandon/change.sh openjdk
cd /home/tandon/Projects/GraphBenchmark/src;
time java -XX:-UseCMSCompactAtFullCollection -XX:ConcGCThreads=5 -Xms4g -XX:NewRatio=10 -XX:NumberPartitions=500  -XX:+PrintGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails Benchmark 22 $2 4 10000
;;
*)
sudo bash /home/tandon/change.sh
cd /home/tandon/Projects/GraphBenchmark/src;
time java -XX:-UseCMSCompactAtFullCollection -XX:ConcGCThreads=4 -Xms4g -XX:NewRatio=10 -XX:+PrintGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails Benchmark 22 $2 4 10000
esac
