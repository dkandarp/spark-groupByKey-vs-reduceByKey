import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

public class Commons {

    static void log(List<Tuple2<String, Integer>> result) {
        System.out.println("Total unique values:" + result.size());
        ArrayList<Tuple2<String, Integer>> list = new ArrayList<>(result);

        sort(list, (o1, o2) -> o1._2.compareTo(o2._2));


        list.subList(0, 10).forEach(System.out::println);
        list.subList(list.size() - 10, list.size()).forEach(System.out::println);
    }
}
