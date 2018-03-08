package streams;

import lambdas.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class MapFilterReduceTest {
    public static void main(String[] args) {
        List<Integer> scores = Arrays.asList(50,100,25,37,19,79,45,110);
        int totalCenturies = scores.stream().filter(score -> score>=100).collect(Collectors.toList()).size();
        System.out.println("totalCenturies = "+totalCenturies);

        int totalScore = scores.stream().reduce(0, (a, b)-> a+b);
        System.out.println("totalScore = "+totalScore);

        int maxScore = scores.stream().reduce(Integer.MIN_VALUE, (a, b) -> Integer.max(a, b));
        System.out.println("max score = "+maxScore);

        List<String> stringScores = scores.stream().map(score -> "'"+score+"'").collect(Collectors.toList());
        System.out.println(stringScores);


        List<Employee> employees = new ArrayList<>();
        for(int i=10;i>0;i--)
        {
            Employee e = new Employee(String.valueOf(i),"Employee"+i).setSalary(200*i).setAddress("HSR").setAge(20+i);
            employees.add(e);
        }

        Collections.shuffle(employees);

        employees.stream()
                .map(e -> e.getSalary())
                .peek(salary -> System.out.println("processed salary = "+salary))
                .filter(salary -> salary > 600)
                .forEach(System.out::println);

        //ranges:
        employees.stream()
                .skip(2) //skips first two employees
                .limit(4) // considers only next four out of remaining 8 employees
                .forEach(e -> System.out.println(e));

        //Reductions:
        // a. Match reductions
        boolean anyInKORA = employees.stream()
                .anyMatch(employee -> employee.getAddress().equals("KORA"));
        System.out.println("employees living in KORA="+anyInKORA);

        boolean noneInKORA = employees.stream()
                .noneMatch(employee -> employee.getAddress().equals("KORA"));
        System.out.println("NO employees living in KORA="+noneInKORA);

        boolean allInHSR = employees.stream()
                .allMatch(employee -> employee.getAddress().equals("HSR"));
        System.out.println("All employees stay in HSR? "+allInHSR);

        // b. Find reductions
        Optional<Employee> firstEmpAgedMoreThan25 = employees.stream().filter(e -> e.getAge() > 25).findFirst();
        System.out.println("First employee with age greater than 25 = " +firstEmpAgedMoreThan25.get());

        Collections.shuffle(employees);
        Optional<Employee> anyEmpAgedMoreThan25 = employees.stream().filter(e -> e.getAge() > 25).findAny();
        System.out.println("Any employee with age greater than 25 = " +anyEmpAgedMoreThan25.get());

        /*Optional<Employee> firstEmpAgedMoreThan40 = employees.stream().filter(e -> e.getAge() > 40).findFirst();
        System.out.println("First employee with age greater than 25 = " +firstEmpAgedMoreThan40.get());*/

        //reduction using primitive streams
        OptionalDouble averageAge = employees.stream().mapToInt(Employee::getAge).average();
        System.out.println("average age = "+averageAge.getAsDouble());

        //finding total salary
        double totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();
        System.out.println("total salary paid to all employees = "+totalSalary);

        List<Employee> employees1 = Arrays.asList(new Employee("1", "Rahul").setAddress("Mumbai").setSalary(100)
                , new Employee("1", "KK").setAddress("Mumbai").setSalary(100),
                new Employee("1", "Vishnu").setAddress("Bangalore").setSalary(100));

        Map<String, List<String>> map2 = new HashMap<>();
        map2.put("Rahul", Arrays.asList("org1", "org2", "org3"));
        map2.put("KK", Arrays.asList("org1", "org2", "org3"));
        map2.put("Vishnu", Arrays.asList("org1", "org2", "org3"));

       Map<String, Map<String, Integer>> summary = new HashMap<>();

        employees1.stream().forEach(e -> {
            List<String> orgs = map2.get(e.getName());
            orgs.forEach(org -> {
                Map<String, Integer> currMap = summary.getOrDefault(org, new HashMap<>());
                currMap.merge(e.getAddress(), 1, (currentCount, newCount) -> currentCount + 1);
                summary.putIfAbsent(org, currMap);
            });
        });
        System.out.println(summary);


        String paramName = new HashMap<String, String>().getOrDefault("x", "rahu").concat(".*");
        System.out.println("rahul".matches(paramName));
        System.out.println("rahul".contains("hu"));
        List<String> stringList = Arrays.asList("rahul", "rohan", "rama", "rahil").stream().filter(s -> s.contains("")).collect(Collectors.toList());
        System.out.println(stringList);
    }
}
