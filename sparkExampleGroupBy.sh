#!/usr/bin/env bash


java    \
    -cp /Users/Dave/Documents/Dave/Development/spark-aggregation-demo/build/libs/spark-aggregation-demo-1.0-SNAPSHOT-all.jar:. \
    GenerateSampleInputFile                                                                                                 \
    100000000 "/Users/Dave/Documents/Dave/Development/spark-aggregation-demo/sparkGroupBy.txt"

ls -latrh /Users/Dave/Documents/Dave/Development/spark-aggregation-demo/sparkGroupBy.txt

/usr/local/Cellar/apache-spark/2.3.0/bin/spark-submit                                                                                                           \
      --class GroupByExample                                                                                             \
      --master local[4]                                                                                                  \
      /Users/Dave/Documents/Dave/Development/spark-aggregation-demo/build/libs/spark-aggregation-demo-1.0-SNAPSHOT-all.jar   \
      "/Users/Dave/Documents/Dave/Development/spark-aggregation-demo/sparkGroupBy.txt" > groupBy.log 2>&1



/usr/local/Cellar/apache-spark/2.3.0/bin/spark-submit                                                                                                           \
      --class ReduceByKeyExample                                                                                         \
      --master local[4]                                                                                                  \
      /Users/Dave/Documents/Dave/Development/spark-aggregation-demo/build/libs/spark-aggregation-demo-1.0-SNAPSHOT-all.jar   \
      "/Users/Dave/Documents/Dave/Development/spark-aggregation-demo/sparkGroupBy.txt" > reduceBy.log 2>&1


rm -fr /Users/Dave/Documents/Dave/Development/spark-aggregation-demo/sparkGroupBy.txt
