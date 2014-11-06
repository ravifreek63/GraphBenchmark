#!/bin/bash
cd /home/tandon/Projects/GraphBenchmark/src;
javac *.java
sudo echo 1 > /proc/sys/vm/drop_caches;
sudo echo 2 > /proc/sys/vm/drop_caches;
sudo echo 3 > /proc/sys/vm/drop_caches;
case "$1" in
"openjdk")
sudo bash /home/tandon/change.sh openjdk
cd /home/tandon/Projects/GraphBenchmark/src;
java -Xcomp -XX:InCoreHeapSize=270 -XX:+UseConcMarkSweepGC -XX:+PrintGC -Xms10g -XX:NewRatio=50 -XX:-PrintGCDetails -XX:+INTER_COMPILER -XX:-INTER_INTERPRETER -XX:+CMS_Swap -XX:-L_SWAP -XX:-JavaThreadPrefetch -XX:+Swap_Protect  Benchmark 21 16 4 10
;;
"openjdk_gc")
sudo bash /home/tandon/change.sh openjdk
cd /home/tandon/Projects/GraphBenchmark/src;
time java -XX:-UseCMSCompactAtFullCollection -XX:-UseG1GC -XX:-PrintSafepointStatistics -XX:+UseConcMarkSweepGC -XX:ConcGCThreads=$2 -XX:NumberPartitions=500 -XX:+PrintGC -Xms20g -XX:NewRatio=50 -XX:+PrintGCDetails Benchmark 23 16 4 10
;;
*)
sudo bash /home/tandon/change.sh
cd /home/tandon/Projects/GraphBenchmark/src;
time java -XX:-UseCMSCompactAtFullCollection -XX:ConcGCThreads=$2 -Xms20g -XX:NewRatio=50 -XX:+PrintGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails Benchmark 23 16 4 10
esac
