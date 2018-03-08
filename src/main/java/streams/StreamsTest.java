package streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StreamsTest {
    public static void main(String[] args) throws IOException {
        // building a stream
        Stream.Builder<String> builder = Stream.builder();
        builder.add("one").add("two").add("three");
        builder.build().forEach(System.out::println);

        // concatenating two streams:
        String path1 = "/Users/rmane/ProjectDocs/untitled.txt";
        String path2 = "/Users/rmane/ProjectDocs/untitled 2.txt";
        Stream<String> mergedStream = getMergedFileStream(path1, path2);
        //System.out.println("# streams "+mergedStreamOfStreams.count());
        System.out.println("# lines " + mergedStream.count());

        // counting words
       Long count = getMergedFileStream(path1, path2)
                .flatMap(line -> Pattern.compile(" ").splitAsStream(line))
                .distinct() // distinct word count
                .count();
        System.out.println("# unique words = " + count);

        OptionalDouble average = getMergedFileStream(path1, path2)
                .flatMap(line -> Pattern.compile(" ").splitAsStream(line))
                //.map(word -> word.length())  // this gives a Stream<Integer> which doesnt have an average method, we need to convert it to IntStream
                .mapToInt(word -> word.length())
                .average();
        System.out.println("avg word length = "+average.getAsDouble());




        OptionalInt optionalMin = getMergedFileStream(path1, path2)
                .flatMap(line -> Pattern.compile(" ").splitAsStream(line))
                .mapToInt(word -> word.length())
                .min();
        System.out.println("min word length = "+optionalMin.getAsInt());

        IntSummaryStatistics intSummaryStatistics = getMergedFileStream(path1, path2)
                .flatMap(line -> Pattern.compile(" ").splitAsStream(line))
                .mapToInt(word -> word.length())
                .summaryStatistics();
        System.out.println(intSummaryStatistics);

        String longestWord = getMergedFileStream(path1, path2)
                .flatMap(line -> Pattern.compile(" ").splitAsStream(line))
                .max(Comparator.comparingInt(word -> word.length())).get();
        System.out.println("longest word = "+longestWord);

        String smallestWord = getMergedFileStream(path1, path2)
                .flatMap(line -> Pattern.compile(" ").splitAsStream(line))
                .min(Comparator.comparingInt(word -> word.length())).get();
        System.out.println("smallest word = "+smallestWord);
    }

    private static Stream<String> getMergedFileStream(String path1, String path2) throws IOException {
        Stream<String> s1 = Files.lines(Paths.get(path1));
        Stream<String> s2 = Files.lines(Paths.get(path2));
        Stream<Stream<String>> mergedStreamOfStreams = Stream.of(s1, s2); // stream of stream
        return mergedStreamOfStreams.flatMap(Function.identity());
    }
}
