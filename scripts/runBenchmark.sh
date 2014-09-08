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
java -XX:InCoreHeapSize=270 -XX:+UseConcMarkSweepGC -XX:+PrintGC -Xms10g -XX:NewRatio=50 -XX:-PrintGCDetails -XX:+INTER_COMPILER -XX:-INTER_INTERPRETER -XX:+CMS_Swap -XX:-L_SWAP -XX:-JavaThreadPrefetch -XX:+Swap_Protect  Benchmark 21 16 4 10
;;
*)
sudo bash /home/tandon/change.sh
cd /home/tandon/Projects/GraphBenchmark/src;
java -XX:+PrintGC -Xmx10g  Benchmark 21 16 4 64
esac


