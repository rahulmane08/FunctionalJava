package lambdas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionTest 
{
	public static void main(String[] args) {
		Function<String, Integer> f = Integer::parseInt; //method reference to a static method
		System.out.println(f.apply("20")); // parseInt
		
		Supplier<List<String>> listCreator = ArrayList::new;
		System.out.println(listCreator.get()); // []
		
		Supplier<Employee> employeeCreator = Employee::new;
		System.out.println(employeeCreator.get()); //calls the default constructor : Employee [name=DEFAULT, id=DEFAULT_ID, age=0, address=null, salary=0.0]
		
		BiFunction<String, String, Employee> employeeOverloaded = Employee::new;
		System.out.println(employeeOverloaded.apply("1","Rahul"));

		BinaryOperator<Integer> adder = (a,b) -> a+b;
		System.out.println(adder.apply(5,4));

		Consumer<Object> printer = System.out::println;
		printer.accept("Hello world");
		Arrays.asList(1,2,3,4,5,6).forEach(printer);

		Employee e = new Employee("123","Rahul");
		e.setSalary(1000).setAge(31).setAddress("HSR");
		Function<Employee, String> nameGetter = Employee::getName;
		Function<Employee, String> addressGetter = Employee::getAddress;
		Function<Employee, String> idGetter = Employee::getId;
		Function<Employee, Double> salGetter = Employee::getSalary;
		System.out.printf("name = %s, address = %s, id = %s, salary = %s\n",
				nameGetter.apply(e), addressGetter.apply(e), idGetter.apply(e), String.valueOf(salGetter.apply(e)));

		// Predicates:
		Predicate<String> p1 = (s) -> s.length() < 20;
		Predicate<String> p2 = (s) -> s.length() > 5;
		Predicate<String> p3 = p1.and(p2); // length of string greater than 5 and less than 20
		Predicate<String> p4 = p1.or(p2);
		System.out.println(p3.test("hi")); //false
		System.out.println(p3.test("Rahul Mane")); //true
		System.out.println(p4.test("hi")); //true

		List<String> list = new ArrayList<>(Arrays.asList("Rahul", "Santosh", "Murali"));
		list.replaceAll(String::toLowerCase);
		System.out.println(list);
		list.removeIf(p2);
		System.out.println(list); // Rahul

	}
}
