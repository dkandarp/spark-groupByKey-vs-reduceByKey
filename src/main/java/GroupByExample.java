import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.StreamSupport.stream;

public class GroupByExample {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("please pass the file path & name for generation");
        }

        long start = System.currentTimeMillis();
        SparkConf conf = new SparkConf()
                .setAppName("Group By Example")
                .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> spaceSeparatedFile = sc.textFile(args[0], 10);

        JavaRDD<String> flattern = spaceSeparatedFile.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
        JavaPairRDD<String, Integer> pair = flattern.mapToPair(word -> new Tuple2<>(word, 1));
        JavaPairRDD<String, Iterable<Integer>> grouped = pair.groupByKey();

        JavaPairRDD<String, Integer> agg = grouped.aggregateByKey(0,
                (Function2<Integer, Iterable<Integer>, Integer>) (v1, v2) -> v1 + stream(v2.spliterator(), false).mapToInt(t -> t).sum(),
                (Function2<Integer, Integer, Integer>) (v1, v2) -> v1 + v2
        );

        List<Tuple2<String, Integer>> outcome = agg.collect();
        System.out.println("Time Taken:" + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start));
        Commons.log(outcome);
    }
}
