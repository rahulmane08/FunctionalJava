package streams;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class ParallelStreamsTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");

        List<Integer> list = new ArrayList<>();
        for (int i=0;i<100;i++) {
            list.add(i);
        }
        ForkJoinPool fjp = new ForkJoinPool(2);
        Double average = fjp.submit(() -> {
            return list.stream()
                    .filter(i -> i > 30)
                    .mapToInt(Integer::new)
                    .average().getAsDouble();
        }).get();
        System.out.println(average);
    }
}
