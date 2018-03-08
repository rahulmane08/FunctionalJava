package lambdas;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalTest {
    public static void main(String[] args) {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElse("john");
        System.out.println(name);

        Map<String, String> map = new HashMap<>();
        List<String> keys = map.entrySet().stream().map(entry->{
            return entry.getKey();
        }).collect(Collectors.toList());
        System.out.println(keys);

        List<StringBuilder> l1 = Arrays.asList(new StringBuilder[]{new StringBuilder("a")
                ,new StringBuilder("b")
                ,new StringBuilder("c")});
        List<StringBuilder> l2 = Arrays.asList(new StringBuilder[]{new StringBuilder("x")
                ,new StringBuilder("y")
                ,new StringBuilder("z")});

        Stream.of(l1)
                .flatMap(list1-> list1.stream())
                .forEach(elem1-> {
                    Stream.of(l2)
                            .flatMap(list2-> list2.stream())
                            .forEach(elem2-> elem1.append(elem2));
                });
        System.out.println(l1+"\n"+l2);
    }

}
