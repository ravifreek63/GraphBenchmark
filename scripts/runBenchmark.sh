cd /home/tandon/Projects/GraphBenchmark/src;
javac *.java
sudo echo 1 > /proc/sys/vm/drop_caches;
sudo echo 2 > /proc/sys/vm/drop_caches;
sudo echo 3 > /proc/sys/vm/drop_caches;
case "$1" in
"openjdk")
sudo bash ~/change.sh openjdk
java -XX:+PrintGC -Xmx10g  Benchmark 21 16 4 64
;;
*)
sudo bash ~/change.sh
java -XX:+PrintGC -Xmx10g  Benchmark 21 16 4 64
esac


