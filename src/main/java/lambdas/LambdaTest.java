package lambdas;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class LambdaTest {

    static class NameComparator implements Comparator<Employee>
    {
        @Override
        public int compare(Employee o1, Employee o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
    public static void main(String[] args)
    {
        //anonymous IC
        new Thread(()->System.out.println("sample lambda thread")).start();

        //method references
        List<String> list = Arrays.asList(new String[] {"d","c","b","a"});
        Collections.sort(list, String::compareToIgnoreCase);
        System.out.println(list);

        //method references for user defined objects
        List<Employee> employees = new ArrayList<>();
        for(int i=10;i>0;i--)
        {
            Employee e = new Employee(String.valueOf(i),"Rahul"+i);
            e.setSalary(200000*i);
            employees.add(e);
        }

        // using the compareTo method of Employee class using method references
        Collections.sort(employees, Employee::compareTo);
        System.out.println(employees);

        // creating a anonymous comparator using lambda
        Comparator<Employee> nameComparator = (o1, o2)->o1.getName().compareTo(o2.getName());

        Collections.sort(employees, nameComparator);
        employees.sort((o1,o2)->o2.getName().compareTo(o1.getName())); // Collections have a sort method now
        System.out.println("After name asc sort : \n"+employees);

        // Using Function that will be used by Comparator.comparing
        Function<Employee, String> nameGetter = e -> e.getName();
        employees.sort(Comparator.comparing(nameGetter).reversed());
        System.out.println("After name desc sort : \n"+employees);

        // using method references
        employees.sort(Comparator.comparing(Employee::getSalary));
        System.out.println("After salary asc sort : \n"+employees);

        employees.sort(Comparator.comparing(Employee::getSalary).reversed().thenComparing(Employee::getName));
        System.out.println("after descending salary sort and asc names = "+employees);

        //two forms of lambdra expressions.
        //(parameters) -> expression
        //(parameters) -> { statements;}
        // in the below example hte listFiles method takes a FileFilter object which is a functional interface
        //containing only one method accept(File f)
        //hence we can use a lambda expression by simply passing a f1-> //filter criteria
        //f1 here points to each file in the array
        File f = new File("/Users/rmane");
        File[] childFiles = f.listFiles(f1->f1.isFile());
        System.out.println("All child files in "+f.getAbsolutePath());
        Arrays.asList(childFiles).forEach(file->System.out.println(file.getName()));

        File[] childDirectories = f.listFiles(d->d.isDirectory());
        System.out.println("\nAll child directories in "+f.getAbsolutePath());
        Arrays.asList(childDirectories).forEach(file->System.out.println(file.getName()));


        //we can also use method references as the accept() takes a file object and hence we can pass a
        //method in the file class that returns a boolean
        File[] hiddenDirectories = f.listFiles(File::isHidden);
        System.out.println("\nAll hidden ßchild directories in "+f.getAbsolutePath());
        Arrays.asList(hiddenDirectories).forEach(file->System.out.println(file.getName()));

        //1. method reference to a static method
        /**This is a functional interface whose functional method is apply(Object).
         Type Parameters:
         <T> the type of the input to the function
         <R> the type of the result of the function
         **/
        Function<String, Integer> intParseFunction = Integer::parseInt;
        System.out.println(intParseFunction.apply("300"));

        //2. method references for instance methods of an existing object
        Consumer<Object> println = System.out::println;


        //3. method references on constructors
        Supplier<List<String>> listSupplier = ArrayList::new;
        List<String> l1 = listSupplier.get();
        l1.add("a");
        List<String> l2 = listSupplier.get();
        l2.add("b");
        System.out.println(l1+","+l2);

    }
}
