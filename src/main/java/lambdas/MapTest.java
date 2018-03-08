package lambdas;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("Rahul", "Mane");
        map.put("Pooja", "Sharma");
        map.put("Santosh", "K");
        map.put("Lokesh", "Bandaru");
        map.put("Nikhil", "Sekhar");

        System.out.println(map.getOrDefault("Rahul", "DUMMY"));
        System.out.println(map.getOrDefault("NoName", "DUMMY"));

        String s = map.computeIfAbsent("Ravi", str -> "Kasha").toLowerCase();
        System.out.println(map +" , mapped value = "+s);
        s = map.computeIfAbsent("Ravi", str -> "Patil").toLowerCase();
        System.out.println(map +" , old value = "+s);

        s = map.computeIfPresent("Ravi", (k, v) -> "Patil");
        System.out.println(map +" , mapped value = "+s);

        s = map.computeIfPresent("Bruce", (k, v) -> "Wayne");
        System.out.println(map +" , mapped value = "+s);

        Map<String, Integer> battingScores = new HashMap<>();
        battingScores.put("Dravid",100);
        battingScores.put("Ganguly",200);
        Map<String, Integer> battingScores1 = new HashMap<>();
        battingScores1.put("Sachin",150);
        battingScores1.put("Ganguly",100);
        battingScores1.put("Dravid",100);
        battingScores1.put("Dhoni",100);

        battingScores1.forEach((plyr, scr) -> {
            battingScores.merge(plyr, scr, (currentScore, newScore) -> currentScore+newScore);
        });
        System.out.println(battingScores);
    }
}
